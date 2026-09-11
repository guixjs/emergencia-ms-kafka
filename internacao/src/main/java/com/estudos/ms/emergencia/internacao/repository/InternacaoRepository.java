package com.estudos.ms.emergencia.internacao.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudos.ms.emergencia.internacao.model.Internacao;

@Repository
public interface InternacaoRepository extends JpaRepository<Internacao, Long> {

  List<Internacao> findByDataHoraFimInternacaoBeforeAndInternacaoFinalizadaFalse(LocalDateTime agora);
}
