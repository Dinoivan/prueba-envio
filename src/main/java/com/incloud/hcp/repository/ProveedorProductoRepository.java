package com.incloud.hcp.repository;

import com.incloud.hcp.domain.ProveedorProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrador on 29/08/2017.
 */
public interface ProveedorProductoRepository extends JpaRepository<ProveedorProducto, Integer> {

    @Query("SELECT p FROM ProveedorProducto p WHERE p.proveedor.idProveedor = ?1")
    List<ProveedorProducto> getListProductoByIdProveedor(Integer idProveedor);

    @Modifying
    @Query("DELETE FROM ProveedorProducto p WHERE p.proveedor.idProveedor= ?1")
    void deleteProductoByIdProveedor(Integer idProveedor);
}
