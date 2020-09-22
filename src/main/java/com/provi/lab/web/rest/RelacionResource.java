package com.provi.lab.web.rest;

import com.provi.lab.domain.Relacion;
import com.provi.lab.repository.RelacionRepository;
import com.provi.lab.web.rest.errors.BadRequestAlertException;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.provi.lab.domain.Relacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RelacionResource {

    private final Logger log = LoggerFactory.getLogger(RelacionResource.class);

    private static final String ENTITY_NAME = "relacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelacionRepository relacionRepository;

    public RelacionResource(RelacionRepository relacionRepository) {
        this.relacionRepository = relacionRepository;
    }

    /**
     * {@code POST  /relacions} : Create a new relacion.
     *
     * @param relacion the relacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relacion, or with status {@code 400 (Bad Request)} if the relacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relacions")
    public ResponseEntity<Relacion> createRelacion(@Valid @RequestBody Relacion relacion) throws URISyntaxException {
        log.debug("REST request to save Relacion : {}", relacion);
        if (relacion.getId() != null) {
            throw new BadRequestAlertException("A new relacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Relacion result = relacionRepository.save(relacion);
        return ResponseEntity.created(new URI("/api/relacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relacions} : Updates an existing relacion.
     *
     * @param relacion the relacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relacion,
     * or with status {@code 400 (Bad Request)} if the relacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relacions")
    public ResponseEntity<Relacion> updateRelacion(@Valid @RequestBody Relacion relacion) throws URISyntaxException {
        log.debug("REST request to update Relacion : {}", relacion);
        if (relacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Relacion result = relacionRepository.save(relacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /relacions} : get all the relacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relacions in body.
     */
    @GetMapping("/relacions")
    public ResponseEntity<List<Relacion>> getAllRelacions(Pageable pageable) {
        log.debug("REST request to get a page of Relacions");
        Page<Relacion> page = relacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /relacions/:id} : get the "id" relacion.
     *
     * @param id the id of the relacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relacions/{id}")
    public ResponseEntity<Relacion> getRelacion(@PathVariable Long id) {
        log.debug("REST request to get Relacion : {}", id);
        Optional<Relacion> relacion = relacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(relacion);
    }

    /**
     * {@code DELETE  /relacions/:id} : delete the "id" relacion.
     *
     * @param id the id of the relacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relacions/{id}")
    public ResponseEntity<Void> deleteRelacion(@PathVariable Long id) {
        log.debug("REST request to delete Relacion : {}", id);
        relacionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
