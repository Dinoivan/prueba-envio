package com.incloud.hcp.repository;

import com.incloud.hcp.domain.ProveedorCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrador on 25/09/2017.
 */
public interface ProveedorCatalogoRepository extends JpaRepository<ProveedorCatalogo, Integer> {
    @Modifying
    @Query("DELETE FROM ProveedorCatalogo p WHERE p.proveedor.idProveedor= ?1 AND p.archivoId = ?2 ")
    void deleteCatalogoByIdProveedorCatalogoById(Integer idProveedor, String archivoId);

    @Modifying
    @Query("SELECT p FROM ProveedorCatalogo p WHERE p.proveedor.idProveedor = ?1")
    List<ProveedorCatalogo> getProveedorCatalogoByIdProveedor(Integer idProveedor);
}