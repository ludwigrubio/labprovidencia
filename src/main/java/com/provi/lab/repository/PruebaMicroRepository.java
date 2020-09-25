package com.provi.lab.repository;

import com.provi.lab.domain.PruebaMicro;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PruebaMicro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PruebaMicroRepository extends JpaRepository<PruebaMicro, Long>, JpaSpecificationExecutor<PruebaMicro> {
}
