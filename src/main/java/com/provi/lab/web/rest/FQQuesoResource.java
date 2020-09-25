package com.provi.lab.web.rest;

import com.provi.lab.domain.FQQueso;
import com.provi.lab.service.FQQuesoService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.FQQuesoCriteria;
import com.provi.lab.service.FQQuesoQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.FQQueso}.
 */
@RestController
@RequestMapping("/api")
public class FQQuesoResource {

    private final Logger log = LoggerFactory.getLogger(FQQuesoResource.class);

    private static final String ENTITY_NAME = "fQQueso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FQQuesoService fQQuesoService;

    private final FQQuesoQueryService fQQuesoQueryService;

    public FQQuesoResource(FQQuesoService fQQuesoService, FQQuesoQueryService fQQuesoQueryService) {
        this.fQQuesoService = fQQuesoService;
        this.fQQuesoQueryService = fQQuesoQueryService;
    }

    /**
     * {@code POST  /fq-quesos} : Create a new fQQueso.
     *
     * @param fQQueso the fQQueso to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fQQueso, or with status {@code 400 (Bad Request)} if the fQQueso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fq-quesos")
    public ResponseEntity<FQQueso> createFQQueso(@Valid @RequestBody FQQueso fQQueso) throws URISyntaxException {
        log.debug("REST request to save FQQueso : {}", fQQueso);
        if (fQQueso.getId() != null) {
            throw new BadRequestAlertException("A new fQQueso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FQQueso result = fQQuesoService.save(fQQueso);
        return ResponseEntity.created(new URI("/api/fq-quesos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fq-quesos} : Updates an existing fQQueso.
     *
     * @param fQQueso the fQQueso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fQQueso,
     * or with status {@code 400 (Bad Request)} if the fQQueso is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fQQueso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fq-quesos")
    public ResponseEntity<FQQueso> updateFQQueso(@Valid @RequestBody FQQueso fQQueso) throws URISyntaxException {
        log.debug("REST request to update FQQueso : {}", fQQueso);
        if (fQQueso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FQQueso result = fQQuesoService.save(fQQueso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fQQueso.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fq-quesos} : get all the fQQuesos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fQQuesos in body.
     */
    @GetMapping("/fq-quesos")
    public ResponseEntity<List<FQQueso>> getAllFQQuesos(FQQuesoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FQQuesos by criteria: {}", criteria);
        Page<FQQueso> page = fQQuesoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fq-quesos/count} : count all the fQQuesos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fq-quesos/count")
    public ResponseEntity<Long> countFQQuesos(FQQuesoCriteria criteria) {
        log.debug("REST request to count FQQuesos by criteria: {}", criteria);
        return ResponseEntity.ok().body(fQQuesoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fq-quesos/:id} : get the "id" fQQueso.
     *
     * @param id the id of the fQQueso to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fQQueso, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fq-quesos/{id}")
    public ResponseEntity<FQQueso> getFQQueso(@PathVariable Long id) {
        log.debug("REST request to get FQQueso : {}", id);
        Optional<FQQueso> fQQueso = fQQuesoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fQQueso);
    }

    /**
     * {@code DELETE  /fq-quesos/:id} : delete the "id" fQQueso.
     *
     * @param id the id of the fQQueso to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fq-quesos/{id}")
    public ResponseEntity<Void> deleteFQQueso(@PathVariable Long id) {
        log.debug("REST request to delete FQQueso : {}", id);
        fQQuesoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
