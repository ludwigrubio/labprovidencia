package com.provi.lab.web.rest;

import com.provi.lab.domain.Superficie;
import com.provi.lab.service.SuperficieService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.SuperficieCriteria;
import com.provi.lab.service.SuperficieQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.Superficie}.
 */
@RestController
@RequestMapping("/api")
public class SuperficieResource {

    private final Logger log = LoggerFactory.getLogger(SuperficieResource.class);

    private static final String ENTITY_NAME = "superficie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuperficieService superficieService;

    private final SuperficieQueryService superficieQueryService;

    public SuperficieResource(SuperficieService superficieService, SuperficieQueryService superficieQueryService) {
        this.superficieService = superficieService;
        this.superficieQueryService = superficieQueryService;
    }

    /**
     * {@code POST  /superficies} : Create a new superficie.
     *
     * @param superficie the superficie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new superficie, or with status {@code 400 (Bad Request)} if the superficie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/superficies")
    public ResponseEntity<Superficie> createSuperficie(@Valid @RequestBody Superficie superficie) throws URISyntaxException {
        log.debug("REST request to save Superficie : {}", superficie);
        if (superficie.getId() != null) {
            throw new BadRequestAlertException("A new superficie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Superficie result = superficieService.save(superficie);
        return ResponseEntity.created(new URI("/api/superficies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /superficies} : Updates an existing superficie.
     *
     * @param superficie the superficie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated superficie,
     * or with status {@code 400 (Bad Request)} if the superficie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the superficie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/superficies")
    public ResponseEntity<Superficie> updateSuperficie(@Valid @RequestBody Superficie superficie) throws URISyntaxException {
        log.debug("REST request to update Superficie : {}", superficie);
        if (superficie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Superficie result = superficieService.save(superficie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, superficie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /superficies} : get all the superficies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of superficies in body.
     */
    @GetMapping("/superficies")
    public ResponseEntity<List<Superficie>> getAllSuperficies(SuperficieCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Superficies by criteria: {}", criteria);
        Page<Superficie> page = superficieQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /superficies/count} : count all the superficies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/superficies/count")
    public ResponseEntity<Long> countSuperficies(SuperficieCriteria criteria) {
        log.debug("REST request to count Superficies by criteria: {}", criteria);
        return ResponseEntity.ok().body(superficieQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /superficies/:id} : get the "id" superficie.
     *
     * @param id the id of the superficie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the superficie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/superficies/{id}")
    public ResponseEntity<Superficie> getSuperficie(@PathVariable Long id) {
        log.debug("REST request to get Superficie : {}", id);
        Optional<Superficie> superficie = superficieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(superficie);
    }

    /**
     * {@code DELETE  /superficies/:id} : delete the "id" superficie.
     *
     * @param id the id of the superficie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/superficies/{id}")
    public ResponseEntity<Void> deleteSuperficie(@PathVariable Long id) {
        log.debug("REST request to delete Superficie : {}", id);
        superficieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
