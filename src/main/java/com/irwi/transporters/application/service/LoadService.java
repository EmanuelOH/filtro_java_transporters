package com.irwi.transporters.application.service;

import com.irwi.transporters.application.dtos.reponses.LoadDto;
import com.irwi.transporters.domain.entities.AuditLog;
import com.irwi.transporters.infrastructure.persistences.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoadService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void updateLoad(Long id, LoadDto loadDto, String user) {
        // Lógica para actualizar la carga...

        // Registrar auditoría
        AuditLog auditLog = new AuditLog();
        auditLog.setAction("UPDATE");
        auditLog.setEntity("Carga");
        auditLog.setEntityId(id);
        auditLog.setUser(user);
        auditLog.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }
}
