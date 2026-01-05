package com.incloud.hcp.repository;

import com.incloud.hcp.domain.almacen.OrdenDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenDespachoRepository extends JpaRepository<OrdenDespacho, Integer> {

    @Query("SELECT oc FROM OrdenDespacho oc where oc.isActive = '1' and oc.estadoSap = 'L'")
    List<OrdenDespacho> getAllActive();

    @Query("SELECT oc FROM OrdenDespacho oc where oc.isActive = '1' and oc.id = ?1 and oc.estadoSap = 'L'")
    OrdenDespacho getOrdenDespachoById(Integer idOrdenDespacho);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.isActive = '1' and oc.estadoSap in ('L','A') and oc.fechaRegistro between ?1 and ?2 and oc.proveedorRuc = ?3 AND EXISTS ( SELECT 1 FROM OrdenDespachoDetalle godd WHERE godd.idOrdenDespacho = oc.id AND godd.cantidad > 0)")
    List<OrdenDespacho> getOrdenDespachoByFechaRegistroBetweenAndProveedorRuc(Date fechaInicio, Date fechaFin, String proveedorRuc);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.isActive = '1' and oc.estadoSap in ('L','A') and oc.fechaRegistro between ?1 and ?2 AND EXISTS ( SELECT 1 FROM OrdenDespachoDetalle godd WHERE godd.idOrdenDespacho = oc.id AND godd.cantidad > 0)")
    List<OrdenDespacho> getOrdenDespachoByFechaRegistroBetween( Date fechaInicio, Date fechaFin );

    @Query("SELECT oc FROM OrdenDespacho oc where oc.isActive = '1' and oc.estadoSap in ('L','A') and oc.fechaRegistro between ?1 and ?2 and oc.proveedorRuc = ?3 and oc.fechaPublicacion between ?4 and ?5")
    List<OrdenDespacho> getOrdenDespachoByFechaRegistroBetweenAndProveedorRuc2(Date fechaInicio, Date fechaFin, String proveedorRuc, Date fechaInicioPublicacion, Date fechaFinPublicacion);

    @Query(nativeQuery = true, value ="SELECT * FROM ORDEN_DESPACHO oc WHERE IS_ACTIVE = 1  and oc.ESTADO_SAP in ('L','A') and TO_DATE(oc.FECHA_REGISTRO) between ?1 and ?2 AND TO_DATE(oc.FECHA_PUBLICACION) between ?3 and ?4")
    List<OrdenDespacho> getOrdenDespachoByFechaRegistroBetween2(Date fechaInicio, Date fechaFin, Date fechaInicioPublicacion, Date fechaFinPublicacion);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.isActive = '1' and oc.idEstadoOrdenCompra = ?1  and oc.fechaRegistro between ?2 and ?3")
    List<OrdenDespacho> getOrdenDespachoByFechaRegistroEstadoBetween(Integer idEstado, Date fechaInicio, Date fechaFin);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.isActive = '1' and oc.numeroOrdenCompra = ?1 and oc.idEstadoOrdenCompra = ?2  and oc.fechaRegistro between ?3 and ?4")
    List<OrdenDespacho> getOrdenDespachoByFechaRegistroEstadoBetweenOrdenDespacho(String ordenDespacho, Integer idEstado, Date fechaInicio, Date fechaFin);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.numeroOrdenCompra = ?1 order by oc.id desc")
    List<OrdenDespacho> getAllOcs(String numeroOrdenCompra);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.id= ?1 and oc.isActive = '1' and oc.estadoSap = 'L'")
    Optional<OrdenDespacho> findByIdAndIsActive(Integer idOrdenDespacho);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap in ('L','A','B')")
    Optional<OrdenDespacho> getOrdenDespachoActivaByNumero(String numeroOrdenCompra);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap in ('L','A','B')")
    OrdenDespacho getODActivaByNumero(String numeroOrdenCompra);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap in ('L','A','B')")
    List<OrdenDespacho> listOrdenDespachoActivaByNumero(String numeroOrdenCompra);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap = 'L'")
    Optional<OrdenDespacho> getOrdenDespachoLiberadaByNumero(String numeroOrdenCompra);


    @Query("SELECT oc FROM OrdenDespacho oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap = 'L' and oc.idEstadoOrdenCompra not in (4,5)")
    Optional<OrdenDespacho> getOrdenDespachoLiberadaActivaValidaByNumero(String numeroOrdenCompra);

    @Query(nativeQuery = true,
            value="SELECT top 1 oc.* FROM ORDEN_DESPACHO oc WHERE NUMERO_ORDEN_COMPRA=?1 ")
    OrdenDespacho getOrdenDespachoByNOCompra(String nOrdenCompra);

    @Query("SELECT oc FROM OrdenDespacho oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.idEstadoOrdenCompra = 4 ")
    OrdenDespacho getOrdenDespachoRechazada(String numeroOrdenCompra);
}