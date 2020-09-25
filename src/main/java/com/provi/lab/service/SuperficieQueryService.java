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

import com.provi.lab.domain.Superficie;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.SuperficieRepository;
import com.provi.lab.service.dto.SuperficieCriteria;

/**
 * Service for executing complex queries for {@link Superficie} entities in the database.
 * The main input is a {@link SuperficieCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Superficie} or a {@link Page} of {@link Superficie} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SuperficieQueryService extends QueryService<Superficie> {

    private final Logger log = LoggerFactory.getLogger(SuperficieQueryService.class);

    private final SuperficieRepository superficieRepository;

    public SuperficieQueryService(SuperficieRepository superficieRepository) {
        this.superficieRepository = superficieRepository;
    }

    /**
     * Return a {@link List} of {@link Superficie} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Superficie> findByCriteria(SuperficieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Superficie> specification = createSpecification(criteria);
        return superficieRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Superficie} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Superficie> findByCriteria(SuperficieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Superficie> specification = createSpecification(criteria);
        return superficieRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SuperficieCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Superficie> specification = createSpecification(criteria);
        return superficieRepository.count(specification);
    }

    /**
     * Function to convert {@link SuperficieCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Superficie> createSpecification(SuperficieCriteria criteria) {
        Specification<Superficie> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Superficie_.id));
            }
            if (criteria.getSuperficie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSuperficie(), Superficie_.superficie));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Superficie_.descripcion));
            }
        }
        return specification;
    }
}
