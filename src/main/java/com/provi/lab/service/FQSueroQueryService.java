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

import com.provi.lab.domain.FQSuero;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.FQSueroRepository;
import com.provi.lab.service.dto.FQSueroCriteria;

/**
 * Service for executing complex queries for {@link FQSuero} entities in the database.
 * The main input is a {@link FQSueroCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FQSuero} or a {@link Page} of {@link FQSuero} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FQSueroQueryService extends QueryService<FQSuero> {

    private final Logger log = LoggerFactory.getLogger(FQSueroQueryService.class);

    private final FQSueroRepository fQSueroRepository;

    public FQSueroQueryService(FQSueroRepository fQSueroRepository) {
        this.fQSueroRepository = fQSueroRepository;
    }

    /**
     * Return a {@link List} of {@link FQSuero} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FQSuero> findByCriteria(FQSueroCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FQSuero> specification = createSpecification(criteria);
        return fQSueroRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FQSuero} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FQSuero> findByCriteria(FQSueroCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FQSuero> specification = createSpecification(criteria);
        return fQSueroRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FQSueroCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FQSuero> specification = createSpecification(criteria);
        return fQSueroRepository.count(specification);
    }

    /**
     * Function to convert {@link FQSueroCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FQSuero> createSpecification(FQSueroCriteria criteria) {
        Specification<FQSuero> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FQSuero_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), FQSuero_.fecha));
            }
            if (criteria.getLote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLote(), FQSuero_.lote));
            }
            if (criteria.getAcidez() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcidez(), FQSuero_.acidez));
            }
            if (criteria.getTemperatura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperatura(), FQSuero_.temperatura));
            }
            if (criteria.getDelvo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelvo(), FQSuero_.delvo));
            }
            if (criteria.getSolidos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSolidos(), FQSuero_.solidos));
            }
            if (criteria.getNeutralizantes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNeutralizantes(), FQSuero_.neutralizantes));
            }
            if (criteria.getPh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPh(), FQSuero_.ph));
            }
            if (criteria.getCloro() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCloro(), FQSuero_.cloro));
            }
            if (criteria.getAlmidon() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAlmidon(), FQSuero_.almidon));
            }
            if (criteria.getDummy1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy1(), FQSuero_.dummy1));
            }
            if (criteria.getDummy2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy2(), FQSuero_.dummy2));
            }
            if (criteria.getDummy3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy3(), FQSuero_.dummy3));
            }
            if (criteria.getDummy4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy4(), FQSuero_.dummy4));
            }
            if (criteria.getDummy5() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy5(), FQSuero_.dummy5));
            }
            if (criteria.getObservaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservaciones(), FQSuero_.observaciones));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(FQSuero_.area, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getProductoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductoId(),
                    root -> root.join(FQSuero_.producto, JoinType.LEFT).get(Producto_.id)));
            }
            if (criteria.getAnalistaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnalistaId(),
                    root -> root.join(FQSuero_.analista, JoinType.LEFT).get(UserExtra_.id)));
            }
            if (criteria.getProveedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getProveedorId(),
                    root -> root.join(FQSuero_.proveedor, JoinType.LEFT).get(Personal_.id)));
            }
            if (criteria.getContenedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getContenedorId(),
                    root -> root.join(FQSuero_.contenedor, JoinType.LEFT).get(Contenedor_.id)));
            }
        }
        return specification;
    }
}
