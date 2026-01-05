package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 24/08/2017.
 */
public interface LicitacionDetalleRepository extends JpaRepository<LicitacionDetalle, Integer> {

    List<LicitacionDetalle> findByLicitacionOrderByDescripcion(Licitacion licitacion);

    @Query("select u FROM LicitacionDetalle u where u.licitacion = ?1 order by u.descripcion, u.solicitudPedido, u.posicionSolicitudPedido, u.idLicitacionDetalle")
    List<LicitacionDetalle> findByLicitacionOrdenado(Licitacion licitacion);


    List<LicitacionDetalle> findByLicitacion(Licitacion licitacion);

    @Query("select u FROM LicitacionDetalle u where u.licitacion.idLicitacion = ?1 order by u.solicitudPedido, u.posicionSolicitudPedido, u.descripcion")
    List<LicitacionDetalle> findByIdLicitacionOrdenado(Integer idLicitacion);

    @Modifying
    @Query("DELETE FROM LicitacionDetalle d WHERE d.licitacion.idLicitacion= ?1")
    void deleteDetailByLicitacion(Integer idLicitacion);


}