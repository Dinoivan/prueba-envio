package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Cotizacion;
import com.incloud.hcp.domain.CotizacionCampoRespuesta;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.Proveedor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 31/08/2017.
 */
public interface CotizacionCampoRespuestaRepository extends JpaRepository<CotizacionCampoRespuesta, Integer> {

    @Query("select u from CotizacionCampoRespuesta u where u.cotizacion.licitacion = ?1")
    List<CotizacionCampoRespuesta> findByAndSort(Licitacion licitacion, Sort sort);

    @Query("select u from CotizacionCampoRespuesta u where u.cotizacion.licitacion = ?1 and u.cotizacion.proveedor = ?2")
    List<CotizacionCampoRespuesta> findByAndSort2(Licitacion licitacion, Proveedor proveedor, Sort sort);

    @Query("select u from CotizacionCampoRespuesta u where u.cotizacion.licitacion = ?1 and u.cotizacion.proveedor = ?2")
    List<CotizacionCampoRespuesta> findLicitacionProveedor(Licitacion licitacion, Proveedor proveedor);

    void deleteCotizacionCampoRespuestaByCotizacion(Cotizacion cotizacion);

    @Modifying
    @Query("DELETE FROM CotizacionCampoRespuesta p WHERE p.cotizacion.idCotizacion = ?1")
    void deleteCotizacionCampoRespuestaByCotizacion02(Integer idCotizacion);

    List<CotizacionCampoRespuesta> findByCotizacion(Cotizacion cotizacion);

}


