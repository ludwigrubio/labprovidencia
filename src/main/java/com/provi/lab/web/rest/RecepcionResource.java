package com.provi.lab.web.rest;

import com.provi.lab.domain.Recepcion;
import com.provi.lab.service.RecepcionService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.RecepcionCriteria;
import com.provi.lab.service.RecepcionQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.Recepcion}.
 */
@RestController
@RequestMapping("/api")
public class RecepcionResource {

    private final Logger log = LoggerFactory.getLogger(RecepcionResource.class);

    private static final String ENTITY_NAME = "recepcion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecepcionService recepcionService;

    private final RecepcionQueryService recepcionQueryService;

    public RecepcionResource(RecepcionService recepcionService, RecepcionQueryService recepcionQueryService) {
        this.recepcionService = recepcionService;
        this.recepcionQueryService = recepcionQueryService;
    }

    /**
     * {@code POST  /recepcions} : Create a new recepcion.
     *
     * @param recepcion the recepcion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recepcion, or with status {@code 400 (Bad Request)} if the recepcion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recepcions")
    public ResponseEntity<Recepcion> createRecepcion(@Valid @RequestBody Recepcion recepcion) throws URISyntaxException {
        log.debug("REST request to save Recepcion : {}", recepcion);
        if (recepcion.getId() != null) {
            throw new BadRequestAlertException("A new recepcion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recepcion result = recepcionService.save(recepcion);
        return ResponseEntity.created(new URI("/api/recepcions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recepcions} : Updates an existing recepcion.
     *
     * @param recepcion the recepcion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recepcion,
     * or with status {@code 400 (Bad Request)} if the recepcion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recepcion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recepcions")
    public ResponseEntity<Recepcion> updateRecepcion(@Valid @RequestBody Recepcion recepcion) throws URISyntaxException {
        log.debug("REST request to update Recepcion : {}", recepcion);
        if (recepcion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Recepcion result = recepcionService.save(recepcion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recepcion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /recepcions} : get all the recepcions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recepcions in body.
     */
    @GetMapping("/recepcions")
    public ResponseEntity<List<Recepcion>> getAllRecepcions(RecepcionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Recepcions by criteria: {}", criteria);
        Page<Recepcion> page = recepcionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recepcions/count} : count all the recepcions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/recepcions/count")
    public ResponseEntity<Long> countRecepcions(RecepcionCriteria criteria) {
        log.debug("REST request to count Recepcions by criteria: {}", criteria);
        return ResponseEntity.ok().body(recepcionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /recepcions/:id} : get the "id" recepcion.
     *
     * @param id the id of the recepcion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recepcion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recepcions/{id}")
    public ResponseEntity<Recepcion> getRecepcion(@PathVariable Long id) {
        log.debug("REST request to get Recepcion : {}", id);
        Optional<Recepcion> recepcion = recepcionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recepcion);
    }

    /**
     * {@code DELETE  /recepcions/:id} : delete the "id" recepcion.
     *
     * @param id the id of the recepcion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recepcions/{id}")
    public ResponseEntity<Void> deleteRecepcion(@PathVariable Long id) {
        log.debug("REST request to delete Recepcion : {}", id);
        recepcionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
