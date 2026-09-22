package com.estudos.ms.emergencia.auditoria.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

class ExtrairIdFichaServiceTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final ExtrairIdFichaService service = new ExtrairIdFichaService();

  @Test
  void deveExtrairIdFichaNaRaiz() throws Exception {
    var mensagem = objectMapper.readTree("{\"idFicha\": 1, \"sintomasRelatados\": \"dor no peito\"}");

    assertThat(service.extrair(mensagem)).isEqualTo(Optional.of(1L));
  }

  @Test
  void deveExtrairIdFichaDentroDeRelatorio() throws Exception {
    var mensagem = objectMapper.readTree("""
        {
          "idInternacao": 1,
          "relatorio": {
            "ficha": {
              "idFicha": 1
            }
          }
        }
        """);

    assertThat(service.extrair(mensagem)).isEqualTo(Optional.of(1L));
  }

  @Test
  void deveExtrairIdFichaDeUmPayloadEnvelopado() throws Exception {
    var mensagem = objectMapper.readTree("""
        {
          "tipoEvento": "INTERNACAO_FINALIZADA",
          "payload": {
            "relatorio": {
              "ficha": {
                "idFicha": 1
              }
            }
          }
        }
        """);

    assertThat(service.extrair(mensagem)).isEqualTo(Optional.of(1L));
  }

  @Test
  void deveRetornarVazioQuandoIdFichaNaoForNumerico() throws Exception {
    var mensagem = objectMapper.readTree("{\"idFicha\": \"1\"}");

    assertThat(service.extrair(mensagem)).isEmpty();
  }

  @Test
  void deveRetornarVazioParaMensagemNula() {
    assertThat(service.extrair(null)).isEmpty();
  }
}