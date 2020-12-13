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

import com.provi.lab.domain.Recepcion;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.RecepcionRepository;
import com.provi.lab.service.dto.RecepcionCriteria;

/**
 * Service for executing complex queries for {@link Recepcion} entities in the database.
 * The main input is a {@link RecepcionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Recepcion} or a {@link Page} of {@link Recepcion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RecepcionQueryService extends QueryService<Recepcion> {

    private final Logger log = LoggerFactory.getLogger(RecepcionQueryService.class);

    private final RecepcionRepository recepcionRepository;

    public RecepcionQueryService(RecepcionRepository recepcionRepository) {
        this.recepcionRepository = recepcionRepository;
    }

    /**
     * Return a {@link List} of {@link Recepcion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Recepcion> findByCriteria(RecepcionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Recepcion> specification = createSpecification(criteria);
        return recepcionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Recepcion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Recepcion> findByCriteria(RecepcionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Recepcion> specification = createSpecification(criteria);
        return recepcionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RecepcionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Recepcion> specification = createSpecification(criteria);
        return recepcionRepository.count(specification);
    }

    /**
     * Function to convert {@link RecepcionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Recepcion> createSpecification(RecepcionCriteria criteria) {
        Specification<Recepcion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Recepcion_.id));
            }
            if (criteria.getLitros() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLitros(), Recepcion_.litros));
            }
            if (criteria.getTiempo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTiempo(), Recepcion_.tiempo));
            }
            if (criteria.getTurno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTurno(), Recepcion_.turno));
            }
            if (criteria.getIncentivoLT() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIncentivoLT(), Recepcion_.incentivoLT));
            }
            if (criteria.getIncentivoT() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIncentivoT(), Recepcion_.incentivoT));
            }
            if (criteria.getTipoLeche() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoLeche(), Recepcion_.tipoLeche));
            }
            if (criteria.getFlete() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlete(), Recepcion_.flete));
            }
            if (criteria.getProveedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getProveedorId(),
                    root -> root.join(Recepcion_.proveedor, JoinType.LEFT).get(Personal_.id)));
            }
        }
        return specification;
    }
}
