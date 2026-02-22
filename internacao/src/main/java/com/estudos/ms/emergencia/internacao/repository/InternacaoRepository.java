package com.estudos.ms.emergencia.internacao.repository;

import com.estudos.ms.emergencia.internacao.model.Internacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternacaoRepository extends JpaRepository<Internacao, String> {
}
