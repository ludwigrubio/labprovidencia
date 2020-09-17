package com.provi.lab.service;

import com.provi.lab.domain.Dummy;
import com.provi.lab.repository.DummyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Dummy}.
 */
@Service
@Transactional
public class DummyService {

    private final Logger log = LoggerFactory.getLogger(DummyService.class);

    private final DummyRepository dummyRepository;

    public DummyService(DummyRepository dummyRepository) {
        this.dummyRepository = dummyRepository;
    }

    /**
     * Save a dummy.
     *
     * @param dummy the entity to save.
     * @return the persisted entity.
     */
    public Dummy save(Dummy dummy) {
        log.debug("Request to save Dummy : {}", dummy);
        return dummyRepository.save(dummy);
    }

    /**
     * Get all the dummies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Dummy> findAll(Pageable pageable) {
        log.debug("Request to get all Dummies");
        return dummyRepository.findAll(pageable);
    }


    /**
     * Get one dummy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Dummy> findOne(Long id) {
        log.debug("Request to get Dummy : {}", id);
        return dummyRepository.findById(id);
    }

    /**
     * Delete the dummy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dummy : {}", id);
        dummyRepository.deleteById(id);
    }
}
