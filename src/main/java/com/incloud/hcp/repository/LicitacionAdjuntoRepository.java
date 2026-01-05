package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionAdjunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 04/09/2017.
 */
public interface LicitacionAdjuntoRepository extends JpaRepository<LicitacionAdjunto, Integer> {

    public List<LicitacionAdjunto> findByLicitacionOrderByDescripcion(Licitacion licitacion);

    public List<LicitacionAdjunto> findByLicitacionOrderByArchivoNombre(Licitacion licitacion);

    @Modifying
    @Query("delete from LicitacionAdjunto u where u.licitacion = ?1 and u.archivoId = ?2")
    public void deleteLicitacionAdjuntoByLicitacionArchivoId(Licitacion licitacion, String archivoId);

    @Modifying
    @Query("delete from LicitacionAdjunto u where u.licitacion = ?1")
    public void deleteLicitacionAdjuntoByLicitacion(Licitacion licitacion);

}

