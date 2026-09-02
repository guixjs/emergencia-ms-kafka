package com.estudos.ms.emergencia.auditoria.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.estudos.ms.emergencia.auditoria.model.EventoAuditoria;

public interface AuditoriaRepository extends MongoRepository<EventoAuditoria, String>{
  
}
