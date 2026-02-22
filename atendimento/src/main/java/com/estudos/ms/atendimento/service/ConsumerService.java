package com.estudos.ms.atendimento.service;

import com.estudos.ms.atendimento.model.FichaCriadaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private final ObjectMapper objectMapper;
    private final AtendimentoService atendimentoService;

    public ConsumerService(ObjectMapper objectMapper, AtendimentoService atendimentoService) {
        this.objectMapper = objectMapper;
        this.atendimentoService = atendimentoService;
    }

    @KafkaListener(topics = "FICHA_CRIADA", groupId = "teste-json")
    public void consumir(ConsumerRecord<Long, String> record) {
        FichaCriadaDTO ficha = null;
        try {
            ficha = objectMapper.readValue(record.value(), FichaCriadaDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        this.atendimentoService.atender(ficha);
    }
}
