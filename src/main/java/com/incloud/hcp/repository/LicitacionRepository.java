package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Licitacion;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by USER on 23/08/2017.
 */
public interface LicitacionRepository extends JpaRepository<Licitacion, Integer> {

    Integer countByEstadoLicitacion(String estadoLicitacion);

    Long countByEstadoLicitacionNot(String estadoLicitacion);

    //List<Licitacion> findByNroLicitacion (String nroLicitacion);

    List<Licitacion> findByEstadoLicitacion (String estadoLicitacion);

    @Query("select u from Licitacion u where u.estadoLicitacion=?1 and u.fechaCierreRecepcionOferta <= ?2")
    List<Licitacion> findByLicitacionCierre(String estadoLicitacion, Date fechaCierre);

    @Query("select u from Licitacion u where u.estadoLicitacion=?1 and u.fechaUltimaRenegociacion <= ?2")
    List<Licitacion> findByLicitacionUltimaRenegociacion(String estadoLicitacion, Date fechaCierre);

    @Query("select u from Licitacion u where u.estadoLicitacion = ?1")
    List<Licitacion> findByEstadoLicitacionAndSort(String estadoLicitacion, Sort sort);

    @Query("SELECT coalesce(max(li.nroLicitacion), 0) FROM Licitacion li")
    Integer getMaxNroLicitacion();

    @Query("SELECT coalesce(max(li.nroLicitacion), 0) FROM Licitacion li where li.anioLicitacion = ?1")
    Integer getMaxNroLicitacionAnio(Integer anioLicitacion);

    @Modifying
    @Query("UPDATE Licitacion t SET t.estadoLicitacion= ?2, t.fechaPublicacion= ?3, t.usuarioPublicacionId= ?4, t.usuarioPublicacionName= ?5, t.usuarioPublicacionEmail= ?6 WHERE t.idLicitacion=?1")
    void updateLicitacionOnPublicacion(
            Integer idLicitacion,
            String estado,
            Date fechaPublicacion,
            String usuarioPublicacionId,
            String usuarioPublicacionName,
            String usuarioPublicacionEmail);

    @Modifying
    @Query("UPDATE Licitacion t SET t.estadoLicitacion= ?1, t.fechaModificacion= ?2 WHERE t.idLicitacion=?3")
    void updateEstadoLicitacion02(String estado, Date fechaModificacion, Integer idLicitacion);

    @Modifying
    @Query("UPDATE Licitacion t SET t.fechaCierreRecepcionOferta= ?1,t.fechaUltimaRenegociacion=?1 WHERE t.idLicitacion=?2")
    void updateFechaRecepcionOferta(Date fechaRecepcionOferta, Integer idLicitacion);

    @Query("select count(u) from Licitacion u where u.estadoLicitacion IN ('AD', 'ES', 'AN')")
    Integer countByEstadoLicitacionEstadoTerminal();

}