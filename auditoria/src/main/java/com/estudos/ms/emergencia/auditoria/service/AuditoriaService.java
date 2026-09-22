package com.estudos.ms.emergencia.auditoria.service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.auditoria.model.EventoAuditoria;
import com.estudos.ms.emergencia.auditoria.repository.AuditoriaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuditoriaService {

  private final ObjectMapper objectMapper;
  private final AuditoriaRepository auditoriaRepository;
  private final ExtrairIdFichaService extrairIdFichaService;
  private final Logger LOG = LoggerFactory.getLogger(AuditoriaService.class);

  public AuditoriaService(ObjectMapper objectMapper, AuditoriaRepository auditoriaRepository,
      ExtrairIdFichaService extrairIdFichaService) {
    this.objectMapper = objectMapper;
    this.auditoriaRepository = auditoriaRepository;
    this.extrairIdFichaService = extrairIdFichaService;
  }

  public void salvarEvento(ConsumerRecord<?, String> record) {
    JsonNode json = converterMensagem(record.value());
    if (json == null || !json.isObject()) {
      LOG.warn("Evento ignorado por possuir payload JSON inválido. topico={}", record.topic());
      return;
    }

    Map<String, Object> payload = objectMapper.convertValue(json, Map.class);
    Long idFicha = extrairIdFichaService.extrair(json).orElse(null);
    String origem = origem(record);
    String destino = destino(record.topic(), json);
    String eventId = eventId(record);

    if (idFicha == null) {
      LOG.warn("Evento sem idFicha identificado. eventId={}, topico={}", eventId, record.topic());
    }

    EventoAuditoria evento = new EventoAuditoria(
        eventId, idFicha, origem, destino, record.topic(), payload, LocalDateTime.now());

    LOG.info("Salvando: {}", evento);
    auditoriaRepository.save(evento);
  }

  private JsonNode converterMensagem(String value) {
    try {
      return objectMapper.readTree(value);
    } catch (Exception e) {
      LOG.error("Erro ao converter mensagem", e);
      return null;
    }
  }

  private String origem(ConsumerRecord<?, String> record) {
    var header = record.headers().lastHeader("origem");
    if (header != null) {
      return new String(header.value(), StandardCharsets.UTF_8);
    }
    return switch (record.topic()) {
      case "FICHA_CRIADA" -> "RECEPCAO";
      case "PACIENTE_LIBERADO" -> "ALTA";
      default -> "UNKNOWN";
    };
  }

  private String destino(String topico, JsonNode mensagem) {
    if (topico.startsWith("ENCAMINHAMENTO_")) {
      return topico.substring("ENCAMINHAMENTO_".length());
    }
    return switch (topico) {
      case "FICHA_CRIADA", "ATENDIMENTO_INCIADO", "ATENDIMENTO_CONCLUIDO" -> "ATENDIMENTO";
      case "INTERNACAO_INICIADA", "INTERNACAO_FINALIZADA" -> "INTERNACAO";
      case "MEDICACAO_INICIADA", "MEDICACAO_CONCLUIDA" -> "MEDICACAO";
      case "PACIENTE_LIBERADO" -> "ALTA";
      default -> mensagem.path("encaminhamento").asText("UNKNOWN");
    };
  }

  private String eventId(ConsumerRecord<?, String> record) {
    if (record.partition() >= 0 && record.offset() >= 0) {
      return record.topic() + "-" + record.partition() + "-" + record.offset();
    }
    return UUID.randomUUID().toString();
  }

}
