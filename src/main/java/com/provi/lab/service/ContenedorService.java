package com.provi.lab.service;

import com.provi.lab.domain.Contenedor;
import com.provi.lab.repository.ContenedorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Contenedor}.
 */
@Service
@Transactional
public class ContenedorService {

    private final Logger log = LoggerFactory.getLogger(ContenedorService.class);

    private final ContenedorRepository contenedorRepository;

    public ContenedorService(ContenedorRepository contenedorRepository) {
        this.contenedorRepository = contenedorRepository;
    }

    /**
     * Save a contenedor.
     *
     * @param contenedor the entity to save.
     * @return the persisted entity.
     */
    public Contenedor save(Contenedor contenedor) {
        log.debug("Request to save Contenedor : {}", contenedor);
        return contenedorRepository.save(contenedor);
    }

    /**
     * Get all the contenedors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Contenedor> findAll(Pageable pageable) {
        log.debug("Request to get all Contenedors");
        return contenedorRepository.findAll(pageable);
    }


    /**
     * Get one contenedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Contenedor> findOne(Long id) {
        log.debug("Request to get Contenedor : {}", id);
        return contenedorRepository.findById(id);
    }

    /**
     * Delete the contenedor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contenedor : {}", id);
        contenedorRepository.deleteById(id);
    }
}
