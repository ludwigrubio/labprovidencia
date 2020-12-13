package com.provi.lab.web.rest;

import com.provi.lab.domain.LogLactoEscan;
import com.provi.lab.service.LogLactoEscanService;
import com.provi.lab.web.rest.errors.BadRequestAlertException;
import com.provi.lab.service.dto.LogLactoEscanCriteria;
import com.provi.lab.service.LogLactoEscanQueryService;

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
 * REST controller for managing {@link com.provi.lab.domain.LogLactoEscan}.
 */
@RestController
@RequestMapping("/api")
public class LogLactoEscanResource {

    private final Logger log = LoggerFactory.getLogger(LogLactoEscanResource.class);

    private static final String ENTITY_NAME = "logLactoEscan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LogLactoEscanService logLactoEscanService;

    private final LogLactoEscanQueryService logLactoEscanQueryService;

    public LogLactoEscanResource(LogLactoEscanService logLactoEscanService, LogLactoEscanQueryService logLactoEscanQueryService) {
        this.logLactoEscanService = logLactoEscanService;
        this.logLactoEscanQueryService = logLactoEscanQueryService;
    }

    /**
     * {@code POST  /log-lacto-escans} : Create a new logLactoEscan.
     *
     * @param logLactoEscan the logLactoEscan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new logLactoEscan, or with status {@code 400 (Bad Request)} if the logLactoEscan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/log-lacto-escans")
    public ResponseEntity<LogLactoEscan> createLogLactoEscan(@Valid @RequestBody LogLactoEscan logLactoEscan) throws URISyntaxException {
        log.debug("REST request to save LogLactoEscan : {}", logLactoEscan);
        if (logLactoEscan.getId() != null) {
            throw new BadRequestAlertException("A new logLactoEscan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogLactoEscan result = logLactoEscanService.save(logLactoEscan);
        return ResponseEntity.created(new URI("/api/log-lacto-escans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /log-lacto-escans} : Updates an existing logLactoEscan.
     *
     * @param logLactoEscan the logLactoEscan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated logLactoEscan,
     * or with status {@code 400 (Bad Request)} if the logLactoEscan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the logLactoEscan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/log-lacto-escans")
    public ResponseEntity<LogLactoEscan> updateLogLactoEscan(@Valid @RequestBody LogLactoEscan logLactoEscan) throws URISyntaxException {
        log.debug("REST request to update LogLactoEscan : {}", logLactoEscan);
        if (logLactoEscan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LogLactoEscan result = logLactoEscanService.save(logLactoEscan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, logLactoEscan.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /log-lacto-escans} : get all the logLactoEscans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of logLactoEscans in body.
     */
    @GetMapping("/log-lacto-escans")
    public ResponseEntity<List<LogLactoEscan>> getAllLogLactoEscans(LogLactoEscanCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LogLactoEscans by criteria: {}", criteria);
        Page<LogLactoEscan> page = logLactoEscanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /log-lacto-escans/count} : count all the logLactoEscans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/log-lacto-escans/count")
    public ResponseEntity<Long> countLogLactoEscans(LogLactoEscanCriteria criteria) {
        log.debug("REST request to count LogLactoEscans by criteria: {}", criteria);
        return ResponseEntity.ok().body(logLactoEscanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /log-lacto-escans/:id} : get the "id" logLactoEscan.
     *
     * @param id the id of the logLactoEscan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the logLactoEscan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/log-lacto-escans/{id}")
    public ResponseEntity<LogLactoEscan> getLogLactoEscan(@PathVariable Long id) {
        log.debug("REST request to get LogLactoEscan : {}", id);
        Optional<LogLactoEscan> logLactoEscan = logLactoEscanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(logLactoEscan);
    }

    /**
     * {@code DELETE  /log-lacto-escans/:id} : delete the "id" logLactoEscan.
     *
     * @param id the id of the logLactoEscan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/log-lacto-escans/{id}")
    public ResponseEntity<Void> deleteLogLactoEscan(@PathVariable Long id) {
        log.debug("REST request to delete LogLactoEscan : {}", id);
        logLactoEscanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /tinas/creacion/:id} : create by tinas count.
     *
     * @param count the count of tinas.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tinas/creacion/{count}")
    public ResponseEntity<Void> createTinasByCount(@PathVariable Long count) {
        log.debug("Request to create number of tinas : {}", count);

        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, count.toString())).build();

    }
}
