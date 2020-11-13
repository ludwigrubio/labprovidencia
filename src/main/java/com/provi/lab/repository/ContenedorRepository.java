package com.provi.lab.repository;

import com.provi.lab.domain.Contenedor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Contenedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor, Long>, JpaSpecificationExecutor<Contenedor> {
}
