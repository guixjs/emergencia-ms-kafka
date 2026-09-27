package com.estudos.ms.emergencia.recepcao.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estudos.ms.emergencia.recepcao.dto.FichaCriadaDTO;
import com.estudos.ms.emergencia.recepcao.dto.NovaFichaRequestDTO;
import com.estudos.ms.emergencia.recepcao.mapper.FichaMapper;
import com.estudos.ms.emergencia.recepcao.model.Ficha;
import com.estudos.ms.emergencia.recepcao.model.Paciente;
import com.estudos.ms.emergencia.recepcao.repository.FichaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NovaFichaService {

    private FichaRepository repository;
    private DispatcherFicha dispatcherFicha;

    public NovaFichaService(FichaRepository repository, KafkaTemplate<Long, String> kafkaTemplate,
            ObjectMapper objectMapper, DispatcherFicha dispatcherFicha) {
        this.repository = repository;
        this.dispatcherFicha = dispatcherFicha;
    }

    public FichaCriadaDTO execute(NovaFichaRequestDTO novaFichaRequestDTO) {
        var ficha = montarFicha(novaFichaRequestDTO);
        var fichaCriada = FichaMapper.converteDeEntidadeParaRespostaDTO(this.repository.save(ficha));

        dispatcherFicha.enviarFichaKafka(fichaCriada);
        return fichaCriada;
    }

    private Ficha montarFicha(NovaFichaRequestDTO novaFichaRequestDTO) {
        var preferencial = verificarPrefencialidade(novaFichaRequestDTO.idadePaciente());

        var paciente = new Paciente(novaFichaRequestDTO.nomePaciente(), novaFichaRequestDTO.idadePaciente());
        return new Ficha(paciente, novaFichaRequestDTO.sintomas(), preferencial);
    }

    private boolean verificarPrefencialidade(Integer idade) {
        return idade <= 18 || idade >= 65;
    }
}
