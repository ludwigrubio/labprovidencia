package com.provi.lab.service;

import com.provi.lab.domain.FQMantequilla;
import com.provi.lab.repository.FQMantequillaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FQMantequilla}.
 */
@Service
@Transactional
public class FQMantequillaService {

    private final Logger log = LoggerFactory.getLogger(FQMantequillaService.class);

    private final FQMantequillaRepository fQMantequillaRepository;

    public FQMantequillaService(FQMantequillaRepository fQMantequillaRepository) {
        this.fQMantequillaRepository = fQMantequillaRepository;
    }

    /**
     * Save a fQMantequilla.
     *
     * @param fQMantequilla the entity to save.
     * @return the persisted entity.
     */
    public FQMantequilla save(FQMantequilla fQMantequilla) {
        log.debug("Request to save FQMantequilla : {}", fQMantequilla);
        return fQMantequillaRepository.save(fQMantequilla);
    }

    /**
     * Get all the fQMantequillas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FQMantequilla> findAll(Pageable pageable) {
        log.debug("Request to get all FQMantequillas");
        return fQMantequillaRepository.findAll(pageable);
    }


    /**
     * Get one fQMantequilla by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FQMantequilla> findOne(Long id) {
        log.debug("Request to get FQMantequilla : {}", id);
        return fQMantequillaRepository.findById(id);
    }

    /**
     * Delete the fQMantequilla by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FQMantequilla : {}", id);
        fQMantequillaRepository.deleteById(id);
    }
}
