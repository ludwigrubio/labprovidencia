package com.provi.lab.web.rest;

import com.provi.lab.domain.TipoProducto;
import com.provi.lab.repository.TipoProductoRepository;
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
 * REST controller for managing {@link com.provi.lab.domain.TipoProducto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoProductoResource {

    private final Logger log = LoggerFactory.getLogger(TipoProductoResource.class);

    private static final String ENTITY_NAME = "tipoProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoProductoRepository tipoProductoRepository;

    public TipoProductoResource(TipoProductoRepository tipoProductoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    /**
     * {@code POST  /tipo-productos} : Create a new tipoProducto.
     *
     * @param tipoProducto the tipoProducto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoProducto, or with status {@code 400 (Bad Request)} if the tipoProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-productos")
    public ResponseEntity<TipoProducto> createTipoProducto(@Valid @RequestBody TipoProducto tipoProducto) throws URISyntaxException {
        log.debug("REST request to save TipoProducto : {}", tipoProducto);
        if (tipoProducto.getId() != null) {
            throw new BadRequestAlertException("A new tipoProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoProducto result = tipoProductoRepository.save(tipoProducto);
        return ResponseEntity.created(new URI("/api/tipo-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-productos} : Updates an existing tipoProducto.
     *
     * @param tipoProducto the tipoProducto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoProducto,
     * or with status {@code 400 (Bad Request)} if the tipoProducto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoProducto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-productos")
    public ResponseEntity<TipoProducto> updateTipoProducto(@Valid @RequestBody TipoProducto tipoProducto) throws URISyntaxException {
        log.debug("REST request to update TipoProducto : {}", tipoProducto);
        if (tipoProducto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoProducto result = tipoProductoRepository.save(tipoProducto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoProducto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-productos} : get all the tipoProductos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoProductos in body.
     */
    @GetMapping("/tipo-productos")
    public ResponseEntity<List<TipoProducto>> getAllTipoProductos(Pageable pageable) {
        log.debug("REST request to get a page of TipoProductos");
        Page<TipoProducto> page = tipoProductoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-productos/:id} : get the "id" tipoProducto.
     *
     * @param id the id of the tipoProducto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoProducto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-productos/{id}")
    public ResponseEntity<TipoProducto> getTipoProducto(@PathVariable Long id) {
        log.debug("REST request to get TipoProducto : {}", id);
        Optional<TipoProducto> tipoProducto = tipoProductoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoProducto);
    }

    /**
     * {@code DELETE  /tipo-productos/:id} : delete the "id" tipoProducto.
     *
     * @param id the id of the tipoProducto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-productos/{id}")
    public ResponseEntity<Void> deleteTipoProducto(@PathVariable Long id) {
        log.debug("REST request to delete TipoProducto : {}", id);
        tipoProductoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
