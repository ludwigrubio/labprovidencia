package com.provi.lab.web.rest;

import com.provi.lab.domain.FQMantequilla;
import com.provi.lab.service.FQMantequillaService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.FQMantequillaCriteria;
import com.provi.lab.service.FQMantequillaQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.FQMantequilla}.
 */
@RestController
@RequestMapping("/api")
public class FQMantequillaResource {

    private final Logger log = LoggerFactory.getLogger(FQMantequillaResource.class);

    private static final String ENTITY_NAME = "fQMantequilla";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FQMantequillaService fQMantequillaService;

    private final FQMantequillaQueryService fQMantequillaQueryService;

    public FQMantequillaResource(FQMantequillaService fQMantequillaService, FQMantequillaQueryService fQMantequillaQueryService) {
        this.fQMantequillaService = fQMantequillaService;
        this.fQMantequillaQueryService = fQMantequillaQueryService;
    }

    /**
     * {@code POST  /fq-mantequillas} : Create a new fQMantequilla.
     *
     * @param fQMantequilla the fQMantequilla to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fQMantequilla, or with status {@code 400 (Bad Request)} if the fQMantequilla has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fq-mantequillas")
    public ResponseEntity<FQMantequilla> createFQMantequilla(@Valid @RequestBody FQMantequilla fQMantequilla) throws URISyntaxException {
        log.debug("REST request to save FQMantequilla : {}", fQMantequilla);
        if (fQMantequilla.getId() != null) {
            throw new BadRequestAlertException("A new fQMantequilla cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FQMantequilla result = fQMantequillaService.save(fQMantequilla);
        return ResponseEntity.created(new URI("/api/fq-mantequillas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fq-mantequillas} : Updates an existing fQMantequilla.
     *
     * @param fQMantequilla the fQMantequilla to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fQMantequilla,
     * or with status {@code 400 (Bad Request)} if the fQMantequilla is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fQMantequilla couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fq-mantequillas")
    public ResponseEntity<FQMantequilla> updateFQMantequilla(@Valid @RequestBody FQMantequilla fQMantequilla) throws URISyntaxException {
        log.debug("REST request to update FQMantequilla : {}", fQMantequilla);
        if (fQMantequilla.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FQMantequilla result = fQMantequillaService.save(fQMantequilla);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fQMantequilla.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fq-mantequillas} : get all the fQMantequillas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fQMantequillas in body.
     */
    @GetMapping("/fq-mantequillas")
    public ResponseEntity<List<FQMantequilla>> getAllFQMantequillas(FQMantequillaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FQMantequillas by criteria: {}", criteria);
        Page<FQMantequilla> page = fQMantequillaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fq-mantequillas/count} : count all the fQMantequillas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fq-mantequillas/count")
    public ResponseEntity<Long> countFQMantequillas(FQMantequillaCriteria criteria) {
        log.debug("REST request to count FQMantequillas by criteria: {}", criteria);
        return ResponseEntity.ok().body(fQMantequillaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fq-mantequillas/:id} : get the "id" fQMantequilla.
     *
     * @param id the id of the fQMantequilla to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fQMantequilla, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fq-mantequillas/{id}")
    public ResponseEntity<FQMantequilla> getFQMantequilla(@PathVariable Long id) {
        log.debug("REST request to get FQMantequilla : {}", id);
        Optional<FQMantequilla> fQMantequilla = fQMantequillaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fQMantequilla);
    }

    /**
     * {@code DELETE  /fq-mantequillas/:id} : delete the "id" fQMantequilla.
     *
     * @param id the id of the fQMantequilla to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fq-mantequillas/{id}")
    public ResponseEntity<Void> deleteFQMantequilla(@PathVariable Long id) {
        log.debug("REST request to delete FQMantequilla : {}", id);
        fQMantequillaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
