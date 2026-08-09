package com.estudos.ms.emergencia.alta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudos.ms.emergencia.alta.model.Alta;

@Repository
public interface AltaRepository extends JpaRepository<Alta, Long> {
  
}
