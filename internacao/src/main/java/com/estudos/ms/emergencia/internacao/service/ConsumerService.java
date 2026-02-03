package com.estudos.ms.emergencia.internacao.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {

    private final ObjectMapper objectMapper;

    public ConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "ATENDIMENTO_INTERNACAO", groupId = "internacao-group")
    public void consumirMensagemInternacao(String mensagem) {
        System.out.println("TESTANDO:");
        System.out.println(mensagem);


    }
}
