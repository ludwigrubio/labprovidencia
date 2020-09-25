package com.provi.lab.repository;

import com.provi.lab.domain.FQSuero;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FQSuero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FQSueroRepository extends JpaRepository<FQSuero, Long>, JpaSpecificationExecutor<FQSuero> {
}
