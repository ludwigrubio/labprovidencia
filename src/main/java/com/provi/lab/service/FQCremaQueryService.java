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

import com.provi.lab.domain.FQCrema;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.FQCremaRepository;
import com.provi.lab.service.dto.FQCremaCriteria;

/**
 * Service for executing complex queries for {@link FQCrema} entities in the database.
 * The main input is a {@link FQCremaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FQCrema} or a {@link Page} of {@link FQCrema} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FQCremaQueryService extends QueryService<FQCrema> {

    private final Logger log = LoggerFactory.getLogger(FQCremaQueryService.class);

    private final FQCremaRepository fQCremaRepository;

    public FQCremaQueryService(FQCremaRepository fQCremaRepository) {
        this.fQCremaRepository = fQCremaRepository;
    }

    /**
     * Return a {@link List} of {@link FQCrema} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FQCrema> findByCriteria(FQCremaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FQCrema> specification = createSpecification(criteria);
        return fQCremaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FQCrema} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FQCrema> findByCriteria(FQCremaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FQCrema> specification = createSpecification(criteria);
        return fQCremaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FQCremaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FQCrema> specification = createSpecification(criteria);
        return fQCremaRepository.count(specification);
    }

    /**
     * Function to convert {@link FQCremaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FQCrema> createSpecification(FQCremaCriteria criteria) {
        Specification<FQCrema> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FQCrema_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), FQCrema_.fecha));
            }
            if (criteria.getLote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLote(), FQCrema_.lote));
            }
            if (criteria.getAcidez() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcidez(), FQCrema_.acidez));
            }
            if (criteria.getGrasa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrasa(), FQCrema_.grasa));
            }
            if (criteria.getPh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPh(), FQCrema_.ph));
            }
            if (criteria.getDummy1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy1(), FQCrema_.dummy1));
            }
            if (criteria.getDummy2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy2(), FQCrema_.dummy2));
            }
            if (criteria.getDummy3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy3(), FQCrema_.dummy3));
            }
            if (criteria.getDummy4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy4(), FQCrema_.dummy4));
            }
            if (criteria.getDummy5() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy5(), FQCrema_.dummy5));
            }
            if (criteria.getObservaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservaciones(), FQCrema_.observaciones));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(FQCrema_.area, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getProductoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoId(),
                    root -> root.join(FQCrema_.producto, JoinType.LEFT).get(Producto_.id)));
            }
            if (criteria.getAnalistaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnalistaId(),
                    root -> root.join(FQCrema_.analista, JoinType.LEFT).get(UserExtra_.id)));
            }
            if (criteria.getProveedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getProveedorId(),
                    root -> root.join(FQCrema_.proveedor, JoinType.LEFT).get(Personal_.id)));
            }
            if (criteria.getContenedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getContenedorId(),
                    root -> root.join(FQCrema_.contenedor, JoinType.LEFT).get(Contenedor_.id)));
            }
        }
        return specification;
    }
}
