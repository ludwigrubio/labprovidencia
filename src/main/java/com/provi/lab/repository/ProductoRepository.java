package com.provi.lab.repository;

import com.provi.lab.domain.Producto;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
}
