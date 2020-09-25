package com.provi.lab.repository;

import com.provi.lab.domain.Superficie;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Superficie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuperficieRepository extends JpaRepository<Superficie, Long>, JpaSpecificationExecutor<Superficie> {
}
