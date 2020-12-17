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

import com.provi.lab.domain.PruebaMicro;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.PruebaMicroRepository;
import com.provi.lab.service.dto.PruebaMicroCriteria;

/**
 * Service for executing complex queries for {@link PruebaMicro} entities in the database.
 * The main input is a {@link PruebaMicroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PruebaMicro} or a {@link Page} of {@link PruebaMicro} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PruebaMicroQueryService extends QueryService<PruebaMicro> {

    private final Logger log = LoggerFactory.getLogger(PruebaMicroQueryService.class);

    private final PruebaMicroRepository pruebaMicroRepository;

    public PruebaMicroQueryService(PruebaMicroRepository pruebaMicroRepository) {
        this.pruebaMicroRepository = pruebaMicroRepository;
    }

    /**
     * Return a {@link List} of {@link PruebaMicro} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PruebaMicro> findByCriteria(PruebaMicroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PruebaMicro> specification = createSpecification(criteria);
        return pruebaMicroRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PruebaMicro} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PruebaMicro> findByCriteria(PruebaMicroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PruebaMicro> specification = createSpecification(criteria);
        return pruebaMicroRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PruebaMicroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PruebaMicro> specification = createSpecification(criteria);
        return pruebaMicroRepository.count(specification);
    }

    /**
     * Function to convert {@link PruebaMicroCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PruebaMicro> createSpecification(PruebaMicroCriteria criteria) {
        Specification<PruebaMicro> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PruebaMicro_.id));
            }
            if (criteria.getTipodeMuestra() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTipodeMuestra(), PruebaMicro_.tipodeMuestra));
            }
            if (criteria.getLote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLote(), PruebaMicro_.lote));
            }
            if (criteria.getInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInicio(), PruebaMicro_.inicio));
            }
            if (criteria.getFin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFin(), PruebaMicro_.fin));
            }
            if (criteria.getResultado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResultado(), PruebaMicro_.resultado));
            }
            if (criteria.getUnidades() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnidades(), PruebaMicro_.unidades));
            }
            if (criteria.getObservaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservaciones(), PruebaMicro_.observaciones));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(PruebaMicro_.area, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getCultivoId() != null) {
                specification = specification.and(buildSpecification(criteria.getCultivoId(),
                    root -> root.join(PruebaMicro_.cultivo, JoinType.LEFT).get(Cultivo_.id)));
            }
            if (criteria.getSuperficieId() != null) {
                specification = specification.and(buildSpecification(criteria.getSuperficieId(),
                    root -> root.join(PruebaMicro_.superficie, JoinType.LEFT).get(Superficie_.id)));
            }
            if (criteria.getProductoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoId(),
                    root -> root.join(PruebaMicro_.producto, JoinType.LEFT).get(Producto_.id)));
            }
            if (criteria.getAnalistaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnalistaId(),
                    root -> root.join(PruebaMicro_.analista, JoinType.LEFT).get(UserExtra_.id)));
            }
            if (criteria.getProveedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getProveedorId(),
                    root -> root.join(PruebaMicro_.proveedor, JoinType.LEFT).get(Personal_.id)));
            }
            if (criteria.getProcesoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcesoId(),
                    root -> root.join(PruebaMicro_.proceso, JoinType.LEFT).get(Proceso_.id)));
            }
        }
        return specification;
    }
}
