package com.provi.lab.web.rest;

import com.provi.lab.domain.Dummy;
import com.provi.lab.service.DummyService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.DummyCriteria;
import com.provi.lab.service.DummyQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.Dummy}.
 */
@RestController
@RequestMapping("/api")
public class DummyResource {

    private final Logger log = LoggerFactory.getLogger(DummyResource.class);

    private static final String ENTITY_NAME = "dummy";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DummyService dummyService;

    private final DummyQueryService dummyQueryService;

    public DummyResource(DummyService dummyService, DummyQueryService dummyQueryService) {
        this.dummyService = dummyService;
        this.dummyQueryService = dummyQueryService;
    }

    /**
     * {@code POST  /dummies} : Create a new dummy.
     *
     * @param dummy the dummy to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dummy, or with status {@code 400 (Bad Request)} if the dummy has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dummies")
    public ResponseEntity<Dummy> createDummy(@Valid @RequestBody Dummy dummy) throws URISyntaxException {
        log.debug("REST request to save Dummy : {}", dummy);
        if (dummy.getId() != null) {
            throw new BadRequestAlertException("A new dummy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dummy result = dummyService.save(dummy);
        return ResponseEntity.created(new URI("/api/dummies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dummies} : Updates an existing dummy.
     *
     * @param dummy the dummy to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dummy,
     * or with status {@code 400 (Bad Request)} if the dummy is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dummy couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dummies")
    public ResponseEntity<Dummy> updateDummy(@Valid @RequestBody Dummy dummy) throws URISyntaxException {
        log.debug("REST request to update Dummy : {}", dummy);
        if (dummy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dummy result = dummyService.save(dummy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dummy.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dummies} : get all the dummies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dummies in body.
     */
    @GetMapping("/dummies")
    public ResponseEntity<List<Dummy>> getAllDummies(DummyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Dummies by criteria: {}", criteria);
        Page<Dummy> page = dummyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dummies/count} : count all the dummies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dummies/count")
    public ResponseEntity<Long> countDummies(DummyCriteria criteria) {
        log.debug("REST request to count Dummies by criteria: {}", criteria);
        return ResponseEntity.ok().body(dummyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dummies/:id} : get the "id" dummy.
     *
     * @param id the id of the dummy to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dummy, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dummies/{id}")
    public ResponseEntity<Dummy> getDummy(@PathVariable Long id) {
        log.debug("REST request to get Dummy : {}", id);
        Optional<Dummy> dummy = dummyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dummy);
    }

    /**
     * {@code DELETE  /dummies/:id} : delete the "id" dummy.
     *
     * @param id the id of the dummy to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dummies/{id}")
    public ResponseEntity<Void> deleteDummy(@PathVariable Long id) {
        log.debug("REST request to delete Dummy : {}", id);
        dummyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
