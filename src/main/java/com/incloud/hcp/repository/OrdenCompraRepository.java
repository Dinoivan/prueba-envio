package com.incloud.hcp.repository;

import com.incloud.hcp.domain.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.estadoSap = 'L'")
    List<OrdenCompra> getAllActive();

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.id = ?1 and oc.estadoSap = 'L'")
    OrdenCompra getOrdenCompraById(Integer idOrdenCompra);

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.estadoSap in ('L','A') and oc.fechaRegistro between ?1 and ?2 and oc.proveedorRuc = ?3")
    List<OrdenCompra> getOrdenCompraByFechaRegistroBetweenAndProveedorRuc(Date fechaInicio, Date fechaFin, String proveedorRuc);

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.estadoSap in ('L','A') and oc.fechaRegistro between ?1 and ?2")
    List<OrdenCompra> getOrdenCompraByFechaRegistroBetween(Date fechaInicio, Date fechaFin);

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.estadoSap in ('L','A') and oc.fechaRegistro between ?1 and ?2 and oc.proveedorRuc = ?3 and oc.fechaPublicacion between ?4 and ?5")
    List<OrdenCompra> getOrdenCompraByFechaRegistroBetweenAndProveedorRuc2(Date fechaInicio, Date fechaFin, String proveedorRuc, Date fechaInicioPublicacion, Date fechaFinPublicacion);

    //@Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.estadoSap in ('L','A') and oc.fechaRegistro between ?1 and ?2 and oc.fechaPublicacion between ?3 and ?4")
    @Query(nativeQuery = true, value ="SELECT * FROM ORDEN_COMPRA oc WHERE IS_ACTIVE = 1  and oc.ESTADO_SAP in ('L','A') and TO_DATE(oc.FECHA_REGISTRO) between ?1 and ?2 AND TO_DATE(oc.FECHA_PUBLICACION) between ?3 and ?4")
    List<OrdenCompra> getOrdenCompraByFechaRegistroBetween2(Date fechaInicio, Date fechaFin, Date fechaInicioPublicacion, Date fechaFinPublicacion);

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.idEstadoOrdenCompra = ?1  and oc.fechaRegistro between ?2 and ?3")
    List<OrdenCompra> getOrdenCompraByFechaRegistroEstadoBetween(Integer idEstado, Date fechaInicio, Date fechaFin);

    @Query("SELECT oc FROM OrdenCompra oc where oc.isActive = '1' and oc.numeroOrdenCompra = ?1 and oc.idEstadoOrdenCompra = ?2  and oc.fechaRegistro between ?3 and ?4")
    List<OrdenCompra> getOrdenCompraByFechaRegistroEstadoBetweenOrdenCompra(String ordenCompra, Integer idEstado, Date fechaInicio, Date fechaFin);

    @Query("SELECT oc FROM OrdenCompra oc where oc.numeroOrdenCompra = ?1 order by oc.id desc")
    List<OrdenCompra> getAllOcs(String numeroOrdenCompra);

    @Query("SELECT oc FROM OrdenCompra oc where oc.id= ?1 and oc.isActive = '1' and oc.estadoSap = 'L'")
    Optional<OrdenCompra> findByIdAndIsActive(Integer idOrdenCompra);

    @Query("SELECT oc FROM OrdenCompra oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap in ('L','A','B')")
    Optional<OrdenCompra> getOrdenCompraActivaByNumero(String numeroOrdenCompra);

    @Query("SELECT oc FROM OrdenCompra oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap in ('L','A','B')")
    List<OrdenCompra> listOrdenCompraActivaByNumero(String numeroOrdenCompra);

    @Query("SELECT oc FROM OrdenCompra oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap = 'L'")
    Optional<OrdenCompra> getOrdenCompraLiberadaByNumero(String numeroOrdenCompra);


    @Query("SELECT oc FROM OrdenCompra oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.estadoSap = 'L' and oc.idEstadoOrdenCompra not in (4,5)")
    Optional<OrdenCompra> getOrdenCompraLiberadaActivaValidaByNumero(String numeroOrdenCompra);

    @Query(nativeQuery = true,
            value="SELECT top 1 oc.* FROM ORDEN_COMPRA oc WHERE NUMERO_ORDEN_COMPRA=?1 ")
    OrdenCompra getOrdenCompraByNOCompra(String nOrdenCompra);

    @Query("SELECT oc FROM OrdenCompra oc where oc.numeroOrdenCompra = ?1 and oc.isActive = '1' and oc.idEstadoOrdenCompra = 4 ")
    OrdenCompra getOrdenCompraRechazada(String numeroOrdenCompra);
}