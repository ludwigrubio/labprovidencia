package com.provi.lab.web.rest;

import com.provi.lab.domain.FQSuero;
import com.provi.lab.service.FQSueroService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.FQSueroCriteria;
import com.provi.lab.service.FQSueroQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.FQSuero}.
 */
@RestController
@RequestMapping("/api")
public class FQSueroResource {

    private final Logger log = LoggerFactory.getLogger(FQSueroResource.class);

    private static final String ENTITY_NAME = "fQSuero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FQSueroService fQSueroService;

    private final FQSueroQueryService fQSueroQueryService;

    public FQSueroResource(FQSueroService fQSueroService, FQSueroQueryService fQSueroQueryService) {
        this.fQSueroService = fQSueroService;
        this.fQSueroQueryService = fQSueroQueryService;
    }

    /**
     * {@code POST  /fq-sueros} : Create a new fQSuero.
     *
     * @param fQSuero the fQSuero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fQSuero, or with status {@code 400 (Bad Request)} if the fQSuero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fq-sueros")
    public ResponseEntity<FQSuero> createFQSuero(@Valid @RequestBody FQSuero fQSuero) throws URISyntaxException {
        log.debug("REST request to save FQSuero : {}", fQSuero);
        if (fQSuero.getId() != null) {
            throw new BadRequestAlertException("A new fQSuero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FQSuero result = fQSueroService.save(fQSuero);
        return ResponseEntity.created(new URI("/api/fq-sueros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fq-sueros} : Updates an existing fQSuero.
     *
     * @param fQSuero the fQSuero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fQSuero,
     * or with status {@code 400 (Bad Request)} if the fQSuero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fQSuero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fq-sueros")
    public ResponseEntity<FQSuero> updateFQSuero(@Valid @RequestBody FQSuero fQSuero) throws URISyntaxException {
        log.debug("REST request to update FQSuero : {}", fQSuero);
        if (fQSuero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FQSuero result = fQSueroService.save(fQSuero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fQSuero.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fq-sueros} : get all the fQSueros.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fQSueros in body.
     */
    @GetMapping("/fq-sueros")
    public ResponseEntity<List<FQSuero>> getAllFQSueros(FQSueroCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FQSueros by criteria: {}", criteria);
        Page<FQSuero> page = fQSueroQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fq-sueros/count} : count all the fQSueros.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fq-sueros/count")
    public ResponseEntity<Long> countFQSueros(FQSueroCriteria criteria) {
        log.debug("REST request to count FQSueros by criteria: {}", criteria);
        return ResponseEntity.ok().body(fQSueroQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fq-sueros/:id} : get the "id" fQSuero.
     *
     * @param id the id of the fQSuero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fQSuero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fq-sueros/{id}")
    public ResponseEntity<FQSuero> getFQSuero(@PathVariable Long id) {
        log.debug("REST request to get FQSuero : {}", id);
        Optional<FQSuero> fQSuero = fQSueroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fQSuero);
    }

    /**
     * {@code DELETE  /fq-sueros/:id} : delete the "id" fQSuero.
     *
     * @param id the id of the fQSuero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fq-sueros/{id}")
    public ResponseEntity<Void> deleteFQSuero(@PathVariable Long id) {
        log.debug("REST request to delete FQSuero : {}", id);
        fQSueroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
