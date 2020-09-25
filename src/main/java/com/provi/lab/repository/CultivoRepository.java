package com.provi.lab.repository;

import com.provi.lab.domain.Cultivo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cultivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Long>, JpaSpecificationExecutor<Cultivo> {
}
