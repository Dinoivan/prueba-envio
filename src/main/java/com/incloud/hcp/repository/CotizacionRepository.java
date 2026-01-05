package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Cotizacion;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.Proveedor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by USER on 25/08/2017.
 */
public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {


    List<Cotizacion> findByLicitacion(Licitacion licitacion);

    Integer countByLicitacion(Licitacion licitacion);

    @Query("select u from Cotizacion u where u.licitacion = ?1")
    List<Cotizacion> findByLicitacionAndSort(Licitacion licitacion, Sort sort);

    @Query("select u from Cotizacion u where u.licitacion = ?1 and u.estadoCotizacion <> 'EN' ")
    List<Cotizacion> findByLicitacionNoAdjudicada(Licitacion licitacion);

    @Query("select u from Cotizacion u where u.licitacion = ?1 and u.estadoCotizacion = 'EN' ")
    List<Cotizacion> findByLicitacionPorAdjudicarAndSort(Licitacion licitacion, Sort sort);

    @Query("select u from Cotizacion u where u.licitacion = ?1 and u.proveedor = ?2")
    List<Cotizacion> findByLicitacionAndProveedorSort(Licitacion licitacion, Proveedor proveedor, Sort sort);

    @Query("select u from Cotizacion u where u.licitacion.idLicitacion = ?1 and u.proveedor.idProveedor = ?2")
    Cotizacion findByLicitacionAndProveedor(Integer licitacion, Integer proveedor);

    @Query("select u from Cotizacion u where u.licitacion = ?1 and u.proveedor = ?2 and u.estadoCotizacion = 'EN' " )
    List<Cotizacion> findByLicitacionAdjudicarAndProveedorSort(Licitacion licitacion, Proveedor proveedor, Sort sort);

    @Modifying
    @Query("UPDATE Cotizacion t SET t.estadoCotizacion= ?1, t.usuarioModificacion = ?2 WHERE t.idCotizacion=?3")
    void updateEstadoLicitacion(String estadoCotizacion, Integer usuarioModificacion, Integer idCotizacion);

    void deleteCotizacionByLicitacionAndProveedor(Licitacion licitacion, Proveedor proveedor);

    @Query("select count(u.idCotizacion) from Cotizacion u where u.licitacion = ?1 and u.estadoCotizacion = ?2")
    Integer countByLicitacionAndEstadoCotizacion(Licitacion licitacion, String estadoCotizacion);

    Cotizacion getByLicitacionAndProveedor(Licitacion licitacion, Proveedor proveedor);


}


