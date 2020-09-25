package com.provi.lab.service;

import com.provi.lab.domain.Recepcion;
import com.provi.lab.repository.RecepcionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Recepcion}.
 */
@Service
@Transactional
public class RecepcionService {

    private final Logger log = LoggerFactory.getLogger(RecepcionService.class);

    private final RecepcionRepository recepcionRepository;

    public RecepcionService(RecepcionRepository recepcionRepository) {
        this.recepcionRepository = recepcionRepository;
    }

    /**
     * Save a recepcion.
     *
     * @param recepcion the entity to save.
     * @return the persisted entity.
     */
    public Recepcion save(Recepcion recepcion) {
        log.debug("Request to save Recepcion : {}", recepcion);
        return recepcionRepository.save(recepcion);
    }

    /**
     * Get all the recepcions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Recepcion> findAll(Pageable pageable) {
        log.debug("Request to get all Recepcions");
        return recepcionRepository.findAll(pageable);
    }


    /**
     * Get one recepcion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Recepcion> findOne(Long id) {
        log.debug("Request to get Recepcion : {}", id);
        return recepcionRepository.findById(id);
    }

    /**
     * Delete the recepcion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recepcion : {}", id);
        recepcionRepository.deleteById(id);
    }
}
