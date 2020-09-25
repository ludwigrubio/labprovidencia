package com.provi.lab.service;

import com.provi.lab.domain.FQCrema;
import com.provi.lab.repository.FQCremaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FQCrema}.
 */
@Service
@Transactional
public class FQCremaService {

    private final Logger log = LoggerFactory.getLogger(FQCremaService.class);

    private final FQCremaRepository fQCremaRepository;

    public FQCremaService(FQCremaRepository fQCremaRepository) {
        this.fQCremaRepository = fQCremaRepository;
    }

    /**
     * Save a fQCrema.
     *
     * @param fQCrema the entity to save.
     * @return the persisted entity.
     */
    public FQCrema save(FQCrema fQCrema) {
        log.debug("Request to save FQCrema : {}", fQCrema);
        return fQCremaRepository.save(fQCrema);
    }

    /**
     * Get all the fQCremas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FQCrema> findAll(Pageable pageable) {
        log.debug("Request to get all FQCremas");
        return fQCremaRepository.findAll(pageable);
    }


    /**
     * Get one fQCrema by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FQCrema> findOne(Long id) {
        log.debug("Request to get FQCrema : {}", id);
        return fQCremaRepository.findById(id);
    }

    /**
     * Delete the fQCrema by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FQCrema : {}", id);
        fQCremaRepository.deleteById(id);
    }
}
