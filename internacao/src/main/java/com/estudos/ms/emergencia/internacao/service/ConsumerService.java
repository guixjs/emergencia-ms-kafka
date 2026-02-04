package com.estudos.ms.emergencia.internacao.service;

import com.estudos.ms.emergencia.internacao.model.Internacao;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {

    private final ObjectMapper objectMapper;
    private final ProcessarInternacaoService processarInternacao;

    public ConsumerService(ObjectMapper objectMapper, ProcessarInternacaoService processarInternacao) {
        this.processarInternacao = processarInternacao;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "ATENDIMENTO_INTERNACAO", groupId = "internacao-group")
    public void consumirMensagemInternacao(String mensagem) {
        try {
            var internacaoDTO = objectMapper.readValue(mensagem, Internacao.class);
            this.processarInternacao.execute(internacaoDTO);
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem de internação: " + e.getMessage());
        }

    }
}
