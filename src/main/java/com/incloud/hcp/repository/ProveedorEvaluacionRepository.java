package com.incloud.hcp.repository;

import com.incloud.hcp.domain.ProveedorEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrador on 18/09/2017.
 */
public interface ProveedorEvaluacionRepository  extends JpaRepository<ProveedorEvaluacion, Integer> {
    @Query("SELECT p FROM ProveedorEvaluacion p WHERE p.proveedor.idProveedor = ?1")
    List<ProveedorEvaluacion> getProveedorEvaluacionByIdProveedor(Integer idProveedor);
}
