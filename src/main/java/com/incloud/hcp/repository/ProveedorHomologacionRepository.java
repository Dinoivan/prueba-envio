package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Homologacion;
import com.incloud.hcp.domain.ProveedorHomologacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by MARCELO on 23/10/2017.
 */
public interface ProveedorHomologacionRepository extends JpaRepository<ProveedorHomologacion, Integer> {

    public List<ProveedorHomologacion> findByHomologacion(Homologacion homologacion);

 	@Modifying
    @Query("DELETE FROM ProveedorHomologacion p WHERE p.proveedor.idProveedor= ?1")
    void deleteRespuestaByIdProveedor(Integer idProveedor);
}
