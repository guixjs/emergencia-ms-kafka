package com.estudos.ms.atendimento.service;

import com.estudos.ms.atendimento.model.FichaCriadaDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

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
        var ficha = objectMapper.readValue(record.value(), FichaCriadaDTO.class);

        this.atendimentoService.atender(ficha);
    }
}
