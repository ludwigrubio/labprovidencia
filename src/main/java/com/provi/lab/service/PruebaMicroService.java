package com.provi.lab.service;

import com.provi.lab.domain.PruebaMicro;
import com.provi.lab.repository.PruebaMicroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PruebaMicro}.
 */
@Service
@Transactional
public class PruebaMicroService {

    private final Logger log = LoggerFactory.getLogger(PruebaMicroService.class);

    private final PruebaMicroRepository pruebaMicroRepository;

    public PruebaMicroService(PruebaMicroRepository pruebaMicroRepository) {
        this.pruebaMicroRepository = pruebaMicroRepository;
    }

    /**
     * Save a pruebaMicro.
     *
     * @param pruebaMicro the entity to save.
     * @return the persisted entity.
     */
    public PruebaMicro save(PruebaMicro pruebaMicro) {
        log.debug("Request to save PruebaMicro : {}", pruebaMicro);
        return pruebaMicroRepository.save(pruebaMicro);
    }

    /**
     * Get all the pruebaMicros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PruebaMicro> findAll(Pageable pageable) {
        log.debug("Request to get all PruebaMicros");
        return pruebaMicroRepository.findAll(pageable);
    }


    /**
     * Get one pruebaMicro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PruebaMicro> findOne(Long id) {
        log.debug("Request to get PruebaMicro : {}", id);
        return pruebaMicroRepository.findById(id);
    }

    /**
     * Delete the pruebaMicro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PruebaMicro : {}", id);
        pruebaMicroRepository.deleteById(id);
    }
}
