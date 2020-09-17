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

import com.provi.lab.domain.Dummy;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.DummyRepository;
import com.provi.lab.service.dto.DummyCriteria;

/**
 * Service for executing complex queries for {@link Dummy} entities in the database.
 * The main input is a {@link DummyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Dummy} or a {@link Page} of {@link Dummy} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DummyQueryService extends QueryService<Dummy> {

    private final Logger log = LoggerFactory.getLogger(DummyQueryService.class);

    private final DummyRepository dummyRepository;

    public DummyQueryService(DummyRepository dummyRepository) {
        this.dummyRepository = dummyRepository;
    }

    /**
     * Return a {@link List} of {@link Dummy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Dummy> findByCriteria(DummyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dummy> specification = createSpecification(criteria);
        return dummyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Dummy} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Dummy> findByCriteria(DummyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dummy> specification = createSpecification(criteria);
        return dummyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DummyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dummy> specification = createSpecification(criteria);
        return dummyRepository.count(specification);
    }

    /**
     * Function to convert {@link DummyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Dummy> createSpecification(DummyCriteria criteria) {
        Specification<Dummy> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Dummy_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Dummy_.name));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(Dummy_.area, JoinType.LEFT).get(Area_.id)));
            }
        }
        return specification;
    }
}
