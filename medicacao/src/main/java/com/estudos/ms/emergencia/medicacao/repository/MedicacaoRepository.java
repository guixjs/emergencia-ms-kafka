package com.estudos.ms.emergencia.medicacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudos.ms.emergencia.medicacao.model.Medicacao;

@Repository
public interface MedicacaoRepository extends JpaRepository<Medicacao, Long> {

}
