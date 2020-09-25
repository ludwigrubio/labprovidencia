package com.provi.lab.service;

import com.provi.lab.domain.FQQueso;
import com.provi.lab.repository.FQQuesoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FQQueso}.
 */
@Service
@Transactional
public class FQQuesoService {

    private final Logger log = LoggerFactory.getLogger(FQQuesoService.class);

    private final FQQuesoRepository fQQuesoRepository;

    public FQQuesoService(FQQuesoRepository fQQuesoRepository) {
        this.fQQuesoRepository = fQQuesoRepository;
    }

    /**
     * Save a fQQueso.
     *
     * @param fQQueso the entity to save.
     * @return the persisted entity.
     */
    public FQQueso save(FQQueso fQQueso) {
        log.debug("Request to save FQQueso : {}", fQQueso);
        return fQQuesoRepository.save(fQQueso);
    }

    /**
     * Get all the fQQuesos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FQQueso> findAll(Pageable pageable) {
        log.debug("Request to get all FQQuesos");
        return fQQuesoRepository.findAll(pageable);
    }


    /**
     * Get one fQQueso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FQQueso> findOne(Long id) {
        log.debug("Request to get FQQueso : {}", id);
        return fQQuesoRepository.findById(id);
    }

    /**
     * Delete the fQQueso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FQQueso : {}", id);
        fQQuesoRepository.deleteById(id);
    }
}
