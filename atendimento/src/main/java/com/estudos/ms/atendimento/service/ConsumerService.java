package com.estudos.ms.atendimento.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.estudos.ms.atendimento.model.Ficha;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {

    private final ObjectMapper objectMapper;
    private final AtendimentoService atendimentoService;
    private final static String TOPICO = "FICHA_CRIADA";

    public ConsumerService(ObjectMapper objectMapper, AtendimentoService atendimentoService) {
        this.objectMapper = objectMapper;
        this.atendimentoService = atendimentoService;
    }

    @KafkaListener(topics = TOPICO, groupId = "atendimento-group")
    public void consumir(ConsumerRecord<Long, String> record) {
        Ficha ficha = null;
        try {
            ficha = objectMapper.readValue(record.value(), Ficha.class);
            this.atendimentoService.atender(ficha);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
