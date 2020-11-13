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

import com.provi.lab.domain.Contenedor;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.ContenedorRepository;
import com.provi.lab.service.dto.ContenedorCriteria;

/**
 * Service for executing complex queries for {@link Contenedor} entities in the database.
 * The main input is a {@link ContenedorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Contenedor} or a {@link Page} of {@link Contenedor} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContenedorQueryService extends QueryService<Contenedor> {

    private final Logger log = LoggerFactory.getLogger(ContenedorQueryService.class);

    private final ContenedorRepository contenedorRepository;

    public ContenedorQueryService(ContenedorRepository contenedorRepository) {
        this.contenedorRepository = contenedorRepository;
    }

    /**
     * Return a {@link List} of {@link Contenedor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Contenedor> findByCriteria(ContenedorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contenedor> specification = createSpecification(criteria);
        return contenedorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Contenedor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Contenedor> findByCriteria(ContenedorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contenedor> specification = createSpecification(criteria);
        return contenedorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContenedorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contenedor> specification = createSpecification(criteria);
        return contenedorRepository.count(specification);
    }

    /**
     * Function to convert {@link ContenedorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Contenedor> createSpecification(ContenedorCriteria criteria) {
        Specification<Contenedor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Contenedor_.id));
            }
            if (criteria.getContenedor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContenedor(), Contenedor_.contenedor));
            }
        }
        return specification;
    }
}
