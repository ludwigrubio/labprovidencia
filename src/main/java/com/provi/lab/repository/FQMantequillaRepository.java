package com.provi.lab.repository;

import com.provi.lab.domain.FQMantequilla;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FQMantequilla entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FQMantequillaRepository extends JpaRepository<FQMantequilla, Long>, JpaSpecificationExecutor<FQMantequilla> {
}
