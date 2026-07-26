package com.estudos.ms.atendimento.service;

import com.estudos.ms.atendimento.model.AtendimentoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AtendimentoDispatcher {

    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(AtendimentoDispatcher.class);

    public AtendimentoDispatcher(KafkaTemplate<Long, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public void enviarAtendimento(AtendimentoDTO atendimento, String topico){

        try {
            // simulando atendimento
            Thread.sleep(5000);
            String json = objectMapper.writeValueAsString(atendimento);
            if (kafkaTemplate != null && Objects.nonNull(topico)) {
                kafkaTemplate.send(topico, json);
                logger.info("Mensagem enviada para Kafka: " + json);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("Não foi possível enviar a mensagem: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
