package com.incloud.hcp.dto;

import com.incloud.hcp.domain.CentroAlmacen;
import com.incloud.hcp.domain.ClaseDocumento;
import com.incloud.hcp.domain.LicitacionProveedor;
import com.incloud.hcp.domain.Moneda;

import java.sql.Timestamp;

public class LicitacionAdjudicadoDTO {

    private Integer idLicitacion;
    private String comentarioAnulacion;
    private String comentarioLicitacion;
    private String estadoLicitacion;
    private Timestamp fechaCierreRecepcionOferta;
    private Timestamp fechaCreacion;
    private Timestamp fechaEntregaInicio;
    private Timestamp fechaModificacion;
    private Timestamp fechaPublicacion;
    private String necesidadUrgencia;
    private Integer nroLicitacion;
    private Integer anioLicitacion;
    private String usuarioCreacion;
    private String usuarioModificacion;
    private String puntoEntrega;
    private String usuarioPublicacionId;
    private String usuarioPublicacionName;
    private String usuarioPublicacionEmail;
    private String usuarioAnulacionId;
    private String indRepublicado;

    private String fechaCierreRecepcionOfertaString;
    private String fechaCreacionString;
    private String fechaPublicacionString;
    private String nroLicitacionString;
    private String nroLicitacionCero;
    private Integer nroProveedoresACotizar;
    private Integer nroProveedoresEnviaronCotizacion;
    private String fechaEntregaInicioString;
    private String descripcionPuntoEntrega;
    private String fechaUltimaRenegociacionString;


    private CentroAlmacen centroAlmacen1;
    private CentroAlmacen centroAlmacen2;
    private ClaseDocumento claseDocumento;
    private Moneda moneda;

    private LicitacionProveedor licitacionProveedor;

    public Integer getIdLicitacion() {
        return idLicitacion;
    }

    public void setIdLicitacion(Integer idLicitacion) {
        this.idLicitacion = idLicitacion;
    }

    public String getComentarioAnulacion() {
        return comentarioAnulacion;
    }

    public void setComentarioAnulacion(String comentarioAnulacion) {
        this.comentarioAnulacion = comentarioAnulacion;
    }

    public String getComentarioLicitacion() {
        return comentarioLicitacion;
    }

    public void setComentarioLicitacion(String comentarioLicitacion) {
        this.comentarioLicitacion = comentarioLicitacion;
    }

    public String getEstadoLicitacion() {
        return estadoLicitacion;
    }

    public void setEstadoLicitacion(String estadoLicitacion) {
        this.estadoLicitacion = estadoLicitacion;
    }

    public Timestamp getFechaCierreRecepcionOferta() {
        return fechaCierreRecepcionOferta;
    }

