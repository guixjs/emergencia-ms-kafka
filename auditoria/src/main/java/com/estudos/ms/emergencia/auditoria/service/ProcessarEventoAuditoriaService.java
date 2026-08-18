package com.estudos.ms.emergencia.auditoria.service;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.auditoria.model.EventoAuditoria;
import com.estudos.ms.emergencia.auditoria.repository.AuditoriaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProcessarEventoAuditoriaService {

  private final ObjectMapper objectMapper;
  private final AuditoriaRepository auditoriaRepository;
  private final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);

  public ProcessarEventoAuditoriaService(ObjectMapper objectMapper, AuditoriaRepository auditoriaRepository) {
    this.objectMapper = objectMapper;
    this.auditoriaRepository = auditoriaRepository;
  }

  public void salvarEvento(ConsumerRecord<String, String> record) {
    System.out.println("CHEGOU AQUI EM SALVAR EVENTO");
    Map<String, Object> mensagem = converterMensagem(record.value());
    var topico = record.topic();
    Long idFicha = ((Number) mensagem.get("id")).longValue();

    EventoAuditoria evento = new EventoAuditoria(idFicha, topico, mensagem);

    LOG.info("Salvando: {}", evento);
    auditoriaRepository.save(evento);
  }

  private Map<String, Object> converterMensagem(String value) {
    System.out.println("CHEGOU AQUI EM CONVERTER MENSAGEM");
    try {

      return objectMapper.readValue(
          value,
          new TypeReference<Map<String, Object>>() {
          });
    } catch (Exception e) {
      LOG.error("Erro ao converter mensagem", e);
      return null;
    }
  }

}
