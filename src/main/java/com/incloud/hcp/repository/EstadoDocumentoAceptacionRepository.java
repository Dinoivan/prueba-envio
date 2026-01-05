package com.incloud.hcp.repository;


import com.incloud.hcp.domain.EstadoDocumentoAceptacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface EstadoDocumentoAceptacionRepository extends JpaRepository<EstadoDocumentoAceptacion, Integer> {
    EstadoDocumentoAceptacion getByDescripcion(String d);


    @Query("SELECT e FROM EstadoDocumentoAceptacion e where e.id=?1")
    Optional<EstadoDocumentoAceptacion> getEstadoById(Integer id);
}