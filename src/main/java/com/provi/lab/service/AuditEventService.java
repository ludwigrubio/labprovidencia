package com.provi.lab.service;

import io.github.jhipster.config.JHipsterProperties;
import com.provi.lab.config.audit.AuditEventConverter;
import com.provi.lab.repository.PersistenceAuditEventRepository;
import com.provi.lab.repository.EntityAuditEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Service for managing audit events.
 * <p>
 * This is the default implementation to support SpringBoot Actuator {@code AuditEventRepository}.
 */
@Service
@Transactional
public class AuditEventService {

    private final Logger log = LoggerFactory.getLogger(AuditEventService.class);

    private final JHipsterProperties jHipsterProperties;

    private final PersistenceAuditEventRepository persistenceAuditEventRepository;

    private final EntityAuditEventRepository entityAuditEventRepository;

    private final AuditEventConverter auditEventConverter;

    public AuditEventService(
        PersistenceAuditEventRepository persistenceAuditEventRepository,
        AuditEventConverter auditEventConverter,
        EntityAuditEventRepository entityAuditEventRepository,
        JHipsterProperties jhipsterProperties) {

        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.entityAuditEventRepository = entityAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
        this.jHipsterProperties = jhipsterProperties;
    }

    /**
     * Old audit events should be automatically deleted after 30 days.
     *
     * This is scheduled to get fired at 12:00 (am).
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void removeOldAuditEvents() {
        persistenceAuditEventRepository
            .findByAuditEventDateBefore(Instant.now().minus(jHipsterProperties.getAuditEvents().getRetentionPeriod(), ChronoUnit.DAYS))
            .forEach(auditEvent -> {
                log.debug("Deleting audit data {}", auditEvent);
                persistenceAuditEventRepository.delete(auditEvent);
            });
    }

    /**
     * Old entity audit events should be automatically deleted after 6 months.
     *
     * This is scheduled to get fired at 12:00 (am).
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void removeOldEntityAuditEvents() {
        log.debug("Deleting Audit Entity less than {}",Instant.now().minus(183, ChronoUnit.DAYS).toString());
        entityAuditEventRepository
            .findByModifiedDateBefore(Instant.now().minus(183, ChronoUnit.DAYS))
            .forEach(entityAuditEvent -> {
                log.debug("Deleting entity audit data {}", entityAuditEvent);
                entityAuditEventRepository.delete(entityAuditEvent);
            });
    }

    @Transactional(readOnly = true)
    public Page<AuditEvent> findAll(Pageable pageable) {
        return persistenceAuditEventRepository.findAll(pageable)
            .map(auditEventConverter::convertToAuditEvent);
    }

    @Transactional(readOnly = true)
    public Page<AuditEvent> findByDates(Instant fromDate, Instant toDate, Pageable pageable) {
        return persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate, pageable)
            .map(auditEventConverter::convertToAuditEvent);
    }

    @Transactional(readOnly = true)
    public Optional<AuditEvent> find(Long id) {
        return persistenceAuditEventRepository.findById(id)
            .map(auditEventConverter::convertToAuditEvent);
    }
}
