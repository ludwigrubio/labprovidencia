package com.provi.lab.service;

import com.provi.lab.domain.Proceso;
import com.provi.lab.repository.ProcesoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Proceso}.
 */
@Service
@Transactional
public class ProcesoService {

    private final Logger log = LoggerFactory.getLogger(ProcesoService.class);

    private final ProcesoRepository procesoRepository;

    public ProcesoService(ProcesoRepository procesoRepository) {
        this.procesoRepository = procesoRepository;
    }

    /**
     * Save a proceso.
     *
     * @param proceso the entity to save.
     * @return the persisted entity.
     */
    public Proceso save(Proceso proceso) {
        log.debug("Request to save Proceso : {}", proceso);
        return procesoRepository.save(proceso);
    }

    /**
     * Get all the procesos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Proceso> findAll(Pageable pageable) {
        log.debug("Request to get all Procesos");
        return procesoRepository.findAll(pageable);
    }


    /**
     * Get one proceso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Proceso> findOne(Long id) {
        log.debug("Request to get Proceso : {}", id);
        return procesoRepository.findById(id);
    }

    /**
     * Delete the proceso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Proceso : {}", id);
        procesoRepository.deleteById(id);
    }
}
