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

import com.provi.lab.domain.FQQueso;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.FQQuesoRepository;
import com.provi.lab.service.dto.FQQuesoCriteria;

/**
 * Service for executing complex queries for {@link FQQueso} entities in the database.
 * The main input is a {@link FQQuesoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FQQueso} or a {@link Page} of {@link FQQueso} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FQQuesoQueryService extends QueryService<FQQueso> {

    private final Logger log = LoggerFactory.getLogger(FQQuesoQueryService.class);

    private final FQQuesoRepository fQQuesoRepository;

    public FQQuesoQueryService(FQQuesoRepository fQQuesoRepository) {
        this.fQQuesoRepository = fQQuesoRepository;
    }

    /**
     * Return a {@link List} of {@link FQQueso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FQQueso> findByCriteria(FQQuesoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FQQueso> specification = createSpecification(criteria);
        return fQQuesoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FQQueso} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FQQueso> findByCriteria(FQQuesoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FQQueso> specification = createSpecification(criteria);
        return fQQuesoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FQQuesoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FQQueso> specification = createSpecification(criteria);
        return fQQuesoRepository.count(specification);
    }

    /**
     * Function to convert {@link FQQuesoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FQQueso> createSpecification(FQQuesoCriteria criteria) {
        Specification<FQQueso> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FQQueso_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), FQQueso_.fecha));
            }
            if (criteria.getLote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLote(), FQQueso_.lote));
            }
            if (criteria.getHumedad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHumedad(), FQQueso_.humedad));
            }
            if (criteria.getPh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPh(), FQQueso_.ph));
            }
            if (criteria.getFundicion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFundicion(), FQQueso_.fundicion));
            }
            if (criteria.getPresentacion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPresentacion(), FQQueso_.presentacion));
            }
            if (criteria.getCaducidad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCaducidad(), FQQueso_.caducidad));
            }
            if (criteria.getApariencia() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApariencia(), FQQueso_.apariencia));
            }
            if (criteria.getSabor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSabor(), FQQueso_.sabor));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getColor(), FQQueso_.color));
            }
            if (criteria.getOlor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOlor(), FQQueso_.olor));
            }
            if (criteria.getTextura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTextura(), FQQueso_.textura));
            }
            if (criteria.getHilado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHilado(), FQQueso_.hilado));
            }
            if (criteria.getDummy1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy1(), FQQueso_.dummy1));
            }
            if (criteria.getDummy2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy2(), FQQueso_.dummy2));
            }
            if (criteria.getDummy3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy3(), FQQueso_.dummy3));
            }
            if (criteria.getDummy4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy4(), FQQueso_.dummy4));
            }
            if (criteria.getDummy5() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy5(), FQQueso_.dummy5));
            }
            if (criteria.getObservaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservaciones(), FQQueso_.observaciones));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(FQQueso_.area, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getProductoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoId(),
                    root -> root.join(FQQueso_.producto, JoinType.LEFT).get(Producto_.id)));
            }
            if (criteria.getAnalistaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnalistaId(),
                    root -> root.join(FQQueso_.analista, JoinType.LEFT).get(UserExtra_.id)));
            }
            if (criteria.getProveedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getProveedorId(),
                    root -> root.join(FQQueso_.proveedor, JoinType.LEFT).get(Personal_.id)));
            }
            if (criteria.getContenedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getContenedorId(),
                    root -> root.join(FQQueso_.contenedor, JoinType.LEFT).get(Contenedor_.id)));
            }
            if (criteria.getProcesoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcesoId(),
                    root -> root.join(FQQueso_.proceso, JoinType.LEFT).get(Proceso_.id)));
            }
        }
        return specification;
    }
}
