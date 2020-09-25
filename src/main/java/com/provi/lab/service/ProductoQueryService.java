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

import com.provi.lab.domain.Producto;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.ProductoRepository;
import com.provi.lab.service.dto.ProductoCriteria;

/**
 * Service for executing complex queries for {@link Producto} entities in the database.
 * The main input is a {@link ProductoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Producto} or a {@link Page} of {@link Producto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductoQueryService extends QueryService<Producto> {

    private final Logger log = LoggerFactory.getLogger(ProductoQueryService.class);

    private final ProductoRepository productoRepository;

    public ProductoQueryService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Return a {@link List} of {@link Producto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Producto> findByCriteria(ProductoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Producto> specification = createSpecification(criteria);
        return productoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Producto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Producto> findByCriteria(ProductoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Producto> specification = createSpecification(criteria);
        return productoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Producto> specification = createSpecification(criteria);
        return productoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Producto> createSpecification(ProductoCriteria criteria) {
        Specification<Producto> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Producto_.id));
            }
            if (criteria.getProducto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProducto(), Producto_.producto));
            }
            if (criteria.getClave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClave(), Producto_.clave));
            }
            if (criteria.getSerieEAN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerieEAN(), Producto_.serieEAN));
            }
            if (criteria.getTipoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoId(),
                    root -> root.join(Producto_.tipo, JoinType.LEFT).get(TipoProducto_.id)));
            }
        }
        return specification;
    }
}
