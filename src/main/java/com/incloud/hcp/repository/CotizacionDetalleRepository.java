package com.incloud.hcp.repository;

import com.incloud.hcp.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 25/08/2017.
 */
public interface CotizacionDetalleRepository extends JpaRepository<CotizacionDetalle, Integer> {

    List<CotizacionDetalle> findByCotizacion(Cotizacion cotizacion);

    List<CotizacionDetalle> findByCotizacionOrderByDescripcion(Cotizacion cotizacion);

    List<CotizacionDetalle> findByCotizacionInOrderByDescripcion(List<Cotizacion> lista);

    @Query("select u from CotizacionDetalle u where u.cotizacion in ?1 order by u.descripcion, u.licitacionDetalle.solicitudPedido, u.licitacionDetalle.posicionSolicitudPedido, u.licitacionDetalle.idLicitacionDetalle, u.cotizacion.proveedor.razonSocial")
    List<CotizacionDetalle> findByCotizacionInOrderByDescripcionAndProveedor(List<Cotizacion> lista);

    @Query("select u from CotizacionDetalle u where u.cotizacion.licitacion in ?1 order by u.solicitudPedido, u.numeroParte, u.descripcion")
    List<CotizacionDetalle> findByCotizacionCuadroComparativo(List<Cotizacion> lista);

    @Query("select u from CotizacionDetalle u where u.cotizacion.licitacion = ?1")
     List<CotizacionDetalle> findByCotizacionInAndSort(Licitacion licitacion, Sort sort);

    @Query("select u from CotizacionDetalle u where u.cotizacion.licitacion = ?1 and u.cotizacion.estadoCotizacion='EN' ")
    List<CotizacionDetalle> findByCotizacionInAndSortAdjudicado(Licitacion licitacion, Sort sort);



    /*@Query("select u from CotizacionDetalle u where u.cotizacion.licitacion = ?1 and u.cotizacion.proveedor = ?2 and u.bienServicio.rubroBien.tipoCuestionario = ?3")
    List<CotizacionDetalle> findByCotizacion(
            Licitacion licitacion,
            Proveedor proveedor,
            TipoLicitacion tipo);*/

    @Query("select u from CotizacionDetalle u where u.cotizacion.licitacion = ?1 and u.cotizacion.proveedor = ?2")
    List<CotizacionDetalle> findByCotizacion2(
            Licitacion licitacion,
            Proveedor proveedor);

    void deleteCotizacionDetalleByCotizacion(Cotizacion cotizacion);

    @Modifying
    @Query("DELETE FROM CotizacionDetalle p WHERE p.cotizacion.idCotizacion = ?1")
    void deleteCotizacionDetalleByCotizacion02(Integer idCotizacion);

    @Query("select u from CotizacionDetalle u where " +
            "u.cotizacion.proveedor.idProveedor = ?1 " +
            "and u.cotizacion.licitacion.idLicitacion = ?2 " +
            "and u.licitacionDetalle.idLicitacionDetalle = ?3 ")
    CotizacionDetalle getByCotizacionByProveedorLicitacionDetalle(
            Integer idProveedor,
            Integer idLicitacion,
            Integer idLicitacionDetalle
    );



}

