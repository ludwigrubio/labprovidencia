package com.provi.lab.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.provi.lab.domain.LogLactoEscan;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.LogLactoEscanRepository;
import com.provi.lab.service.dto.LogLactoEscanCriteria;

/**
 * Service for executing complex queries for {@link LogLactoEscan} entities in the database.
 * The main input is a {@link LogLactoEscanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LogLactoEscan} or a {@link Page} of {@link LogLactoEscan} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LogLactoEscanQueryService extends QueryService<LogLactoEscan> {

    private final Logger log = LoggerFactory.getLogger(LogLactoEscanQueryService.class);

    private final LogLactoEscanRepository logLactoEscanRepository;

    public LogLactoEscanQueryService(LogLactoEscanRepository logLactoEscanRepository) {
        this.logLactoEscanRepository = logLactoEscanRepository;
    }

    /**
     * Return a {@link List} of {@link LogLactoEscan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LogLactoEscan> findByCriteria(LogLactoEscanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LogLactoEscan> specification = createSpecification(criteria);
        return logLactoEscanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LogLactoEscan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LogLactoEscan> findByCriteria(LogLactoEscanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LogLactoEscan> specification = createSpecification(criteria);
        return logLactoEscanRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LogLactoEscanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LogLactoEscan> specification = createSpecification(criteria);
        return logLactoEscanRepository.count(specification);
    }

    /**
     * Function to convert {@link LogLactoEscanCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LogLactoEscan> createSpecification(LogLactoEscanCriteria criteria) {
        Specification<LogLactoEscan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LogLactoEscan_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTipo(), LogLactoEscan_.tipo));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), LogLactoEscan_.fecha));
            }
            if (criteria.getNombreArchivo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreArchivo(), LogLactoEscan_.nombreArchivo));
            }
            if (criteria.getNumeroFila() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroFila(), LogLactoEscan_.numeroFila));
            }
            if (criteria.getMensajeError() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMensajeError(), LogLactoEscan_.mensajeError));
            }
        }
        return specification;
    }
}
