# Alta

[← Voltar para a documentação geral](../README.md)

## Papel no fluxo

Alta recebe o relatório encaminhado para encerramento, registra uma alta simulada e publica `PACIENTE_LIBERADO`. O mesmo consumidor atende mensagens de Atendimento, Internação ou Medicação porque esses producers usam `ENCAMINHAMENTO_ALTA`.

## Entrada e processamento

O [ConsumerServiceAlta](../alta/src/main/java/com/estudos/ms/emergencia/alta/service/ConsumerServiceAlta.java) consome `ENCAMINHAMENTO_ALTA` no grupo `alta-group`. Além do JSON com `RelatorioTriagem`, lê o header Kafka `origem` e passa esse valor ao `AltaService`.

O service cria o registro com a orientação fixa `Repouso`, o relatório recebido e a origem. Persiste o registro em H2 e publica o JSON da entidade no tópico `PACIENTE_LIBERADO`. Essa publicação não adiciona o header `origem`.

## Eventos

| Direção | Tópico | Produtor/consumidor | Observação |
| --- | --- | --- | --- |
| Recebe | `ENCAMINHAMENTO_ALTA` | Atendimento, Internação e Medicação → `alta-group` | Payload de relatório de triagem; espera o header `origem`. |
| Publica | `PACIENTE_LIBERADO` | Alta → Auditoria (`auditoria-group`) | Payload da entidade `Alta`; sem header `origem` enviado por este producer. |

Na Auditoria, a ausência desse header em `PACIENTE_LIBERADO` aciona o fallback que define origem como `ALTA`.

## Persistência e classes

O módulo usa Spring Data JPA com H2 em memória (`emergencialtaodb`) e porta `8085`. Os dados se perdem ao parar a aplicação.

- [ConsumerServiceAlta.java](../alta/src/main/java/com/estudos/ms/emergencia/alta/service/ConsumerServiceAlta.java): listener e extração do header.
- [AltaService.java](../alta/src/main/java/com/estudos/ms/emergencia/alta/service/AltaService.java): criação, persistência e publicação de liberação.
- [Alta.java](../alta/src/main/java/com/estudos/ms/emergencia/alta/model/Alta.java) e [AltaRepository.java](../alta/src/main/java/com/estudos/ms/emergencia/alta/repository/AltaRepository.java): modelo persistido e repositório.
- [application.yaml](../alta/src/main/resources/application.yaml): porta, datasource e broker.

O código encontrado não define um endpoint REST de consulta de altas. O evento `PACIENTE_LIBERADO` é observado pela Auditoria, não por um consumer de negócio adicional localizado no módulo.