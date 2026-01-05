package com.incloud.hcp.repository;

import com.incloud.hcp.domain.DocumentoAceptacion;
import com.incloud.hcp.domain.Licitacion;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentoAceptacionRepository extends JpaRepository<DocumentoAceptacion, Integer> {

    @Query("SELECT da FROM DocumentoAceptacion da where da.idEstadoDocumentoAceptacion not in (4) and da.fechaEmision between ?1 and ?2 and da.proveedorRuc = ?3")
    List<DocumentoAceptacion> getDocumentoAceptacionByFechaRegistroBetweenAndProveedorRuc(Date fechaInicio, Date fechaFin, String proveedorRuc);

    @Query("SELECT da FROM DocumentoAceptacion da where da.idEstadoDocumentoAceptacion not in (4) and da.fechaEmision between ?1 and ?2")
    List<DocumentoAceptacion> getDocumentoAceptacionByFechaRegistroBetween(Date fechaInicio, Date fechaFin);

    Optional<DocumentoAceptacion> findByNumeroDocumentoAceptacion(String numeroDocumentoAceptacion);

    @Query("SELECT da.id FROM DocumentoAceptacion da where da.numeroDocumentoAceptacion = ?1")
    Integer getIdDocumentoAceptacionByNumero (String numeroDocumentoAceptacion);

    @Query("SELECT da FROM DocumentoAceptacion da where da.numeroOrdenCompra = ?1")
    List<DocumentoAceptacion> getDocumentoAceptacionByOc (String numeroOrden);

    @Query("SELECT da FROM DocumentoAceptacion da where da.idTipoDocumentoAceptacion=?1 and da.id = ?2")
    DocumentoAceptacion getDocumentoAceptacionById(Integer idTipoDocumentoAceptacion, Integer idDocumentoAceptacion);

    @Transactional
    @Modifying
    @Query("update DocumentoAceptacion da set da.idEstadoDocumentoAceptacion =?1 where da.id = ?2")
    int updateEstadoDocumentoAceptacionById(int idEstadoDocumentoAceptacion, int idDocumentoAceptacion);

    @Query(nativeQuery = true,value ="SELECT * FROM DOCUMENTO_ACEPTACION WHERE ID_ESTADO_DOCUMENTO_ACEPTACION not in (4) and FECHA_EMISION between ?1 and ?2 and PROVEEDOR_RUC = ?3 AND (NUMERO_ORDEN_COMPRA like '%'||upper(?4)||'%' or NUMERO_ORDEN_COMPRA='') AND (NUMERO_GUIA_PROVEEDOR like '%'||upper(?5)||'%' or NUMERO_GUIA_PROVEEDOR='') AND ID_TIPO_DOCUMENTO_ACEPTACION = ?8  LIMIT ?6 OFFSET ?7")
    List<DocumentoAceptacion> getDocumentoAceptacionByFechaRegistroBetweenAndProveedorRucPaga (Date fechaInicio, Date fechaFin, String proveedorRuc, String nroOC, String nroGP, Integer nroRegistros, Integer paginaMostrar, Integer tipoDocumento);

    @Query(nativeQuery = true,value ="SELECT * FROM DOCUMENTO_ACEPTACION WHERE ID_ESTADO_DOCUMENTO_ACEPTACION not in (4) and FECHA_EMISION between ?1 and ?2 and PROVEEDOR_RUC = ?3 AND (NUMERO_ORDEN_COMPRA like '%'||upper(?4)||'%' or NUMERO_ORDEN_COMPRA='') AND ID_TIPO_DOCUMENTO_ACEPTACION = ?7 LIMIT ?5 OFFSET ?6")
    List<DocumentoAceptacion> getDocumentoAceptacionByFechaRegistroBetweenAndProveedorRucPagb (Date fechaInicio, Date fechaFin, String proveedorRuc, String nroOC, Integer nroRegistros, Integer paginaMostrar, Integer tipoDocumento);

    @Query(nativeQuery = true,value ="SELECT * FROM DOCUMENTO_ACEPTACION WHERE ID_ESTADO_DOCUMENTO_ACEPTACION not in (4) and FECHA_EMISION between ?1 and ?2 AND (NUMERO_ORDEN_COMPRA like '%'||upper(?3)||'%' or NUMERO_ORDEN_COMPRA='') AND (NUMERO_GUIA_PROVEEDOR like '%'||upper(?4)||'%' or NUMERO_GUIA_PROVEEDOR='') AND ID_TIPO_DOCUMENTO_ACEPTACION = ?7  LIMIT ?5 OFFSET ?6")
    List<DocumentoAceptacion> getDocumentoAceptacionByFechaRegistroBetweenPAga (Date fechaInicio, Date fechaFin, String nroOC, String nroGP, Integer nroRegistros, Integer paginaMostrar, Integer tipoDocumento);

    @Query(nativeQuery = true,value ="SELECT * FROM DOCUMENTO_ACEPTACION WHERE ID_ESTADO_DOCUMENTO_ACEPTACION not in (4) and FECHA_EMISION between ?1 and ?2 AND (NUMERO_ORDEN_COMPRA like '%'||upper(?3)||'%' or NUMERO_ORDEN_COMPRA='') AND ID_TIPO_DOCUMENTO_ACEPTACION = ?6 LIMIT ?4 OFFSET ?5")
    List<DocumentoAceptacion> getDocumentoAceptacionByFechaRegistroBetweenPAgb (Date fechaInicio, Date fechaFin, String nroOC, Integer nroRegistros, Integer paginaMostrar, Integer tipoDocumento);

    DocumentoAceptacion getDocumentoAceptacionByNumeroDocumentoAceptacionAndNumeroOrdenCompra(String d, String o);
    @Query("SELECT da FROM DocumentoAceptacion da " +
            "where da.idEstadoDocumentoAceptacion not in (4) and da.fechaEmision between :fechaInicio and :fechaFin " +
            "and da.proveedorRuc like %:ruc% " +
            "and da.numeroDocumentoAceptacion like %:numeroDocumentoAceptacion% " +
            "and da.numeroOrdenCompra like %:numeroOrdenCompra% " +
            "and da.numeroGuiaProveedor like %:numeroGuiaProveedor% " +
            "and da.idOrdenCompra != null")
    List<DocumentoAceptacion> getDocumentoAceptacionByFechaRegistroBetweenAndFilters(@Param("fechaInicio") Date fechaInicio,
                                                                                     @Param("fechaFin") Date fechaFin,
                                                                                     @Param("ruc") String ruc,
                                                                                     @Param("numeroDocumentoAceptacion") String numeroDocumentoAceptacion,
                                                                                     @Param("numeroOrdenCompra") String numeroOrdenCompra,
                                                                                     @Param("numeroGuiaProveedor") String numeroGuiaProveedor);
    @Query("SELECT da FROM DocumentoAceptacion da " +
            "where da.idEstadoDocumentoAceptacion not in (4) and da.fechaEmision between :fechaInicio and :fechaFin " +
            "and da.proveedorRuc like %:ruc% " +
            "and da.numeroDocumentoAceptacion like %:numeroDocumentoAceptacion% " +
            "and da.numeroOrdenCompra like %:numeroOrdenCompra% " +
            "and da.idOrdenCompra != null")
    List<DocumentoAceptacion> getDocumentoAceptacionByFechaRegistroBetweenAndFiltersNGP(@Param("fechaInicio") Date fechaInicio,
                                                                                        @Param("fechaFin") Date fechaFin,
                                                                                        @Param("ruc") String ruc,
                                                                                        @Param("numeroDocumentoAceptacion") String numeroDocumentoAceptacion,
                                                                                        @Param("numeroOrdenCompra") String numeroOrdenCompra);

}