    public void setFechaCierreRecepcionOferta(Timestamp fechaCierreRecepcionOferta) {
        this.fechaCierreRecepcionOferta = fechaCierreRecepcionOferta;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaEntregaInicio() {
        return fechaEntregaInicio;
    }

    public void setFechaEntregaInicio(Timestamp fechaEntregaInicio) {
        this.fechaEntregaInicio = fechaEntregaInicio;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Timestamp getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Timestamp fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getNecesidadUrgencia() {
        return necesidadUrgencia;
    }

    public void setNecesidadUrgencia(String necesidadUrgencia) {
        this.necesidadUrgencia = necesidadUrgencia;
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

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getPuntoEntrega() {
        return puntoEntrega;
    }

    public void setPuntoEntrega(String puntoEntrega) {
        this.puntoEntrega = puntoEntrega;
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

    public String getUsuarioAnulacionId() {
        return usuarioAnulacionId;
    }

    public void setUsuarioAnulacionId(String usuarioAnulacionId) {
        this.usuarioAnulacionId = usuarioAnulacionId;
    }

    public String getIndRepublicado() {
        return indRepublicado;
    }

    public void setIndRepublicado(String indRepublicado) {
        this.indRepublicado = indRepublicado;
    }

    public String getFechaCierreRecepcionOfertaString() {
        return fechaCierreRecepcionOfertaString;
    }

    public void setFechaCierreRecepcionOfertaString(String fechaCierreRecepcionOfertaString) {
        this.fechaCierreRecepcionOfertaString = fechaCierreRecepcionOfertaString;
    }

    public String getFechaCreacionString() {
        return fechaCreacionString;
    }

    public void setFechaCreacionString(String fechaCreacionString) {
        this.fechaCreacionString = fechaCreacionString;
    }

    public String getFechaPublicacionString() {
        return fechaPublicacionString;
    }

    public void setFechaPublicacionString(String fechaPublicacionString) {
        this.fechaPublicacionString = fechaPublicacionString;
    }

    public String getNroLicitacionString() {
        return nroLicitacionString;
    }

    public void setNroLicitacionString(String nroLicitacionString) {
        this.nroLicitacionString = nroLicitacionString;
    }

    public String getNroLicitacionCero() {
        return nroLicitacionCero;
    }

    public void setNroLicitacionCero(String nroLicitacionCero) {
        this.nroLicitacionCero = nroLicitacionCero;
    }

    public Integer getNroProveedoresACotizar() {
        return nroProveedoresACotizar;
    }

    public void setNroProveedoresACotizar(Integer nroProveedoresACotizar) {
        this.nroProveedoresACotizar = nroProveedoresACotizar;
    }

    public Integer getNroProveedoresEnviaronCotizacion() {
        return nroProveedoresEnviaronCotizacion;
    }

    public void setNroProveedoresEnviaronCotizacion(Integer nroProveedoresEnviaronCotizacion) {
        this.nroProveedoresEnviaronCotizacion = nroProveedoresEnviaronCotizacion;
    }

    public String getFechaEntregaInicioString() {
        return fechaEntregaInicioString;
    }

    public void setFechaEntregaInicioString(String fechaEntregaInicioString) {
        this.fechaEntregaInicioString = fechaEntregaInicioString;
    }

    public String getDescripcionPuntoEntrega() {
        return descripcionPuntoEntrega;
    }

    public void setDescripcionPuntoEntrega(String descripcionPuntoEntrega) {
        this.descripcionPuntoEntrega = descripcionPuntoEntrega;
    }

    public String getFechaUltimaRenegociacionString() {
        return fechaUltimaRenegociacionString;
    }

    public void setFechaUltimaRenegociacionString(String fechaUltimaRenegociacionString) {
        this.fechaUltimaRenegociacionString = fechaUltimaRenegociacionString;
    }

    public CentroAlmacen getCentroAlmacen1() {
        return centroAlmacen1;
    }

    public void setCentroAlmacen1(CentroAlmacen centroAlmacen1) {
        this.centroAlmacen1 = centroAlmacen1;
    }

    public CentroAlmacen getCentroAlmacen2() {
        return centroAlmacen2;
    }

    public void setCentroAlmacen2(CentroAlmacen centroAlmacen2) {
        this.centroAlmacen2 = centroAlmacen2;
    }

    public ClaseDocumento getClaseDocumento() {
        return claseDocumento;
    }

    public void setClaseDocumento(ClaseDocumento claseDocumento) {
        this.claseDocumento = claseDocumento;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public LicitacionProveedor getLicitacionProveedor() {
        return licitacionProveedor;
    }

    public void setLicitacionProveedor(LicitacionProveedor licitacionProveedor) {
        this.licitacionProveedor = licitacionProveedor;
    }

    @Override
    public String toString() {
        return "LicitacionAdjudicadoDTO{" +
                "idLicitacion=" + idLicitacion +
                ", comentarioAnulacion='" + comentarioAnulacion + '\'' +
                ", comentarioLicitacion='" + comentarioLicitacion + '\'' +
                ", estadoLicitacion='" + estadoLicitacion + '\'' +
                ", fechaCierreRecepcionOferta=" + fechaCierreRecepcionOferta +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaEntregaInicio=" + fechaEntregaInicio +
                ", fechaModificacion=" + fechaModificacion +
                ", fechaPublicacion=" + fechaPublicacion +
                ", necesidadUrgencia='" + necesidadUrgencia + '\'' +
                ", nroLicitacion=" + nroLicitacion +
                ", anioLicitacion=" + anioLicitacion +
                ", usuarioCreacion='" + usuarioCreacion + '\'' +
                ", usuarioModificacion='" + usuarioModificacion + '\'' +
                ", puntoEntrega='" + puntoEntrega + '\'' +
                ", usuarioPublicacionId='" + usuarioPublicacionId + '\'' +
                ", usuarioPublicacionName='" + usuarioPublicacionName + '\'' +
                ", usuarioPublicacionEmail='" + usuarioPublicacionEmail + '\'' +
                ", usuarioAnulacionId='" + usuarioAnulacionId + '\'' +
                ", indRepublicado='" + indRepublicado + '\'' +
                ", fechaCierreRecepcionOfertaString='" + fechaCierreRecepcionOfertaString + '\'' +
                ", fechaCreacionString='" + fechaCreacionString + '\'' +
                ", fechaPublicacionString='" + fechaPublicacionString + '\'' +
                ", nroLicitacionString='" + nroLicitacionString + '\'' +
                ", nroLicitacionCero='" + nroLicitacionCero + '\'' +
                ", nroProveedoresACotizar=" + nroProveedoresACotizar +
                ", nroProveedoresEnviaronCotizacion=" + nroProveedoresEnviaronCotizacion +
                ", fechaEntregaInicioString='" + fechaEntregaInicioString + '\'' +
                ", descripcionPuntoEntrega='" + descripcionPuntoEntrega + '\'' +
                ", fechaUltimaRenegociacionString='" + fechaUltimaRenegociacionString + '\'' +
                ", centroAlmacen1=" + centroAlmacen1 +
                ", centroAlmacen2=" + centroAlmacen2 +
                ", claseDocumento=" + claseDocumento +
                ", moneda=" + moneda +
                ", licitacionProveedor=" + licitacionProveedor +
                '}';
    }
}
