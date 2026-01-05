package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionProveedor;
import com.incloud.hcp.domain.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by USER on 21/09/2017.
 */
public interface LicitacionProveedorRepository extends JpaRepository<LicitacionProveedor, Integer> {

    List<LicitacionProveedor> findByLicitacion(Licitacion licitacion);

    @Query("select u from LicitacionProveedor u where u.licitacion = ?1 and (u.indSiParticipa is null or u.indSiParticipa = ?2) order by u.proveedor.razonSocial")
    List<LicitacionProveedor> findByLicitacionSiParticipa(Licitacion licitacion, String indicador);

    @Query("select count(u.licitacion.idLicitacion) from LicitacionProveedor u where u.proveedor = ?1 and u.licitacion.estadoLicitacion = ?2" )
    Integer countByProveedorAndEstadoLicitacion(Proveedor proveedor, String estadoLicitacion);

    @Query("select count(u.licitacion.idLicitacion) from LicitacionProveedor u where u.proveedor = ?1 and (u.licitacion.estadoLicitacion = ?2 or u.licitacion.estadoLicitacion = ?3) " )
    Integer countByProveedorAndEstadoLicitacionLicitada(Proveedor proveedor, String estadoLicitacion, String estadoLicitacionSap);

    @Query("select count(u.licitacion.idLicitacion) from LicitacionProveedor u where u.proveedor = ?1 and u.licitacion.estadoLicitacion <> ?2")
    Integer countByProveedorAndNotEstadoLicitacion(Proveedor proveedor, String estadoLicitacion);

    @Modifying
    @Query("DELETE FROM LicitacionProveedor t WHERE t.licitacion.idLicitacion= ?1")
    void deleteLicitacionProveedorByIdLicitacion(Integer idLicitacion);

    Integer countByProveedor(Proveedor proveedor);

    @Query("select count(u.licitacion.idLicitacion) from LicitacionProveedor u where u.licitacion = ?1 and u.licitacion.estadoLicitacion = ?2")
    Integer countByLicitacionAndEstadoLicitacion(Licitacion licitacion, String estadoLicitacion);

    LicitacionProveedor getByLicitacionAndProveedor(Licitacion licitacion, Proveedor proveedor);

    @Query("select u.indSiParticipa from LicitacionProveedor u where u.licitacion.idLicitacion = ?1 and u.proveedor.idProveedor = ?2")
    String getIndSiParticipa(Integer idLicitacion, Integer idProveedor);

    @Modifying
    @Query("UPDATE LicitacionProveedor t SET t.fechaCierreRecepcion =?2 WHERE t.licitacion.idLicitacion = ?1 and t.fechaCierreRecepcion < ?2")
    public void updateFechaCierre(Integer idLicitacion, Date fechaCierre);




}
