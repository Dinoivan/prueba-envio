package com.incloud.hcp.repository;

import com.incloud.hcp.domain.ProveedorSectorTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProveedorSectorTrabajoRepository extends JpaRepository<ProveedorSectorTrabajo, Integer> {
    @Query("SELECT b FROM ProveedorSectorTrabajo b WHERE b.proveedor.idProveedor= ?1")
    List<ProveedorSectorTrabajo> getListSectorTrabajoByIdProveedor(Integer idProveedor);

    @Modifying
    @Query("DELETE FROM ProveedorSectorTrabajo p WHERE p.proveedor.idProveedor= ?1")
    void deleteSectorTrabajoByIdProveedor(Integer idProveedor);
}
