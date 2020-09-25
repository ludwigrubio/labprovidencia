package com.provi.lab.repository;

import com.provi.lab.domain.Recepcion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Recepcion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecepcionRepository extends JpaRepository<Recepcion, Long>, JpaSpecificationExecutor<Recepcion> {
}
