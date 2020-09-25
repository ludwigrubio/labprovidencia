package com.provi.lab.service;

import com.provi.lab.domain.FQSuero;
import com.provi.lab.repository.FQSueroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FQSuero}.
 */
@Service
@Transactional
public class FQSueroService {

    private final Logger log = LoggerFactory.getLogger(FQSueroService.class);

    private final FQSueroRepository fQSueroRepository;

    public FQSueroService(FQSueroRepository fQSueroRepository) {
        this.fQSueroRepository = fQSueroRepository;
    }

    /**
     * Save a fQSuero.
     *
     * @param fQSuero the entity to save.
     * @return the persisted entity.
     */
    public FQSuero save(FQSuero fQSuero) {
        log.debug("Request to save FQSuero : {}", fQSuero);
        return fQSueroRepository.save(fQSuero);
    }

    /**
     * Get all the fQSueros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FQSuero> findAll(Pageable pageable) {
        log.debug("Request to get all FQSueros");
        return fQSueroRepository.findAll(pageable);
    }


    /**
     * Get one fQSuero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FQSuero> findOne(Long id) {
        log.debug("Request to get FQSuero : {}", id);
        return fQSueroRepository.findById(id);
    }

    /**
     * Delete the fQSuero by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FQSuero : {}", id);
        fQSueroRepository.deleteById(id);
    }
}
