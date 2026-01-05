package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Cotizacion;
import com.incloud.hcp.domain.CotizacionAdjunto;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.Proveedor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 01/09/2017.
 */
public interface CotizacionAdjuntoRepository extends JpaRepository<CotizacionAdjunto, Integer> {

    @Query("select u from CotizacionAdjunto u where u.cotizacion.licitacion = ?1")
    List<CotizacionAdjunto>  findByAndSort(Licitacion licitacion, Sort sort);

    @Query("select u from CotizacionAdjunto u where u.cotizacion.licitacion = ?1 and u.cotizacion.proveedor = ?2 order by u.archivoNombre")
    List<CotizacionAdjunto> findByLicitacionProveedor(Licitacion licitacion, Proveedor proveedor);

    @Query("select count(u) from CotizacionAdjunto u where u.cotizacion.licitacion = ?1 and u.cotizacion.proveedor = ?2")
    Integer countByLicitacionProveedor(Licitacion licitacion, Proveedor proveedor);

    void deleteCotizacionAdjuntoByCotizacion(Cotizacion cotizacion);

    @Modifying
    @Query("DELETE FROM CotizacionAdjunto p WHERE p.cotizacion.idCotizacion = ?1")
    void deleteCotizacionAdjuntoByCotizacion02(Integer idCotizacion);

    @Modifying
    @Query("delete from CotizacionAdjunto u where u.cotizacion = ?1 and u.archivoId = ?2")
    void deleteCotizacionAdjuntoByCotizacionArchivoId(Cotizacion cotizacion, String archivoId);

    Integer countByCotizacion(Cotizacion cotizacion);

    @Modifying
    @Query("select u from CotizacionAdjunto u where u.cotizacion = ?1")
    List<CotizacionAdjunto> findCotizacionAdjuntoByCotizacionOrderByArchivoNombre(Cotizacion cotizacion);

    @Query("select count(1) from CotizacionAdjunto u where u.cotizacion.licitacion = ?1 and u.cotizacion.proveedor = ?2")
    Integer countByCotizacionAdjuntoProveedor(Licitacion licitacion, Proveedor proveedorn);

    List<CotizacionAdjunto> findByCotizacion(Cotizacion cotizacion);

}
