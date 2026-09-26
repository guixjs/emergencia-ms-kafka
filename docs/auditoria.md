# Auditoria

[← Voltar para a documentação geral](../README.md)

## Papel no fluxo

Auditoria registra uma trilha dos eventos recebidos pelo Kafka sem participar das decisões de encaminhamento. Ela tem um consumer group próprio, `auditoria-group`, e salva cada mensagem válida como um documento na coleção `eventos` do banco MongoDB `emergencia`.

O envelope padronizado é criado dentro do serviço de Auditoria a partir do `ConsumerRecord`. Os producers não enviam esse envelope comum: cada tópico continua carregando seu payload de domínio, e o serviço de Auditoria o guarda no campo `payload` junto dos metadados calculados.

## Tópicos consumidos

O listener em [ConsumerService.java](../auditoria/src/main/java/com/estudos/ms/emergencia/auditoria/service/ConsumerService.java) registra `auditoria-group` para os seguintes tópicos:

| Tópico | Producer encontrado |
| --- | --- |
| `FICHA_CRIADA` | Recepção |
| `ATENDIMENTO_INCIADO` | Atendimento |
| `ENCAMINHAMENTO_ALTA` | Atendimento, Internação, Medicação |
| `ENCAMINHAMENTO_MEDICACAO` | Atendimento |
| `ENCAMINHAMENTO_INTERNACAO` | Atendimento |
| `ATENDIMENTO_CONCLUIDO` | Atendimento |
| `INTERNACAO_INICIADA` | Internação |
| `INTERNACAO_FINALIZADA` | Internação |
| `PACIENTE_LIBERADO` | Alta |
| `MEDICACAO_INICIADA` | Medicação |
| `MEDICACAO_CONCLUIDA` | Medicação |

Como o grupo é independente dos grupos de negócio, Auditoria recebe suas próprias mensagens em vez de compartilhar o processamento desses grupos.

## Conversão para o envelope

`ConsumerService` recebe um `ConsumerRecord<?, String>` e chama `AuditoriaService.salvarEvento`. O service:

1. Faz parse do valor como JSON. Se o parse falhar, ou se o JSON não for um objeto, registra aviso/erro e ignora a mensagem.
2. Converte o objeto JSON completo em `Map<String, Object>` para o campo `payload`.
3. Extrai `idFicha` de um dos caminhos conhecidos, quando houver valor numérico.
4. Calcula origem, destino, tipo e identificador do evento a partir dos dados do record e do JSON.
5. Cria `EventoAuditoria`, usa `LocalDateTime.now()` como timestamp de processamento e salva pelo repositório Mongo.

### Envelope modelado

O construtor de [EventoAuditoria.java](../auditoria/src/main/java/com/estudos/ms/emergencia/auditoria/model/EventoAuditoria.java) recebe os campos:

| Campo do modelo | Valor atribuído |
| --- | --- |
| `id` | Campo anotado com `@Id`, usado como identificador do documento Mongo. |
| `idEvento` | `topico-particao-offset` quando partição e offset são não negativos; caso contrário, UUID. |
| `idFicha` | Primeiro valor numérico encontrado nos caminhos listados abaixo; pode ficar `null`. |
| `origem` | Header `origem`, quando presente; senão, fallback por tópico ou `UNKNOWN`. |
| `destino` | Sufixo de `ENCAMINHAMENTO_*`, tabela de tópicos conhecida ou campo `encaminhamento`; pode resultar em `UNKNOWN`. |
| `tipoEvento` | Nome do tópico (`record.topic()`). |
| `payload` | Objeto JSON da mensagem convertido para mapa; não é apenas o subcampo `payload` de um envelope produtor. |
| `timestamp` | Horário local em que Auditoria processa a mensagem (`LocalDateTime.now()`), não o timestamp de criação do evento no producer. |

Há uma particularidade no modelo: o campo é chamado `idEvento`, mas o getter é `getEventId()`. A documentação preserva o nome do campo usado no construtor e registra essa divergência; o teste de extração não valida o nome final das propriedades no BSON serializado. No armazenamento Mongo, o campo anotado `@Id` corresponde ao identificador do documento.

