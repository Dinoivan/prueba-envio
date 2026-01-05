package com.incloud.hcp.repository;

import com.incloud.hcp.domain.AdjuntoOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrador on 25/09/2017.
 */
public interface AdjuntoOrdenCompraRepository extends JpaRepository<AdjuntoOrdenCompra, Integer> {
    /*@Modifying
    @Query("DELETE FROM AdjuntoOrdenCompra p WHERE p.proveedor.idProveedor= ?1 AND p.archivoId = ?2 ")
    void deleteCatalogoByIdProveedorCatalogoById(Integer idProveedor, String archivoId);*/

    @Query("SELECT p FROM AdjuntoOrdenCompra p WHERE p.ordencompra = ?1")
    AdjuntoOrdenCompra getProveedorCatalogoByIdProveedor(String ordencompra);
}