# Atendimento

[← Voltar para a documentação geral](../README.md)

## Papel no fluxo

Atendimento é o ponto de triagem e roteamento. Ele consome as fichas publicadas pela Recepção, aplica regras simplificadas para calcular risco, setor e encaminhamento, e publica eventos intermediários e o relatório de triagem. Não há repositório nem banco configurado neste módulo.

## Entrada

O [ConsumerService](../atendimento/src/main/java/com/estudos/ms/atendimento/service/ConsumerService.java) consome `FICHA_CRIADA` no grupo `atendimento-group`. Desserializa o valor JSON para `Ficha` e entrega o objeto a `AtendimentoService`.

## Triagem e regras

`AtendimentoService` publica a notificação de início, solicita o relatório à `TriagemService`, publica o encaminhamento calculado e, ao final do método, publica o evento de conclusão da etapa de Atendimento.

As regras implementadas em [TriagemService.java](../atendimento/src/main/java/com/estudos/ms/atendimento/service/TriagemService.java) são simplificadas:

- Sintomas presentes na lista de internação resultam em risco `ALTO`.
- Sintomas presentes na lista de medicação resultam em risco `ALTO` para idade menor que 18 ou maior que 65; nos demais casos, `MEDIO`.
- Outros sintomas resultam em `BAIXO`.
- Idade abaixo de 18 seleciona `PEDIATRIA`; alguns sintomas selecionam especialidades específicas; os demais usam `CLINICO_GERAL`.
- Encaminhamento preferencial resulta em `INTERNACAO`; sem preferência, risco `ALTO` resulta em `INTERNACAO`, `MEDIO` em `MEDICACAO` e os demais em `ALTA`.

A condição de preferência vem na ficha criada pela Recepção (idade até 18 ou a partir de 65). Essas regras não constituem avaliação clínica real.

## Eventos publicados

| Tópico | Conteúdo | Consumidores encontrados |
| --- | --- | --- |
| `ATENDIMENTO_INCIADO` | JSON da ficha recebida. O nome tem essa grafia no código. | Auditoria (`auditoria-group`). |
| `ENCAMINHAMENTO_INTERNACAO` | JSON do relatório de triagem quando o destino é Internação. | Internação (`internacao-group`), Auditoria (`auditoria-group`). |
| `ENCAMINHAMENTO_MEDICACAO` | JSON do relatório quando o destino é Medicação. | Medicação (`medicacao-group`), Auditoria (`auditoria-group`). |
| `ENCAMINHAMENTO_ALTA` | JSON do relatório quando o destino é Alta. | Alta (`alta-group`), Auditoria (`auditoria-group`). |
| `ATENDIMENTO_CONCLUIDO` | JSON do relatório de triagem. | Auditoria (`auditoria-group`). |

O dispatcher adiciona o header Kafka `origem=ATENDIMENTO` às mensagens publicadas. A ordem no método é: evento de início, evento de encaminhamento e evento de conclusão. Logo, `ATENDIMENTO_CONCLUIDO` marca o fim da etapa de triagem, não o fim de Internação ou Medicação.

## Modelos e código

- [AtendimentoService.java](../atendimento/src/main/java/com/estudos/ms/atendimento/service/AtendimentoService.java): coordena início, triagem, roteamento e conclusão.
- [AtendimentoDispatcher.java](../atendimento/src/main/java/com/estudos/ms/atendimento/service/AtendimentoDispatcher.java): serializa mensagens e publica os tópicos.
- [TriagemService.java](../atendimento/src/main/java/com/estudos/ms/atendimento/service/TriagemService.java): calcula risco, setor e encaminhamento.
- [Ficha.java](../atendimento/src/main/java/com/estudos/ms/atendimento/model/Ficha.java), [RelatorioTriagem.java](../atendimento/src/main/java/com/estudos/ms/atendimento/model/RelatorioTriagem.java) e os enums em `atendimento/src/main/java/com/estudos/ms/atendimento/enums/`: modelos da entrada e do resultado.
- [application.yml](../atendimento/src/main/resources/application.yml): porta `8082` e configurações de serialização Kafka.

## Relação com os demais serviços

Internação, Medicação e Alta consomem seus respectivos tópicos de encaminhamento. A Auditoria consome os cinco tópicos publicados/encaminhados listados acima, independentemente do consumer group de negócio. Não foram encontrados consumers de negócio para tópicos chamados `ATENDIMENTO_INTERNACAO`, `ATENDIMENTO_MEDICACAO` ou `ATENDIMENTO_ALTA`; eles não fazem parte do fluxo documentado.