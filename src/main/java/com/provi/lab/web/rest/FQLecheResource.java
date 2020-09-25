package com.provi.lab.web.rest;

import com.provi.lab.domain.FQLeche;
import com.provi.lab.service.FQLecheService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.FQLecheCriteria;
import com.provi.lab.service.FQLecheQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.FQLeche}.
 */
@RestController
@RequestMapping("/api")
public class FQLecheResource {

    private final Logger log = LoggerFactory.getLogger(FQLecheResource.class);

    private static final String ENTITY_NAME = "fQLeche";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FQLecheService fQLecheService;

    private final FQLecheQueryService fQLecheQueryService;

    public FQLecheResource(FQLecheService fQLecheService, FQLecheQueryService fQLecheQueryService) {
        this.fQLecheService = fQLecheService;
        this.fQLecheQueryService = fQLecheQueryService;
    }

    /**
     * {@code POST  /fq-leches} : Create a new fQLeche.
     *
     * @param fQLeche the fQLeche to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fQLeche, or with status {@code 400 (Bad Request)} if the fQLeche has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fq-leches")
    public ResponseEntity<FQLeche> createFQLeche(@Valid @RequestBody FQLeche fQLeche) throws URISyntaxException {
        log.debug("REST request to save FQLeche : {}", fQLeche);
        if (fQLeche.getId() != null) {
            throw new BadRequestAlertException("A new fQLeche cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FQLeche result = fQLecheService.save(fQLeche);
        return ResponseEntity.created(new URI("/api/fq-leches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fq-leches} : Updates an existing fQLeche.
     *
     * @param fQLeche the fQLeche to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fQLeche,
     * or with status {@code 400 (Bad Request)} if the fQLeche is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fQLeche couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fq-leches")
    public ResponseEntity<FQLeche> updateFQLeche(@Valid @RequestBody FQLeche fQLeche) throws URISyntaxException {
        log.debug("REST request to update FQLeche : {}", fQLeche);
        if (fQLeche.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FQLeche result = fQLecheService.save(fQLeche);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fQLeche.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fq-leches} : get all the fQLeches.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fQLeches in body.
     */
    @GetMapping("/fq-leches")
    public ResponseEntity<List<FQLeche>> getAllFQLeches(FQLecheCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FQLeches by criteria: {}", criteria);
        Page<FQLeche> page = fQLecheQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fq-leches/count} : count all the fQLeches.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fq-leches/count")
    public ResponseEntity<Long> countFQLeches(FQLecheCriteria criteria) {
        log.debug("REST request to count FQLeches by criteria: {}", criteria);
        return ResponseEntity.ok().body(fQLecheQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fq-leches/:id} : get the "id" fQLeche.
     *
     * @param id the id of the fQLeche to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fQLeche, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fq-leches/{id}")
    public ResponseEntity<FQLeche> getFQLeche(@PathVariable Long id) {
        log.debug("REST request to get FQLeche : {}", id);
        Optional<FQLeche> fQLeche = fQLecheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fQLeche);
    }

    /**
     * {@code DELETE  /fq-leches/:id} : delete the "id" fQLeche.
     *
     * @param id the id of the fQLeche to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fq-leches/{id}")
    public ResponseEntity<Void> deleteFQLeche(@PathVariable Long id) {
        log.debug("REST request to delete FQLeche : {}", id);
        fQLecheService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
