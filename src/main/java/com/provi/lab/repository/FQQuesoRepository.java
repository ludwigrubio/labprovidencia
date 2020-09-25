package com.provi.lab.repository;

import com.provi.lab.domain.FQQueso;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FQQueso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FQQuesoRepository extends JpaRepository<FQQueso, Long>, JpaSpecificationExecutor<FQQueso> {
}
