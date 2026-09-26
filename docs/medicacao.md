# Medicação

[← Voltar para a documentação geral](../README.md)

## Papel no fluxo

Medicação consome o encaminhamento de triagem correspondente, cria um registro de medicação simulado e publica eventos de acompanhamento. Depois de uma espera simulada, encaminha o relatório à Alta. A etapa não é uma prescrição clínica real.

## Entrada e processamento

O [ConsumerServiceMedicacao](../medicacao/src/main/java/com/estudos/ms/emergencia/medicacao/service/ConsumerServiceMedicacao.java) consome `ENCAMINHAMENTO_MEDICACAO` no grupo `medicacao-group`, converte a mensagem em `RelatorioTriagem` e chama `MedicacaoService`.

O service cria uma entidade com medicamento `Dipirona` e dose `5mg`, persiste via Spring Data JPA em H2 e publica `MEDICACAO_INICIADA`. Em seguida, aguarda um segundo com `Thread.sleep` para simular o tempo de medicação e publica os eventos de encerramento. Essa espera ocorre no processamento do consumer.

## Eventos

| Tópico | Quando é publicado | Consumers encontrados |
| --- | --- | --- |
| `MEDICACAO_INICIADA` | Após salvar o registro; payload é a entidade `Medicacao`. | Auditoria (`auditoria-group`). |
| `MEDICACAO_CONCLUIDA` | Após a espera simulada; payload é a entidade `Medicacao`. | Auditoria (`auditoria-group`). |
| `ENCAMINHAMENTO_ALTA` | Após `MEDICACAO_CONCLUIDA`; payload é o relatório de triagem associado. | Alta (`alta-group`), Auditoria (`auditoria-group`). |

O dispatcher adiciona `origem=MEDICACAO` no header Kafka. O relatório enviado à Alta é publicado no mesmo tópico `ENCAMINHAMENTO_ALTA` usado pelo Atendimento e pela Internação.

## Classes e persistência

- [ConsumerServiceMedicacao.java](../medicacao/src/main/java/com/estudos/ms/emergencia/medicacao/service/ConsumerServiceMedicacao.java): consumer do encaminhamento.
- [MedicacaoService.java](../medicacao/src/main/java/com/estudos/ms/emergencia/medicacao/service/MedicacaoService.java): criação, persistência e espera simulada.
- [MedicacaoDispatcherService.java](../medicacao/src/main/java/com/estudos/ms/emergencia/medicacao/service/MedicacaoDispatcherService.java): publicação de eventos.
- [Medicacao.java](../medicacao/src/main/java/com/estudos/ms/emergencia/medicacao/model/Medicacao.java) e [MedicacaoRepository.java](../medicacao/src/main/java/com/estudos/ms/emergencia/medicacao/repository/MedicacaoRepository.java): entidade e repositório.
- [application.yaml](../medicacao/src/main/resources/application.yaml): porta `8084`, datasource H2 e endereço Kafka.

O datasource usa o banco H2 em memória `emergenciamedicacaodb`; os registros não sobrevivem ao encerramento do processo. A Auditoria consome os três tópicos publicados pelo serviço.