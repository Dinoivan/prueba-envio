package com.incloud.hcp.bean;


import com.incloud.hcp.domain.LicitacionAdjunto;
import com.incloud.hcp.domain.LicitacionDetalle;
import com.incloud.hcp.domain.LicitacionSubetapa;

import java.io.Serializable;
import java.util.List;

/**
 * Created by USER on 28/09/2017.
 */
public class LicitacionRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idLicitacion;
    private String nroLicitacionString;
    private Integer nroLicitacion;
    private Integer anioLicitacion;
    private Integer idAlmacen;
    private String desAlmacen;
    private Integer idCentroLogistico;
    private String desCentroLogistico;
    private Integer idClaseDocumento;
    private String desClaseDocumento;
    private String codGradoUrgencia;
    private String desGradoUrgencia;
    private Integer idMoneda;
    private String desMoneda;
    private String codPuntoEntrega;
    private String desPuntoEntrega;
    private String fechaEntregaInicio;
    private String fechaRecepcionOferta;
    private String fechaUltimaRenegociacion;
    private String estadoLicitacion;
    private String comentarioAnulacion;
    private String comentarioLicitacion;
    private String fechaPublicacion;
    private List<LicitacionDetalle> detalleLicitacion;
    private List<Condicion> condicionLicitacion;
    private List<ProveedorCustom> proveedorLicitacion;
    private List<LicitacionAdjunto> adjuntoLicitacion;
    private List<LicitacionSubetapa> subetapaLicitacion;

    private String usuarioPublicacionId;
    private String usuarioPublicacionName;
    private String usuarioPublicacionEmail;
    private String numeroPeticionOfertaLicitacionSap;


    public LicitacionRequest() {
    }


    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getDesAlmacen() {
        return desAlmacen;
    }

    public void setDesAlmacen(String desAlmacen) {
        this.desAlmacen = desAlmacen;
    }

    public Integer getIdCentroLogistico() {
        return idCentroLogistico;
    }

    public void setIdCentroLogistico(Integer idCentroLogistico) {
        this.idCentroLogistico = idCentroLogistico;
    }

    public String getDesCentroLogistico() {
        return desCentroLogistico;
    }

    public void setDesCentroLogistico(String desCentroLogistico) {
        this.desCentroLogistico = desCentroLogistico;
    }

    public Integer getIdClaseDocumento() {
        return idClaseDocumento;
    }

    public void setIdClaseDocumento(Integer idClaseDocumento) {
        this.idClaseDocumento = idClaseDocumento;
    }

    public String getDesClaseDocumento() {
        return desClaseDocumento;
    }

    public void setDesClaseDocumento(String desClaseDocumento) {
        this.desClaseDocumento = desClaseDocumento;
    }

    public String getCodGradoUrgencia() {
        return codGradoUrgencia;
    }

    public void setCodGradoUrgencia(String codGradoUrgencia) {
        this.codGradoUrgencia = codGradoUrgencia;
    }

    public String getDesGradoUrgencia() {
        return desGradoUrgencia;
    }

    public void setDesGradoUrgencia(String desGradoUrgencia) {
        this.desGradoUrgencia = desGradoUrgencia;
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getDesMoneda() {
        return desMoneda;
    }

    public void setDesMoneda(String desMoneda) {
        this.desMoneda = desMoneda;
    }

    public String getCodPuntoEntrega() {
        return codPuntoEntrega;
    }

    public void setCodPuntoEntrega(String codPuntoEntrega) {
        this.codPuntoEntrega = codPuntoEntrega;
    }

    public String getDesPuntoEntrega() {
        return desPuntoEntrega;
    }

    public void setDesPuntoEntrega(String desPuntoEntrega) {
        this.desPuntoEntrega = desPuntoEntrega;
    }

    public String getFechaEntregaInicio() {
        return fechaEntregaInicio;
    }

    public void setFechaEntregaInicio(String fechaEntregaInicio) {
        this.fechaEntregaInicio = fechaEntregaInicio;
    }

    public String getFechaRecepcionOferta() {
        return fechaRecepcionOferta;
    }

    public void setFechaRecepcionOferta(String fechaRecepcionOferta) {
        this.fechaRecepcionOferta = fechaRecepcionOferta;
    }

    public String getComentarioLicitacion() {
        return comentarioLicitacion;
    }

    public void setComentarioLicitacion(String comentarioLicitacion) {
        this.comentarioLicitacion = comentarioLicitacion;
    }

    public List<LicitacionDetalle> getDetalleLicitacion() {
        return detalleLicitacion;
    }

    public void setDetalleLicitacion(List<LicitacionDetalle> detalleLicitacion) {
        this.detalleLicitacion = detalleLicitacion;
    }

    public List<Condicion> getCondicionLicitacion() {
        return condicionLicitacion;
    }

    public void setCondicionLicitacion(List<Condicion> condicionLicitacion) {
        this.condicionLicitacion = condicionLicitacion;
    }

    public List<ProveedorCustom> getProveedorLicitacion() {
        return proveedorLicitacion;
    }

    public void setProveedorLicitacion(List<ProveedorCustom> proveedorLicitacion) {
        this.proveedorLicitacion = proveedorLicitacion;
    }

    public Integer getIdLicitacion() {
        return idLicitacion;
    }

    public void setIdLicitacion(Integer idLicitacion) {
        this.idLicitacion = idLicitacion;
    }

    public String getNroLicitacionString() {
        return nroLicitacionString;
    }

    public void setNroLicitacionString(String nroLicitacionString) {
        this.nroLicitacionString = nroLicitacionString;
    }

    public String getEstadoLicitacion() {
        return estadoLicitacion;
    }

    public void setEstadoLicitacion(String estadoLicitacion) {
        this.estadoLicitacion = estadoLicitacion;
    }

    public String getComentarioAnulacion() {
        return comentarioAnulacion;
    }

    public void setComentarioAnulacion(String comentarioAnulacion) {
        this.comentarioAnulacion = comentarioAnulacion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Integer getNroLicitacion() {
        return nroLicitacion;
    }

    public void setNroLicitacion(Integer nroLicitacion) {
        this.nroLicitacion = nroLicitacion;
    }

    public Integer getAnioLicitacion() {
        return anioLicitacion;
    }

    public void setAnioLicitacion(Integer anioLicitacion) {
        this.anioLicitacion = anioLicitacion;
    }

    public String getUsuarioPublicacionId() {
        return usuarioPublicacionId;
    }

    public void setUsuarioPublicacionId(String usuarioPublicacionId) {
        this.usuarioPublicacionId = usuarioPublicacionId;
    }

    public String getUsuarioPublicacionName() {
        return usuarioPublicacionName;
    }

    public void setUsuarioPublicacionName(String usuarioPublicacionName) {
        this.usuarioPublicacionName = usuarioPublicacionName;
    }

    public String getUsuarioPublicacionEmail() {
        return usuarioPublicacionEmail;
    }

    public void setUsuarioPublicacionEmail(String usuarioPublicacionEmail) {
        this.usuarioPublicacionEmail = usuarioPublicacionEmail;
    }

    public String getNumeroPeticionOfertaLicitacionSap() {
        return numeroPeticionOfertaLicitacionSap;
    }

    public void setNumeroPeticionOfertaLicitacionSap(String numeroPeticionOfertaLicitacionSap) {
        this.numeroPeticionOfertaLicitacionSap = numeroPeticionOfertaLicitacionSap;
    }

    public List<LicitacionAdjunto> getAdjuntoLicitacion() {
        return adjuntoLicitacion;
    }

    public void setAdjuntoLicitacion(List<LicitacionAdjunto> adjuntoLicitacion) {
        this.adjuntoLicitacion = adjuntoLicitacion;
    }

    public List<LicitacionSubetapa> getSubetapaLicitacion() {
        return subetapaLicitacion;
    }

    public void setSubetapaLicitacion(List<LicitacionSubetapa> subetapaLicitacion) {
        this.subetapaLicitacion = subetapaLicitacion;
    }

    public String getFechaUltimaRenegociacion() {
        return fechaUltimaRenegociacion;
    }

    public void setFechaUltimaRenegociacion(String fechaUltimaRenegociacion) {
        this.fechaUltimaRenegociacion = fechaUltimaRenegociacion;
    }

    @Override
    public String toString() {
        return "LicitacionRequest{" +
                "idLicitacion=" + idLicitacion +
                ", nroLicitacionString='" + nroLicitacionString + '\'' +
                ", nroLicitacion=" + nroLicitacion +
                ", anioLicitacion=" + anioLicitacion +
                ", idAlmacen=" + idAlmacen +
                ", desAlmacen='" + desAlmacen + '\'' +
                ", idCentroLogistico=" + idCentroLogistico +
                ", desCentroLogistico='" + desCentroLogistico + '\'' +
                ", idClaseDocumento=" + idClaseDocumento +
                ", desClaseDocumento='" + desClaseDocumento + '\'' +
                ", codGradoUrgencia='" + codGradoUrgencia + '\'' +
                ", desGradoUrgencia='" + desGradoUrgencia + '\'' +
                ", idMoneda=" + idMoneda +
                ", desMoneda='" + desMoneda + '\'' +
                ", codPuntoEntrega='" + codPuntoEntrega + '\'' +
                ", desPuntoEntrega='" + desPuntoEntrega + '\'' +
                ", fechaEntregaInicio='" + fechaEntregaInicio + '\'' +
                ", fechaRecepcionOferta='" + fechaRecepcionOferta + '\'' +
                ", fechaUltimaRenegociacion='" + fechaUltimaRenegociacion + '\'' +
                ", estadoLicitacion='" + estadoLicitacion + '\'' +
                ", comentarioAnulacion='" + comentarioAnulacion + '\'' +
                ", comentarioLicitacion='" + comentarioLicitacion + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", detalleLicitacion=" + detalleLicitacion +
                ", condicionLicitacion=" + condicionLicitacion +
                ", proveedorLicitacion=" + proveedorLicitacion +
                ", adjuntoLicitacion=" + adjuntoLicitacion +
                ", subetapaLicitacion=" + subetapaLicitacion +
                ", usuarioPublicacionId='" + usuarioPublicacionId + '\'' +
                ", usuarioPublicacionName='" + usuarioPublicacionName + '\'' +
                ", usuarioPublicacionEmail='" + usuarioPublicacionEmail + '\'' +
                ", numeroPeticionOfertaLicitacionSap='" + numeroPeticionOfertaLicitacionSap + '\'' +
                '}';
    }
}
