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

import com.provi.lab.domain.FQMantequilla;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.FQMantequillaRepository;
import com.provi.lab.service.dto.FQMantequillaCriteria;

/**
 * Service for executing complex queries for {@link FQMantequilla} entities in the database.
 * The main input is a {@link FQMantequillaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FQMantequilla} or a {@link Page} of {@link FQMantequilla} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FQMantequillaQueryService extends QueryService<FQMantequilla> {

    private final Logger log = LoggerFactory.getLogger(FQMantequillaQueryService.class);

    private final FQMantequillaRepository fQMantequillaRepository;

    public FQMantequillaQueryService(FQMantequillaRepository fQMantequillaRepository) {
        this.fQMantequillaRepository = fQMantequillaRepository;
    }

    /**
     * Return a {@link List} of {@link FQMantequilla} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FQMantequilla> findByCriteria(FQMantequillaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FQMantequilla> specification = createSpecification(criteria);
        return fQMantequillaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FQMantequilla} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FQMantequilla> findByCriteria(FQMantequillaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FQMantequilla> specification = createSpecification(criteria);
        return fQMantequillaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FQMantequillaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FQMantequilla> specification = createSpecification(criteria);
        return fQMantequillaRepository.count(specification);
    }

    /**
     * Function to convert {@link FQMantequillaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FQMantequilla> createSpecification(FQMantequillaCriteria criteria) {
        Specification<FQMantequilla> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FQMantequilla_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), FQMantequilla_.fecha));
            }
            if (criteria.getLote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLote(), FQMantequilla_.lote));
            }
            if (criteria.getPh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPh(), FQMantequilla_.ph));
            }
            if (criteria.getHumedad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHumedad(), FQMantequilla_.humedad));
            }
            if (criteria.getObservaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservaciones(), FQMantequilla_.observaciones));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(FQMantequilla_.area, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getProductoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoId(),
                    root -> root.join(FQMantequilla_.producto, JoinType.LEFT).get(Producto_.id)));
            }
            if (criteria.getAnalistaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnalistaId(),
                    root -> root.join(FQMantequilla_.analista, JoinType.LEFT).get(UserExtra_.id)));
            }
            if (criteria.getProveedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getProveedorId(),
                    root -> root.join(FQMantequilla_.proveedor, JoinType.LEFT).get(Personal_.id)));
            }
        }
        return specification;
    }
}
