package com.estudos.ms.emergencia.recepcao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.recepcao.dto.FichaCriadaDTO;
import com.estudos.ms.emergencia.recepcao.dto.NovaFichaRequestDTO;
import com.estudos.ms.emergencia.recepcao.mapper.FichaMapper;
import com.estudos.ms.emergencia.recepcao.repository.FichaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NovaFichaService {

    private FichaRepository repository;
    private KafkaTemplate<Long, String> kafkaTemplate;
    private TriagemService triagemService;
    private final ObjectMapper objectMapper;
    private final static Logger logger = LoggerFactory.getLogger(NovaFichaService.class);

    public NovaFichaService(FichaRepository repository, KafkaTemplate<Long, String> kafkaTemplate,
            ObjectMapper objectMapper, TriagemService triagemService) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.triagemService = triagemService;
    }

    public FichaCriadaDTO execute(NovaFichaRequestDTO novaFichaRequestDTO) {
        var ficha = triagemService.montarFicha(novaFichaRequestDTO);
        var fichaCriada = FichaMapper.converteDeEntidadeParaRespostaDTO(this.repository.save(ficha));

        enviarFichaKafka(fichaCriada);
        return fichaCriada;
    }

    private void enviarFichaKafka(FichaCriadaDTO fichaCriada) {

        try {
            String json = objectMapper.writeValueAsString(fichaCriada);
            if (kafkaTemplate != null) {
                kafkaTemplate.send("FICHA_CRIADA", fichaCriada.id(), json);
                logger.info("Mensagem enviada para Kafka: " + json);
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem para Kafka: " + e.getMessage());
            logger.error("Não foi possível enviar a mensagem" + e.getMessage());
        }
    }
}
