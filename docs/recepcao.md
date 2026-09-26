# Recepção

[← Voltar para a documentação geral](../README.md)

## Papel no fluxo

A Recepção é a entrada HTTP do fluxo. Ela recebe os dados básicos do paciente, cria e persiste uma ficha e publica `FICHA_CRIADA` no Kafka. O Atendimento consome esse tópico para iniciar a triagem. A Recepção não possui consumer Kafka no código encontrado.

## Entrada e criação da ficha

O controller expõe `POST /ficha/nova`, que recebe um `NovaFichaRequestDTO` com `nomePaciente`, `idadePaciente` e `sintomas`. O service determina se a ficha é preferencial: idade menor ou igual a 18 ou maior ou igual a 65. Em seguida, persiste a entidade e converte-a em `FichaCriadaDTO`.

O DTO publicado possui `idFicha`, `sintomasRelatados`, `isPreferencial` e `infoPaciente`. O `FichaMapper` transforma os nomes internos da entidade para os nomes usados pela resposta/evento. `DispatcherFicha` publica o JSON no tópico `FICHA_CRIADA` e usa `idFicha` como chave Kafka. O envio não adiciona o header `origem`.

`NovaFichaRequestDTO` contém anotações de validação, mas o controller não usa `@Valid`; portanto, não documentamos essas anotações como validação HTTP efetivamente aplicada.

## Eventos

| Direção | Tópico | Consumer group | Conteúdo/efeito |
| --- | --- | --- | --- |
| Publica | `FICHA_CRIADA` | `atendimento-group`; também `auditoria-group` | JSON da `FichaCriadaDTO`, com chave Kafka igual ao `idFicha`. |

O Atendimento recebe esse evento e produz os resultados da triagem. A Auditoria recebe uma cópia lógica por usar seu próprio grupo.

## Endpoints de geração em lote

O mesmo controller possui `POST /ficha/{qtd}/internacoes` e `POST /ficha/{qtd}/medicacoes`. Eles geram fichas com sintomas escolhidos aleatoriamente em listas diferentes e passam cada ficha pelo mesmo service de criação. São utilitários de simulação; não representam endpoints de Internação ou Medicação.

## Persistência e implementação

As fichas são salvas via Spring Data JPA em H2 em memória. A configuração do serviço define a porta `8090` e habilita o console H2 em `/h2-console`. Os dados não persistem após o processo ser encerrado.

Classes principais:

- [FichaController.java](../recepcao/src/main/java/com/estudos/ms/emergencia/recepcao/controller/FichaController.java): endpoints HTTP.
- [NovaFichaService.java](../recepcao/src/main/java/com/estudos/ms/emergencia/recepcao/service/NovaFichaService.java): regra de preferência, persistência e coordenação da publicação.
- [DispatcherFicha.java](../recepcao/src/main/java/com/estudos/ms/emergencia/recepcao/service/DispatcherFicha.java): serialização e publicação Kafka.
- [FichaMapper.java](../recepcao/src/main/java/com/estudos/ms/emergencia/recepcao/mapper/FichaMapper.java), [FichaCriadaDTO.java](../recepcao/src/main/java/com/estudos/ms/emergencia/recepcao/dto/FichaCriadaDTO.java) e [NovaFichaRequestDTO.java](../recepcao/src/main/java/com/estudos/ms/emergencia/recepcao/dto/NovaFichaRequestDTO.java): conversão e contratos de dados.
- [FichaRepository.java](../recepcao/src/main/java/com/estudos/ms/emergencia/recepcao/repository/FichaRepository.java): repositório JPA.
- [application.yml](../recepcao/src/main/resources/application.yml): porta, H2 e endereço Kafka.

## Relação com os outros serviços

A comunicação com Atendimento ocorre pelo tópico Kafka, sem chamada HTTP direta entre esses serviços. A criação da ficha é persistida antes da tentativa de publicação. O dispatcher captura exceções de serialização/envio e escreve a mensagem em stdout; não há política de retry ou confirmação de publicação configurada nesse código.