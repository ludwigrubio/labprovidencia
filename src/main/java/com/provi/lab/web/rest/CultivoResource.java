package com.provi.lab.web.rest;

import com.provi.lab.domain.Cultivo;
import com.provi.lab.service.CultivoService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.CultivoCriteria;
import com.provi.lab.service.CultivoQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.Cultivo}.
 */
@RestController
@RequestMapping("/api")
public class CultivoResource {

    private final Logger log = LoggerFactory.getLogger(CultivoResource.class);

    private static final String ENTITY_NAME = "cultivo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CultivoService cultivoService;

    private final CultivoQueryService cultivoQueryService;

    public CultivoResource(CultivoService cultivoService, CultivoQueryService cultivoQueryService) {
        this.cultivoService = cultivoService;
        this.cultivoQueryService = cultivoQueryService;
    }

    /**
     * {@code POST  /cultivos} : Create a new cultivo.
     *
     * @param cultivo the cultivo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cultivo, or with status {@code 400 (Bad Request)} if the cultivo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cultivos")
    public ResponseEntity<Cultivo> createCultivo(@Valid @RequestBody Cultivo cultivo) throws URISyntaxException {
        log.debug("REST request to save Cultivo : {}", cultivo);
        if (cultivo.getId() != null) {
            throw new BadRequestAlertException("A new cultivo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cultivo result = cultivoService.save(cultivo);
        return ResponseEntity.created(new URI("/api/cultivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cultivos} : Updates an existing cultivo.
     *
     * @param cultivo the cultivo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cultivo,
     * or with status {@code 400 (Bad Request)} if the cultivo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cultivo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cultivos")
    public ResponseEntity<Cultivo> updateCultivo(@Valid @RequestBody Cultivo cultivo) throws URISyntaxException {
        log.debug("REST request to update Cultivo : {}", cultivo);
        if (cultivo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cultivo result = cultivoService.save(cultivo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cultivo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cultivos} : get all the cultivos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cultivos in body.
     */
    @GetMapping("/cultivos")
    public ResponseEntity<List<Cultivo>> getAllCultivos(CultivoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cultivos by criteria: {}", criteria);
        Page<Cultivo> page = cultivoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cultivos/count} : count all the cultivos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cultivos/count")
    public ResponseEntity<Long> countCultivos(CultivoCriteria criteria) {
        log.debug("REST request to count Cultivos by criteria: {}", criteria);
        return ResponseEntity.ok().body(cultivoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cultivos/:id} : get the "id" cultivo.
     *
     * @param id the id of the cultivo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cultivo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cultivos/{id}")
    public ResponseEntity<Cultivo> getCultivo(@PathVariable Long id) {
        log.debug("REST request to get Cultivo : {}", id);
        Optional<Cultivo> cultivo = cultivoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cultivo);
    }

    /**
     * {@code DELETE  /cultivos/:id} : delete the "id" cultivo.
     *
     * @param id the id of the cultivo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cultivos/{id}")
    public ResponseEntity<Void> deleteCultivo(@PathVariable Long id) {
        log.debug("REST request to delete Cultivo : {}", id);
        cultivoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
