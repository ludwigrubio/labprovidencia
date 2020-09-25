package com.provi.lab.service;

import com.provi.lab.domain.Cultivo;
import com.provi.lab.repository.CultivoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cultivo}.
 */
@Service
@Transactional
public class CultivoService {

    private final Logger log = LoggerFactory.getLogger(CultivoService.class);

    private final CultivoRepository cultivoRepository;

    public CultivoService(CultivoRepository cultivoRepository) {
        this.cultivoRepository = cultivoRepository;
    }

    /**
     * Save a cultivo.
     *
     * @param cultivo the entity to save.
     * @return the persisted entity.
     */
    public Cultivo save(Cultivo cultivo) {
        log.debug("Request to save Cultivo : {}", cultivo);
        return cultivoRepository.save(cultivo);
    }

    /**
     * Get all the cultivos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Cultivo> findAll(Pageable pageable) {
        log.debug("Request to get all Cultivos");
        return cultivoRepository.findAll(pageable);
    }


    /**
     * Get one cultivo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cultivo> findOne(Long id) {
        log.debug("Request to get Cultivo : {}", id);
        return cultivoRepository.findById(id);
    }

    /**
     * Delete the cultivo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cultivo : {}", id);
        cultivoRepository.deleteById(id);
    }
}
