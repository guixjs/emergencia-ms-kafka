package com.estudos.ms.atendimento.service;

import com.estudos.ms.atendimento.model.FichaCriadaDTO;
import org.springframework.kafka.core.KafkaTemplate;

public class AtendimentoService {
    
    private KafkaTemplate<Long, String> kafkaTemplate;


    public void encaminharPaciente(FichaCriadaDTO ficha) {
    }

    public void atender(FichaCriadaDTO ficha) {




    }
}
