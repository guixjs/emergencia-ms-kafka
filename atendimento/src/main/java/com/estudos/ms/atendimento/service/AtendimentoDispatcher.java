package com.estudos.ms.atendimento.service;

import com.estudos.ms.atendimento.model.AtendimentoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AtendimentoDispatcher {

    private final Random random = new Random();
    private final KafkaTemplate<Long, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(AtendimentoDispatcher.class);

    public AtendimentoDispatcher(KafkaTemplate<Long, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void enviarAtendimento(AtendimentoDTO atendimento, String topico) {

        try {
            // simulando atendimento
            int tempoDeAtendimento = random.nextInt(9) + 1;
            tempoDeAtendimento = tempoDeAtendimento * 1000;
            Thread.sleep(tempoDeAtendimento);
            // simulando atendimento

            String json = objectMapper.writeValueAsString(atendimento);
            if (kafkaTemplate != null && Objects.nonNull(topico)) {
                kafkaTemplate.send(topico, json);
                LOGGER.info("Mensagem: {} Topico Enviado: {} ", json, topico);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            LOGGER.error("Nao foi possivel enviar a mensagem: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
