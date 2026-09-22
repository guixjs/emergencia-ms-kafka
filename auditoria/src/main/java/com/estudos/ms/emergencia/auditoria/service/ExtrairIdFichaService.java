package com.estudos.ms.emergencia.auditoria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class ExtrairIdFichaService {

  private static final List<String> CAMINHOS_ID_FICHA = List.of(
      "/idFicha",
      "/ficha/idFicha",
      "/relatorio/ficha/idFicha",
      "/payload/idFicha",
      "/payload/ficha/idFicha",
      "/payload/relatorio/ficha/idFicha");

  public Optional<Long> extrair(JsonNode mensagem) {
    if (mensagem == null || mensagem.isNull()) {
      return Optional.empty();
    }

    return CAMINHOS_ID_FICHA.stream()
        .map(mensagem::at)
        .filter(JsonNode::isNumber)
        .map(JsonNode::longValue)
        .findFirst();
  }
}