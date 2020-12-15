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

import com.provi.lab.domain.FQLeche;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.FQLecheRepository;
import com.provi.lab.service.dto.FQLecheCriteria;

/**
 * Service for executing complex queries for {@link FQLeche} entities in the database.
 * The main input is a {@link FQLecheCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FQLeche} or a {@link Page} of {@link FQLeche} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FQLecheQueryService extends QueryService<FQLeche> {

    private final Logger log = LoggerFactory.getLogger(FQLecheQueryService.class);

    private final FQLecheRepository fQLecheRepository;

    public FQLecheQueryService(FQLecheRepository fQLecheRepository) {
        this.fQLecheRepository = fQLecheRepository;
    }

    /**
     * Return a {@link List} of {@link FQLeche} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FQLeche> findByCriteria(FQLecheCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FQLeche> specification = createSpecification(criteria);
        return fQLecheRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FQLeche} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FQLeche> findByCriteria(FQLecheCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FQLeche> specification = createSpecification(criteria);
        return fQLecheRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FQLecheCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FQLeche> specification = createSpecification(criteria);
        return fQLecheRepository.count(specification);
    }

    /**
     * Function to convert {@link FQLecheCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FQLeche> createSpecification(FQLecheCriteria criteria) {
        Specification<FQLeche> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FQLeche_.id));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), FQLeche_.fecha));
            }
            if (criteria.getLote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLote(), FQLeche_.lote));
            }
            if (criteria.getAcidez() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcidez(), FQLeche_.acidez));
            }
            if (criteria.getTemperatura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperatura(), FQLeche_.temperatura));
            }
            if (criteria.getAgua() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAgua(), FQLeche_.agua));
            }
            if (criteria.getCrioscopia() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCrioscopia(), FQLeche_.crioscopia));
            }
            if (criteria.getAntibiotico() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAntibiotico(), FQLeche_.antibiotico));
            }
            if (criteria.getDelvo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelvo(), FQLeche_.delvo));
            }
            if (criteria.getGrasa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrasa(), FQLeche_.grasa));
            }
            if (criteria.getSolidos() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSolidos(), FQLeche_.solidos));
            }
            if (criteria.getDensidad() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDensidad(), FQLeche_.densidad));
            }
            if (criteria.getLactosa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLactosa(), FQLeche_.lactosa));
            }
            if (criteria.getProteina() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProteina(), FQLeche_.proteina));
            }
            if (criteria.getNeutralizantes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNeutralizantes(), FQLeche_.neutralizantes));
            }
            if (criteria.getAdulterantes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdulterantes(), FQLeche_.adulterantes));
            }
            if (criteria.getReductasa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReductasa(), FQLeche_.reductasa));
            }
            if (criteria.getFosfatasa() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFosfatasa(), FQLeche_.fosfatasa));
            }
            if (criteria.getPh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPh(), FQLeche_.ph));
            }
            if (criteria.getDummy1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy1(), FQLeche_.dummy1));
            }
            if (criteria.getDummy2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy2(), FQLeche_.dummy2));
            }
            if (criteria.getDummy3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy3(), FQLeche_.dummy3));
            }
            if (criteria.getDummy4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy4(), FQLeche_.dummy4));
            }
            if (criteria.getDummy5() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDummy5(), FQLeche_.dummy5));
            }
            if (criteria.getObservaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservaciones(), FQLeche_.observaciones));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(FQLeche_.area, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getRecepcionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRecepcionId(),
                    root -> root.join(FQLeche_.recepcion, JoinType.LEFT).get(Recepcion_.id)));
            }
            if (criteria.getAnalistaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAnalistaId(),
                    root -> root.join(FQLeche_.analista, JoinType.LEFT).get(UserExtra_.id)));
            }
            if (criteria.getProveedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getProveedorId(),
                    root -> root.join(FQLeche_.proveedor, JoinType.LEFT).get(Personal_.id)));
            }
            if (criteria.getContenedorId() != null) {
                specification = specification.and(buildSpecification(criteria.getContenedorId(),
                    root -> root.join(FQLeche_.contenedor, JoinType.LEFT).get(Contenedor_.id)));
            }
            if (criteria.getProcesoId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcesoId(),
                    root -> root.join(FQLeche_.proceso, JoinType.LEFT).get(Proceso_.id)));
            }
        }
        return specification;
    }
}
