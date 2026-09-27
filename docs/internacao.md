# Internação

[← Voltar para a documentação geral](../README.md)

## Papel no fluxo

Internação atende o encaminhamento de triagem destinado a esse caminho. Ao receber o relatório, cria e salva uma internação simulada, publica seu início e agenda verificações para finalizá-la. Depois, publica um encaminhamento à Alta e o evento de finalização da internação.

## Entrada

O [ConsumerServiceInternacao](../internacao/src/main/java/com/estudos/ms/emergencia/internacao/service/ConsumerServiceInternacao.java) consome `ENCAMINHAMENTO_INTERNACAO` no grupo `internacao-group`, desserializa o valor como `RelatorioTriagem` e chama `InternacaoService`.

## Processamento e persistência

`InternacaoService` constrói a entidade com valores simulados para quarto (`701`), ala (`Vermelha`) e motivo. A entidade registra horário inicial, horário final previsto 30 segundos depois e estado não finalizado. O serviço salva via Spring Data JPA em H2 em memória e, após salvar, publica `INTERNACAO_INICIADA`.

`InternacaoApplication` habilita o agendamento. A cada 10 segundos, `verificarInternacao` procura registros com data final anterior ao momento atual e estado não finalizado. Para cada resultado, marca e salva como finalizado e chama o dispatcher.

## Eventos

| Tópico | Quando é publicado | Consumers encontrados |
| --- | --- | --- |
| `INTERNACAO_INICIADA` | Após salvar uma internação. Payload: entidade `Internacao`. | Auditoria (`auditoria-group`). |
| `ENCAMINHAMENTO_ALTA` | Na finalização; contém o relatório associado à internação. | Alta (`alta-group`), Auditoria (`auditoria-group`). |
| `INTERNACAO_FINALIZADA` | Na finalização; contém a entidade de internação atualizada. | Auditoria (`auditoria-group`). |

O dispatcher inclui `origem=INTERNACAO` no header dos três eventos. Ao concluir, publica primeiro `ENCAMINHAMENTO_ALTA` e depois `INTERNACAO_FINALIZADA`. O evento de liberação efetivamente publicado pela Alta é `PACIENTE_LIBERADO`; Internação não publica esse tópico.

## Classes e configuração

- [ConsumerServiceInternacao.java](../internacao/src/main/java/com/estudos/ms/emergencia/internacao/service/ConsumerServiceInternacao.java): consumer de encaminhamento.
- [InternacaoService.java](../internacao/src/main/java/com/estudos/ms/emergencia/internacao/service/InternacaoService.java): criação, persistência e verificação agendada.
- [InternacaoDispatcherService.java](../internacao/src/main/java/com/estudos/ms/emergencia/internacao/service/InternacaoDispatcherService.java): serialização e publicação dos eventos.
- [Internacao.java](../internacao/src/main/java/com/estudos/ms/emergencia/internacao/model/Internacao.java), [InternacaoRepository.java](../internacao/src/main/java/com/estudos/ms/emergencia/internacao/repository/InternacaoRepository.java) e `RelatorioTriagem.java`: entidade, acesso a dados e relatório embutido.
- [InternacaoApplication.java](../internacao/src/main/java/com/estudos/ms/emergencia/internacao/InternacaoApplication.java): habilita o agendamento.
- [application.yml](../internacao/src/main/resources/application.yml): porta `8083`, H2 e broker Kafka.

O banco H2 é em memória e usa `emergenciainternacaodb`; não há persistência durável configurada. A Auditoria também consome os três eventos de saída.