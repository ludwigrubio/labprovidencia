package com.provi.lab.service;

import com.provi.lab.domain.Personal;
import com.provi.lab.repository.PersonalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Personal}.
 */
@Service
@Transactional
public class PersonalService {

    private final Logger log = LoggerFactory.getLogger(PersonalService.class);

    private final PersonalRepository personalRepository;

    public PersonalService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    /**
     * Save a personal.
     *
     * @param personal the entity to save.
     * @return the persisted entity.
     */
    public Personal save(Personal personal) {
        log.debug("Request to save Personal : {}", personal);
        return personalRepository.save(personal);
    }

    /**
     * Get all the personals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Personal> findAll(Pageable pageable) {
        log.debug("Request to get all Personals");
        return personalRepository.findAll(pageable);
    }


    /**
     * Get one personal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Personal> findOne(Long id) {
        log.debug("Request to get Personal : {}", id);
        return personalRepository.findById(id);
    }

    /**
     * Delete the personal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Personal : {}", id);
        personalRepository.deleteById(id);
    }
}
