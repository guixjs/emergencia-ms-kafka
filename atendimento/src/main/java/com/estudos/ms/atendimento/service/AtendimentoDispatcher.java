package com.estudos.ms.atendimento.service;

import com.estudos.ms.atendimento.model.AtendimentoDTO;
import org.springframework.kafka.core.KafkaTemplate;
import tools.jackson.databind.ObjectMapper;

public class AtendimentoDispatcher {

    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public AtendimentoDispatcher(KafkaTemplate<Long, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public void enviarAtendimento(AtendimentoDTO atendimento, String topico){

        try {
            Thread.sleep(500); // simula um processamento demorado
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String json = objectMapper.writeValueAsString(atendimento);
        if (kafkaTemplate != null) {
            kafkaTemplate.send(topico, atendimento.fichaId(), json);
        }

    }
}
