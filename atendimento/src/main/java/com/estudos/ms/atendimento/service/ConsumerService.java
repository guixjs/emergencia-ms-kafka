package com.estudos.ms.atendimento.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.estudos.ms.atendimento.model.FichaCriadaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {

    private final ObjectMapper objectMapper;
    private final AtendimentoService atendimentoService;
    private final static Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);
    private final static String TOPICO = "FICHA_CRIADA";

    public ConsumerService(ObjectMapper objectMapper, AtendimentoService atendimentoService) {
        this.objectMapper = objectMapper;
        this.atendimentoService = atendimentoService;
    }

    @KafkaListener(topics = TOPICO, groupId = "atendimento-group")
    public void consumir(ConsumerRecord<Long, String> record) {
        FichaCriadaDTO ficha = null;
        try {
            LOGGER.info("Mensagem recebida pelo ATENDIMENTO -- Topico: {}", TOPICO );
            ficha = objectMapper.readValue(record.value(), FichaCriadaDTO.class);
            this.atendimentoService.atender(ficha);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
