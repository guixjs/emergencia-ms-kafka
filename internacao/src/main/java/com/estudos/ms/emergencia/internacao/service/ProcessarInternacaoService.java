package com.estudos.ms.emergencia.internacao.service;

import com.estudos.ms.emergencia.internacao.model.Internacao;
import org.springframework.stereotype.Service;

@Service
public class ProcessarInternacaoService {

    public void execute(Internacao internacaoDTO) {

        System.out.println("Processando internação para o paciente: ");
        System.out.println(internacaoDTO.getFicha().getInfoPaciente().getNome());


        System.out.println("---------");
        System.out.println(internacaoDTO.toString());



    }
}
