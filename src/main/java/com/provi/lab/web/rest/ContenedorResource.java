package com.provi.lab.web.rest;

import com.provi.lab.domain.Contenedor;
import com.provi.lab.service.ContenedorService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.ContenedorCriteria;
import com.provi.lab.service.ContenedorQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.Contenedor}.
 */
@RestController
@RequestMapping("/api")
public class ContenedorResource {

    private final Logger log = LoggerFactory.getLogger(ContenedorResource.class);

    private static final String ENTITY_NAME = "contenedor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContenedorService contenedorService;

    private final ContenedorQueryService contenedorQueryService;

    public ContenedorResource(ContenedorService contenedorService, ContenedorQueryService contenedorQueryService) {
        this.contenedorService = contenedorService;
        this.contenedorQueryService = contenedorQueryService;
    }

    /**
     * {@code POST  /contenedors} : Create a new contenedor.
     *
     * @param contenedor the contenedor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contenedor, or with status {@code 400 (Bad Request)} if the contenedor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contenedors")
    public ResponseEntity<Contenedor> createContenedor(@Valid @RequestBody Contenedor contenedor) throws URISyntaxException {
        log.debug("REST request to save Contenedor : {}", contenedor);
        if (contenedor.getId() != null) {
            throw new BadRequestAlertException("A new contenedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contenedor result = contenedorService.save(contenedor);
        return ResponseEntity.created(new URI("/api/contenedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contenedors} : Updates an existing contenedor.
     *
     * @param contenedor the contenedor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contenedor,
     * or with status {@code 400 (Bad Request)} if the contenedor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contenedor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contenedors")
    public ResponseEntity<Contenedor> updateContenedor(@Valid @RequestBody Contenedor contenedor) throws URISyntaxException {
        log.debug("REST request to update Contenedor : {}", contenedor);
        if (contenedor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contenedor result = contenedorService.save(contenedor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contenedor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contenedors} : get all the contenedors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contenedors in body.
     */
    @GetMapping("/contenedors")
    public ResponseEntity<List<Contenedor>> getAllContenedors(ContenedorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Contenedors by criteria: {}", criteria);
        Page<Contenedor> page = contenedorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contenedors/count} : count all the contenedors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contenedors/count")
    public ResponseEntity<Long> countContenedors(ContenedorCriteria criteria) {
        log.debug("REST request to count Contenedors by criteria: {}", criteria);
        return ResponseEntity.ok().body(contenedorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contenedors/:id} : get the "id" contenedor.
     *
     * @param id the id of the contenedor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contenedor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contenedors/{id}")
    public ResponseEntity<Contenedor> getContenedor(@PathVariable Long id) {
        log.debug("REST request to get Contenedor : {}", id);
        Optional<Contenedor> contenedor = contenedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contenedor);
    }

    /**
     * {@code DELETE  /contenedors/:id} : delete the "id" contenedor.
     *
     * @param id the id of the contenedor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contenedors/{id}")
    public ResponseEntity<Void> deleteContenedor(@PathVariable Long id) {
        log.debug("REST request to delete Contenedor : {}", id);
        contenedorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
