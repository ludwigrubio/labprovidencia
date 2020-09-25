package com.provi.lab.repository;

import com.provi.lab.domain.FQLeche;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FQLeche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FQLecheRepository extends JpaRepository<FQLeche, Long>, JpaSpecificationExecutor<FQLeche> {
}
