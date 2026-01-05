package com.incloud.hcp.repository;

import com.incloud.hcp.domain.ProveedorCanalContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrador on 24/10/2017.
 */
public interface ProveedorCanalContactoRepository extends JpaRepository<ProveedorCanalContacto, Integer> {
    @Query("SELECT b FROM ProveedorCanalContacto b WHERE b.proveedor.idProveedor= ?1")
    List<ProveedorCanalContacto> getListCanalContactoByIdProveedor(Integer idProveedor);

    @Modifying
    @Query("DELETE FROM ProveedorCanalContacto p WHERE p.proveedor.idProveedor= ?1")
    void deleteCanalContactoByIdProveedor(Integer idProveedor);
}
