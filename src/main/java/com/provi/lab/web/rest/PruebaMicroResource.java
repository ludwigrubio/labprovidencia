package com.provi.lab.web.rest;

import com.provi.lab.domain.PruebaMicro;
import com.provi.lab.service.PruebaMicroService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.PruebaMicroCriteria;
import com.provi.lab.service.PruebaMicroQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.PruebaMicro}.
 */
@RestController
@RequestMapping("/api")
public class PruebaMicroResource {

    private final Logger log = LoggerFactory.getLogger(PruebaMicroResource.class);

    private static final String ENTITY_NAME = "pruebaMicro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PruebaMicroService pruebaMicroService;

    private final PruebaMicroQueryService pruebaMicroQueryService;

    public PruebaMicroResource(PruebaMicroService pruebaMicroService, PruebaMicroQueryService pruebaMicroQueryService) {
        this.pruebaMicroService = pruebaMicroService;
        this.pruebaMicroQueryService = pruebaMicroQueryService;
    }

    /**
     * {@code POST  /prueba-micros} : Create a new pruebaMicro.
     *
     * @param pruebaMicro the pruebaMicro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pruebaMicro, or with status {@code 400 (Bad Request)} if the pruebaMicro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prueba-micros")
    public ResponseEntity<PruebaMicro> createPruebaMicro(@Valid @RequestBody PruebaMicro pruebaMicro) throws URISyntaxException {
        log.debug("REST request to save PruebaMicro : {}", pruebaMicro);
        if (pruebaMicro.getId() != null) {
            throw new BadRequestAlertException("A new pruebaMicro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PruebaMicro result = pruebaMicroService.save(pruebaMicro);
        return ResponseEntity.created(new URI("/api/prueba-micros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prueba-micros} : Updates an existing pruebaMicro.
     *
     * @param pruebaMicro the pruebaMicro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pruebaMicro,
     * or with status {@code 400 (Bad Request)} if the pruebaMicro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pruebaMicro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prueba-micros")
    public ResponseEntity<PruebaMicro> updatePruebaMicro(@Valid @RequestBody PruebaMicro pruebaMicro) throws URISyntaxException {
        log.debug("REST request to update PruebaMicro : {}", pruebaMicro);
        if (pruebaMicro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PruebaMicro result = pruebaMicroService.save(pruebaMicro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pruebaMicro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prueba-micros} : get all the pruebaMicros.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pruebaMicros in body.
     */
    @GetMapping("/prueba-micros")
    public ResponseEntity<List<PruebaMicro>> getAllPruebaMicros(PruebaMicroCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PruebaMicros by criteria: {}", criteria);
        Page<PruebaMicro> page = pruebaMicroQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prueba-micros/count} : count all the pruebaMicros.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/prueba-micros/count")
    public ResponseEntity<Long> countPruebaMicros(PruebaMicroCriteria criteria) {
        log.debug("REST request to count PruebaMicros by criteria: {}", criteria);
        return ResponseEntity.ok().body(pruebaMicroQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prueba-micros/:id} : get the "id" pruebaMicro.
     *
     * @param id the id of the pruebaMicro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pruebaMicro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prueba-micros/{id}")
    public ResponseEntity<PruebaMicro> getPruebaMicro(@PathVariable Long id) {
        log.debug("REST request to get PruebaMicro : {}", id);
        Optional<PruebaMicro> pruebaMicro = pruebaMicroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pruebaMicro);
    }

    /**
     * {@code DELETE  /prueba-micros/:id} : delete the "id" pruebaMicro.
     *
     * @param id the id of the pruebaMicro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prueba-micros/{id}")
    public ResponseEntity<Void> deletePruebaMicro(@PathVariable Long id) {
        log.debug("REST request to delete PruebaMicro : {}", id);
        pruebaMicroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
