package com.estudos.ms.atendimento.service;

import com.estudos.ms.atendimento.model.AtendimentoDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class AtendimentoDispatcher {

    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public AtendimentoDispatcher(KafkaTemplate<Long, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public void enviarAtendimento(AtendimentoDTO atendimento, String topico){

        try {
            // simulando atendimento
            Thread.sleep(5000);
            String json = objectMapper.writeValueAsString(atendimento);
            if (kafkaTemplate != null) {
                kafkaTemplate.send(topico, atendimento.id(), json);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }



    }
}