### Extração de `idFicha`

[ExtrairIdFichaService.java](../auditoria/src/main/java/com/estudos/ms/emergencia/auditoria/service/ExtrairIdFichaService.java) verifica estes caminhos JSON, na ordem declarada:

- `/idFicha`
- `/ficha/idFicha`
- `/relatorio/ficha/idFicha`
- `/payload/idFicha`
- `/payload/ficha/idFicha`
- `/payload/relatorio/ficha/idFicha`

O primeiro nó numérico encontrado é convertido para `Long`. Valores textuais, mesmo que contenham dígitos, não são aceitos. Se nenhum caminho corresponder, `idFicha` fica nulo; o evento ainda é salvo e um aviso é registrado.

### Origem e destino

Para `origem`, o service lê o último header `origem` do record. Sem header, `FICHA_CRIADA` resulta em `RECEPCAO`, `PACIENTE_LIBERADO` resulta em `ALTA`; os outros tópicos resultam em `UNKNOWN`.

Para `destino`, `ENCAMINHAMENTO_*` usa o sufixo do tópico. Nos demais casos, há mapeamento explícito para Atendimento, Internação, Medicação ou Alta; se o tópico não estiver mapeado, o service lê `encaminhamento` no JSON, com fallback `UNKNOWN`.

Os eventos de Atendimento, Internação e Medicação incluem header `origem` nos dispatchers. Recepção não inclui o header em `FICHA_CRIADA`, e Alta não o inclui em `PACIENTE_LIBERADO`; nesses dois tópicos são usados os fallbacks acima.

## Exemplo do documento

Exemplo ilustrativo da estrutura preenchida pelo código para uma mensagem `FICHA_CRIADA`. Os valores são fictícios; `_id` é o identificador do documento Mongo. A anotação `@Id` e os nomes de campos refletem o modelo, mas o nome BSON resultante para `idEvento` não foi verificado contra uma instância Mongo ativa, especialmente por causa do getter `getEventId()`.

```json
{
  "_id": "<id atribuído ao documento>",
  "idEvento": "FICHA_CRIADA-0-12",
  "idFicha": 123,
  "origem": "RECEPCAO",
  "destino": "ATENDIMENTO",
  "tipoEvento": "FICHA_CRIADA",
  "payload": {
    "idFicha": 123,
    "sintomasRelatados": "Dor no peito",
    "isPreferencial": false,
    "infoPaciente": {
      "nome": "Paciente exemplo",
      "idade": 42
    }
  },
  "timestamp": "2026-09-26T14:30:00"
}
```

O payload exemplifica os campos de `FichaCriadaDTO`; outros tópicos carregam estruturas diferentes, como `RelatorioTriagem`, `Internacao`, `Medicacao` ou `Alta`. O código não normaliza esses payloads para um único formato de domínio.

## Persistência e limites

[AuditoriaRepository.java](../auditoria/src/main/java/com/estudos/ms/emergencia/auditoria/repository/AuditoriaRepository.java) estende `MongoRepository<EventoAuditoria, String>`. A classe de domínio usa `@Document(collection = "eventos")`; a configuração define MongoDB em `mongodb://localhost:27017/emergencia` e a porta HTTP `8086`.

Não foi encontrado controller ou endpoint de consulta do histórico. A documentação não presume retenção, política de deduplicação, garantias de entrega, nem consulta ordenada por `idFicha`; esses comportamentos não aparecem configurados neste módulo.

Referências adicionais: [AuditoriaService.java](../auditoria/src/main/java/com/estudos/ms/emergencia/auditoria/service/AuditoriaService.java), [application.yaml](../auditoria/src/main/resources/application.yaml) e [ExtrairIdFichaServiceTest.java](../auditoria/src/test/java/com/estudos/ms/emergencia/auditoria/service/ExtrairIdFichaServiceTest.java). O teste cobre os caminhos de extração, não a persistência Mongo nem o mapeamento BSON.