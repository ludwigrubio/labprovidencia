package com.provi.lab.repository;

import com.provi.lab.domain.FQCrema;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FQCrema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FQCremaRepository extends JpaRepository<FQCrema, Long>, JpaSpecificationExecutor<FQCrema> {
}
