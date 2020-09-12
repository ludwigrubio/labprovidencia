package com.provi.lab.web.rest;

import com.provi.lab.domain.Personal;
import com.provi.lab.service.PersonalService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.PersonalCriteria;
import com.provi.lab.service.PersonalQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.provi.lab.domain.Personal}.
 */
@RestController
@RequestMapping("/api")
public class PersonalResource {

    private final Logger log = LoggerFactory.getLogger(PersonalResource.class);

    private static final String ENTITY_NAME = "personal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalService personalService;

    private final PersonalQueryService personalQueryService;

    public PersonalResource(PersonalService personalService, PersonalQueryService personalQueryService) {
        this.personalService = personalService;
        this.personalQueryService = personalQueryService;
    }

    /**
     * {@code POST  /personals} : Create a new personal.
     *
     * @param personal the personal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personal, or with status {@code 400 (Bad Request)} if the personal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personals")
    public ResponseEntity<Personal> createPersonal(@Valid @RequestBody Personal personal) throws URISyntaxException {
        log.debug("REST request to save Personal : {}", personal);
        if (personal.getId() != null) {
            throw new BadRequestAlertException("A new personal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Personal result = personalService.save(personal);
        return ResponseEntity.created(new URI("/api/personals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personals} : Updates an existing personal.
     *
     * @param personal the personal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personal,
     * or with status {@code 400 (Bad Request)} if the personal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personals")
    public ResponseEntity<Personal> updatePersonal(@Valid @RequestBody Personal personal) throws URISyntaxException {
        log.debug("REST request to update Personal : {}", personal);
        if (personal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Personal result = personalService.save(personal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /personals} : get all the personals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personals in body.
     */
    @GetMapping("/personals")
    public ResponseEntity<List<Personal>> getAllPersonals(PersonalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Personals by criteria: {}", criteria);
        Page<Personal> page = personalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personals/count} : count all the personals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/personals/count")
    public ResponseEntity<Long> countPersonals(PersonalCriteria criteria) {
        log.debug("REST request to count Personals by criteria: {}", criteria);
        return ResponseEntity.ok().body(personalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /personals/:id} : get the "id" personal.
     *
     * @param id the id of the personal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personals/{id}")
    public ResponseEntity<Personal> getPersonal(@PathVariable Long id) {
        log.debug("REST request to get Personal : {}", id);
        Optional<Personal> personal = personalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personal);
    }

    /**
     * {@code DELETE  /personals/:id} : delete the "id" personal.
     *
     * @param id the id of the personal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personals/{id}")
    public ResponseEntity<Void> deletePersonal(@PathVariable Long id) {
        log.debug("REST request to delete Personal : {}", id);
        personalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
