package com.estudos.ms.emergencia.internacao.service;

import com.estudos.ms.emergencia.internacao.model.Internacao;
import com.estudos.ms.emergencia.internacao.repository.InternacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessarInternacaoService {

    private final InternacaoRepository internacaoRepository;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(ProcessarInternacaoService.class);

    public ProcessarInternacaoService(InternacaoRepository internacaoRepository, ObjectMapper objectMapper) {
        this.internacaoRepository = internacaoRepository;
        this.objectMapper = objectMapper;
    }

    public void execute(Internacao internacao) {
        if (Objects.nonNull(internacao)) {
            var interna = internacaoRepository.save(internacao);
            try {
                var json = objectMapper.writeValueAsString(interna);
                logger.info("Internacao processada e salva com sucesso: " + json);
            } catch (Exception e) {
                Logger logger = LoggerFactory.getLogger(ProcessarInternacaoService.class);
                logger.error("Erro ao converter internacao para JSON: " + e.getMessage());
            }
        }

    }

}
