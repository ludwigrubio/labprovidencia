package com.provi.lab.repository;

import com.provi.lab.domain.LogLactoEscan;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LogLactoEscan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogLactoEscanRepository extends JpaRepository<LogLactoEscan, Long>, JpaSpecificationExecutor<LogLactoEscan> {
}
