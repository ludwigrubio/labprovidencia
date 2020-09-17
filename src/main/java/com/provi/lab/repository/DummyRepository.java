package com.provi.lab.repository;

import com.provi.lab.domain.Dummy;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Dummy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DummyRepository extends JpaRepository<Dummy, Long>, JpaSpecificationExecutor<Dummy> {
}
