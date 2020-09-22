package com.provi.lab.repository;

import com.provi.lab.domain.Relacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Relacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelacionRepository extends JpaRepository<Relacion, Long> {
}
