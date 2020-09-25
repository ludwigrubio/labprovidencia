package com.provi.lab.web.rest;

import com.provi.lab.domain.FQCrema;
import com.provi.lab.service.FQCremaService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.FQCremaCriteria;
import com.provi.lab.service.FQCremaQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.FQCrema}.
 */
@RestController
@RequestMapping("/api")
public class FQCremaResource {

    private final Logger log = LoggerFactory.getLogger(FQCremaResource.class);

    private static final String ENTITY_NAME = "fQCrema";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FQCremaService fQCremaService;

    private final FQCremaQueryService fQCremaQueryService;

    public FQCremaResource(FQCremaService fQCremaService, FQCremaQueryService fQCremaQueryService) {
        this.fQCremaService = fQCremaService;
        this.fQCremaQueryService = fQCremaQueryService;
    }

    /**
     * {@code POST  /fq-cremas} : Create a new fQCrema.
     *
     * @param fQCrema the fQCrema to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fQCrema, or with status {@code 400 (Bad Request)} if the fQCrema has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fq-cremas")
    public ResponseEntity<FQCrema> createFQCrema(@Valid @RequestBody FQCrema fQCrema) throws URISyntaxException {
        log.debug("REST request to save FQCrema : {}", fQCrema);
        if (fQCrema.getId() != null) {
            throw new BadRequestAlertException("A new fQCrema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FQCrema result = fQCremaService.save(fQCrema);
        return ResponseEntity.created(new URI("/api/fq-cremas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fq-cremas} : Updates an existing fQCrema.
     *
     * @param fQCrema the fQCrema to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fQCrema,
     * or with status {@code 400 (Bad Request)} if the fQCrema is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fQCrema couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fq-cremas")
    public ResponseEntity<FQCrema> updateFQCrema(@Valid @RequestBody FQCrema fQCrema) throws URISyntaxException {
        log.debug("REST request to update FQCrema : {}", fQCrema);
        if (fQCrema.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FQCrema result = fQCremaService.save(fQCrema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fQCrema.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fq-cremas} : get all the fQCremas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fQCremas in body.
     */
    @GetMapping("/fq-cremas")
    public ResponseEntity<List<FQCrema>> getAllFQCremas(FQCremaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FQCremas by criteria: {}", criteria);
        Page<FQCrema> page = fQCremaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fq-cremas/count} : count all the fQCremas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fq-cremas/count")
    public ResponseEntity<Long> countFQCremas(FQCremaCriteria criteria) {
        log.debug("REST request to count FQCremas by criteria: {}", criteria);
        return ResponseEntity.ok().body(fQCremaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fq-cremas/:id} : get the "id" fQCrema.
     *
     * @param id the id of the fQCrema to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fQCrema, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fq-cremas/{id}")
    public ResponseEntity<FQCrema> getFQCrema(@PathVariable Long id) {
        log.debug("REST request to get FQCrema : {}", id);
        Optional<FQCrema> fQCrema = fQCremaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fQCrema);
    }

    /**
     * {@code DELETE  /fq-cremas/:id} : delete the "id" fQCrema.
     *
     * @param id the id of the fQCrema to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fq-cremas/{id}")
    public ResponseEntity<Void> deleteFQCrema(@PathVariable Long id) {
        log.debug("REST request to delete FQCrema : {}", id);
        fQCremaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
