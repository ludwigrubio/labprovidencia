package com.provi.lab.service;

import com.provi.lab.domain.UserExtra;
import com.provi.lab.repository.UserExtraRepository;
import com.provi.lab.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserExtra}.
 */
@Service
@Transactional
public class UserExtraService {

    private final Logger log = LoggerFactory.getLogger(UserExtraService.class);

    private final UserExtraRepository userExtraRepository;

    private final UserRepository userRepository;

    public UserExtraService(UserExtraRepository userExtraRepository, UserRepository userRepository) {
        this.userExtraRepository = userExtraRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a userExtra.
     *
     * @param userExtra the entity to save.
     * @return the persisted entity.
     */
    public UserExtra save(UserExtra userExtra) {
        log.debug("Request to save UserExtra : {}", userExtra);
        Long userId = userExtra.getUser().getId();
        userRepository.findById(userId).ifPresent(userExtra::user);
        return userExtraRepository.save(userExtra);
    }

    /**
     * Get all the userExtras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserExtra> findAll(Pageable pageable) {
        log.debug("Request to get all UserExtras");
        return userExtraRepository.findAll(pageable);
    }


    /**
     * Get one userExtra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserExtra> findOne(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        return userExtraRepository.findById(id);
    }

    /**
     * Delete the userExtra by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExtra : {}", id);
        userExtraRepository.deleteById(id);
    }
}
