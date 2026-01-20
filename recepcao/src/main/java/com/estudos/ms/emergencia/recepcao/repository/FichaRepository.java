package com.estudos.ms.emergencia.recepcao.repository;

import com.estudos.ms.emergencia.recepcao.model.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Long> {
}
