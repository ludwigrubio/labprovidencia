package com.provi.lab.service;

import com.provi.lab.domain.FQLeche;
import com.provi.lab.repository.FQLecheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FQLeche}.
 */
@Service
@Transactional
public class FQLecheService {

    private final Logger log = LoggerFactory.getLogger(FQLecheService.class);

    private final FQLecheRepository fQLecheRepository;

    public FQLecheService(FQLecheRepository fQLecheRepository) {
        this.fQLecheRepository = fQLecheRepository;
    }

    /**
     * Save a fQLeche.
     *
     * @param fQLeche the entity to save.
     * @return the persisted entity.
     */
    public FQLeche save(FQLeche fQLeche) {
        log.debug("Request to save FQLeche : {}", fQLeche);
        return fQLecheRepository.save(fQLeche);
    }

    /**
     * Get all the fQLeches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FQLeche> findAll(Pageable pageable) {
        log.debug("Request to get all FQLeches");
        return fQLecheRepository.findAll(pageable);
    }


    /**
     * Get one fQLeche by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FQLeche> findOne(Long id) {
        log.debug("Request to get FQLeche : {}", id);
        return fQLecheRepository.findById(id);
    }

    /**
     * Delete the fQLeche by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FQLeche : {}", id);
        fQLecheRepository.deleteById(id);
    }
}
