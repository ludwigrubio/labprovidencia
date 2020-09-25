package com.provi.lab.service;

import com.provi.lab.domain.Superficie;
import com.provi.lab.repository.SuperficieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Superficie}.
 */
@Service
@Transactional
public class SuperficieService {

    private final Logger log = LoggerFactory.getLogger(SuperficieService.class);

    private final SuperficieRepository superficieRepository;

    public SuperficieService(SuperficieRepository superficieRepository) {
        this.superficieRepository = superficieRepository;
    }

    /**
     * Save a superficie.
     *
     * @param superficie the entity to save.
     * @return the persisted entity.
     */
    public Superficie save(Superficie superficie) {
        log.debug("Request to save Superficie : {}", superficie);
        return superficieRepository.save(superficie);
    }

    /**
     * Get all the superficies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Superficie> findAll(Pageable pageable) {
        log.debug("Request to get all Superficies");
        return superficieRepository.findAll(pageable);
    }


    /**
     * Get one superficie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Superficie> findOne(Long id) {
        log.debug("Request to get Superficie : {}", id);
        return superficieRepository.findById(id);
    }

    /**
     * Delete the superficie by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Superficie : {}", id);
        superficieRepository.deleteById(id);
    }
}
