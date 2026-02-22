package com.estudos.ms.emergencia.internacao.service;

import com.estudos.ms.emergencia.internacao.model.Internacao;
import com.estudos.ms.emergencia.internacao.repository.InternacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProcessarInternacaoService {

    private final InternacaoRepository internacaoRepository;


    public ProcessarInternacaoService(InternacaoRepository internacaoRepository) {
        this.internacaoRepository = internacaoRepository;
    }
    public void execute(Internacao internacao) {

        Internacao interna = internacaoRepository.save(internacao);

        internacaoRepository.save(interna);
        System.out.println(interna);

    }

}
