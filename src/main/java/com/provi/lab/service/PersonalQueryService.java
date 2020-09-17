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

import com.provi.lab.domain.Personal;
import com.provi.lab.domain.*; // for static metamodels
import com.provi.lab.repository.PersonalRepository;
import com.provi.lab.service.dto.PersonalCriteria;

/**
 * Service for executing complex queries for {@link Personal} entities in the database.
 * The main input is a {@link PersonalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Personal} or a {@link Page} of {@link Personal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonalQueryService extends QueryService<Personal> {

    private final Logger log = LoggerFactory.getLogger(PersonalQueryService.class);

    private final PersonalRepository personalRepository;

    public PersonalQueryService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    /**
     * Return a {@link List} of {@link Personal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Personal> findByCriteria(PersonalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Personal> specification = createSpecification(criteria);
        return personalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Personal} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Personal> findByCriteria(PersonalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Personal> specification = createSpecification(criteria);
        return personalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Personal> specification = createSpecification(criteria);
        return personalRepository.count(specification);
    }

    /**
     * Function to convert {@link PersonalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Personal> createSpecification(PersonalCriteria criteria) {
        Specification<Personal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Personal_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Personal_.nombre));
            }
            if (criteria.getApellido1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellido1(), Personal_.apellido1));
            }
            if (criteria.getApellido2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApellido2(), Personal_.apellido2));
            }
            if (criteria.getAlias() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlias(), Personal_.alias));
            }
            if (criteria.getDomicilio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomicilio(), Personal_.domicilio));
            }
            if (criteria.getColonia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColonia(), Personal_.colonia));
            }
            if (criteria.getLocalidad() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalidad(), Personal_.localidad));
            }
            if (criteria.getEstado() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstado(), Personal_.estado));
            }
            if (criteria.getPais() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPais(), Personal_.pais));
            }
            if (criteria.getLatitud() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLatitud(), Personal_.latitud));
            }
            if (criteria.getLongitud() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongitud(), Personal_.longitud));
            }
            if (criteria.getCp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCp(), Personal_.cp));
            }
            if (criteria.getTelefono() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefono(), Personal_.telefono));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Personal_.email));
            }
            if (criteria.getRfc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRfc(), Personal_.rfc));
            }
            if (criteria.getInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInicio(), Personal_.inicio));
            }
            if (criteria.getFin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFin(), Personal_.fin));
            }
            if (criteria.getCargo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCargo(), Personal_.cargo));
            }
            if (criteria.getComentario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComentario(), Personal_.comentario));
            }
            if (criteria.getAreaId() != null) {
                specification = specification.and(buildSpecification(criteria.getAreaId(),
                    root -> root.join(Personal_.area, JoinType.LEFT).get(Area_.id)));
            }
            if (criteria.getDummyId() != null) {
                specification = specification.and(buildSpecification(criteria.getDummyId(),
                    root -> root.join(Personal_.dummy, JoinType.LEFT).get(Dummy_.id)));
            }
        }
        return specification;
    }
}
