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

import com.provi.lab.domain.Cultivo;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.CultivoRepository;
import com.provi.lab.service.dto.CultivoCriteria;

/**
 * Service for executing complex queries for {@link Cultivo} entities in the database.
 * The main input is a {@link CultivoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Cultivo} or a {@link Page} of {@link Cultivo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CultivoQueryService extends QueryService<Cultivo> {

    private final Logger log = LoggerFactory.getLogger(CultivoQueryService.class);

    private final CultivoRepository cultivoRepository;

    public CultivoQueryService(CultivoRepository cultivoRepository) {
        this.cultivoRepository = cultivoRepository;
    }

    /**
     * Return a {@link List} of {@link Cultivo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Cultivo> findByCriteria(CultivoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cultivo> specification = createSpecification(criteria);
        return cultivoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Cultivo} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Cultivo> findByCriteria(CultivoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cultivo> specification = createSpecification(criteria);
        return cultivoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CultivoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cultivo> specification = createSpecification(criteria);
        return cultivoRepository.count(specification);
    }

    /**
     * Function to convert {@link CultivoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cultivo> createSpecification(CultivoCriteria criteria) {
        Specification<Cultivo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cultivo_.id));
            }
            if (criteria.getCultivo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCultivo(), Cultivo_.cultivo));
            }
        }
        return specification;
    }
}
