package com.incloud.hcp.repository;

import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.domain.ProveedorLineaComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrador on 29/08/2017.
 */
public interface ProveedorLineaComercialRepository extends JpaRepository<ProveedorLineaComercial, Integer> {


    @Query("SELECT l FROM ProveedorLineaComercial l WHERE l.proveedor.idProveedor= ?1")
    List<ProveedorLineaComercial> getListLineaComercialByIdProveedor(Integer idProveedor);

    @Query("SELECT DISTINCT l.lineaComercial FROM ProveedorLineaComercial l WHERE l.proveedor.idProveedor= ?1")
    List<LineaComercial> getListOnlyLineaComercialByIdProveedor(Integer idProveedor);

    @Modifying
    @Query("DELETE FROM ProveedorLineaComercial p WHERE p.proveedor.idProveedor= ?1")
    void deleteLineaComercialByIdProveedor(Integer idProveedor);
}
