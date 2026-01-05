package com.incloud.hcp.repository;

import com.incloud.hcp.domain.Prefactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrefacturaRepository extends JpaRepository<Prefactura, Integer>, JpaSpecificationExecutor {

    @Query("SELECT p FROM Prefactura p where p.idEstadoPrefactura = ?1")
    List<Prefactura> getAllByIdEstado(Integer idEstadoPrefactura);

    @Query("SELECT p FROM Prefactura p where p.proveedorRuc = ?1")
    List<Prefactura> getAllByRuc(String ruc);

    @Query("SELECT p FROM Prefactura p where p.fechaEmision between ?1 and ?2 and p.proveedorRuc = ?3")
    List<Prefactura> getPrefacturaByFechaRegistroBetweenAndProveedorRuc(Date fechaInicio, Date fechaFin, String proveedorRuc);

    @Query("SELECT p FROM Prefactura p where ((?1 is null and ?2 is null) or (p.fechaEmision between ?1 and ?2)) " +
            "and ((?3 is null and ?4 is null) or (p.fechaRecepcion between ?3 and ?4)) " +
            "and (?5 is null or p.proveedorRuc = ?5) " +
            "and (?6 is null or p.referencia = ?6)")
    List<Prefactura> getPrefacturaByFechaEmisionBetweenFechaEntradaBetweenAndProveedorRuc(Date fechaEmisionInicio, Date fechaEmisionFin,
                                                                                          Date fechaEntradaInicio, Date fechaEntradaFin,
                                                                                          String proveedorRuc,
                                                                                          String referencia);

    @Query("SELECT p FROM Prefactura p where ((?1 is null and ?2 is null) or (p.fechaEmision between ?1 and ?2)) " +
            "and ((?3 is null and ?4 is null) or (p.fechaRecepcion between ?3 and ?4)) " +
            "and (?5 is null or p.proveedorRuc = ?5) " +
            "and (?6 is null or p.referencia = ?6) " +
            "and (?7 is null or UPPER(p.usuarioComprador) like UPPER(?7)) " +
            "and (?8 is null or UPPER(p.centro) like UPPER(?8)) " +
            "and (?9 is null or p.idEstadoPrefactura = ?9) " +
            "order by p.fechaRecepcion desc")
    List<Prefactura> getPrefacturaByAllFilters(Date fechaEmisionInicio, Date fechaEmisionFin,
                                               Date fechaEntradaInicio, Date fechaEntradaFin,
                                               String proveedorRuc,
                                               String referencia,
                                               String xComprador,
                                               String xCentro,
                                               Integer idEstado);


    @Query("SELECT p FROM Prefactura p where ((?1 is null and ?2 is null) or (p.fechaEmision between ?1 and ?2)) " +
            "and ((?3 is null and ?4 is null) or (p.fechaRecepcion between ?3 and ?4)) " +
            "and (?5 is null or p.proveedorRuc = ?5) " +
            "and (?6 is null or p.referencia = ?6) " +
            "and (?7 is null or UPPER(p.usuarioComprador) like UPPER(?7)) " +
            "and (?8 is null or UPPER(p.centro) like UPPER(?8)) " +
            "and ((?9 is null and p.idEstadoPrefactura <> 4) or p.idEstadoPrefactura = ?9) " +
            "order by p.fechaRecepcion desc")
    List<Prefactura> getPrefacturaListForExcel(Date fechaEmisionInicio, Date fechaEmisionFin,
                                               Date fechaEntradaInicio, Date fechaEntradaFin,
                                               String proveedorRuc,
                                               String referencia,
                                               String xComprador,
                                               String xCentro,
                                               Integer idEstado);


    @Query("SELECT p FROM Prefactura p where p.fechaEmision between ?1 and ?2")
    List<Prefactura> getPrefacturaByFechaRegistroBetween(Date fechaInicio, Date fechaFin);

    @Query("SELECT p FROM Prefactura p where p.id= ?1")
    Optional<Prefactura> getOneById(Integer idPrefactura);

    @Query("SELECT p FROM Prefactura p where p.proveedorRuc = ?1 and p.referencia = ?2")
    List<Prefactura> getPrefacturaListByRucAndReferencia(String ruc, String referencia);

    @Query("SELECT p FROM Prefactura p where p.proveedorRuc = ?1 and p.referencia = ?2 and p.idEstadoPrefactura = ?3")
    List<Prefactura> getPrefacturaListByRucAndReferenciaAndIdEstado(String ruc, String referencia, Integer idEstadoPrefactura);

    @Query("SELECT p FROM Prefactura p where p.proveedorRuc = ?1 and p.referencia = ?2 and p.idEstadoPrefactura in ?3")
    List<Prefactura> getPrefacturaListByRucAndReferenciaAndIdEstadoList(String ruc, String referencia, List<Integer> idEstadoPrefacturaList);

    @Query("SELECT p FROM Prefactura p where p.idEstadoPrefactura = ?1 and p.codigoSap = ?2 and p.ejercicio = ?3")
    Optional<Prefactura> getPrefacturaByIdEstadoAndCodigoSapAndEjercicio(Integer idEstadoPrefactura, String codigoSap, String ejercicio);

    @Query("SELECT p FROM Prefactura p where p.idEstadoPrefactura in ?1 and p.codigoSap = ?2 and p.ejercicio = ?3")
    Optional<Prefactura> getPrefacturaByIdEstadoListAndCodigoSapAndEjercicio(List<Integer> idEstadoPrefacturaList, String codigoSap, String ejercicio);

    @Query("SELECT p FROM Prefactura p where p.idEstadoPrefactura in ?1 and p.codigoSap = ?2 and p.ejercicio = ?3")
    Prefactura findPrefacturaByIdEstadoListAndCodigoSapAndEjercicio(List<Integer> idEstadoPrefacturaList, String codigoSap, String ejercicio);
    @Query("select p from Prefactura p where p.fechaRecepcion between ?1 and ?2 and p.estadoPrefactura.id = ?3")
    List<Prefactura> getPrefacturasByFechaAndEstado(Date fechaRecepcionInicio, Date fechaRecepcionFin, Integer estadoPrefacturaId);

    @Transactional
    @Modifying
    @Query("UPDATE Prefactura t SET t.pdfEcmPathCf = ?2, t.archivoIdPdfCf = ?3 WHERE t.id=?1")
    public void updateMigraDocPDF(Integer id, String pdfEcmPathCf, String archivoIdPdfCf);


    @Transactional
    @Modifying
    @Query("UPDATE Prefactura t SET t.xmlEcmPathCf = ?2, t.archivoIdXMLCf = ?3 WHERE t.id=?1")
    public void updateMigraDocXML(Integer id, String xmlEcmPathCf, String archivoIdXMLCf);

    @Query("SELECT p.id FROM Prefactura p where p.xmlEcmPath is not null and p.pdfEcmPath is not null and p.xmlEcmPathCf is null and p.pdfEcmPathCf is null order by id desc")
    List<Integer> getPrefacturaMigrarDesc();
    @Query("SELECT p.id FROM Prefactura p where p.xmlEcmPath is not null and p.pdfEcmPath is not null and p.xmlEcmPathCf is null and p.pdfEcmPathCf is null order by id asc")
    List<Integer> getPrefacturaMigrar();

}