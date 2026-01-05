package com.incloud.hcp.domain.almacen;

import com.incloud.hcp.domain.almacen.OrdenDespacho;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Entidad que representa un despacho en el módulo de Almacenes
 * Gestiona los despachos creados por proveedores para las órdenes de Despacho
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "GA_GUIA_DESPACHO")
public class GuiaDespacho extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "GA_DESPACHO_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "GA_DESPACHO_ID_SEQ", sequenceName = "GA_DESPACHO_ID_SEQ", allocationSize = 1)
    @Column(name = "ID_GUIA_DESPACHO", unique = true, nullable = false)
    private Integer id;

    @Column(name = "NUMERO_ORDEN_DESPACHO", nullable = false, length = 20)
    private String numeroOrdenDespacho;

    @Column(name = "ID_ORDEN_DESPACHO", nullable = false)
    private Integer idOrdenDespacho;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = OrdenDespacho.class)
    @JoinColumn(name = "ID_ORDEN_DESPACHO", referencedColumnName = "ID_ORDEN_DESPACHO", insertable = false, updatable = false)
    private OrdenDespacho ordenDespacho;

    @Column(name="PROVEEDOR_RUC", length = 16)
    private String proveedorRuc;

    @Column(name="PROVEEDOR_RAZON_SOCIAL", length = 50)
    private String proveedorRazonSocial;

    @Column(name = "ID_ESTADO_DESPACHO", nullable = false)
    private Integer idEstadoDespacho;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = EstadoDespacho.class)
    @JoinColumn(name = "ID_ESTADO_DESPACHO", referencedColumnName = "ID_ESTADO_DESPACHO", insertable = false, updatable = false)
    private EstadoDespacho estadoDespacho;

    @Column(name = "NUMERO_GUIA_REMISION", nullable = false, length = 16)
    private String numeroGuiaRemision;

    @Column(name = "FECHA_CREACION", nullable = false)
    private Timestamp fechaCreacion;

    @Column(name = "FECHA_ENTREGA", nullable = false)
    private Date fechaEntrega;

    @Column(name = "LUGAR_ENTREGA", nullable = false, length = 100)
    private String lugarEntrega;

    @Column(name = "CENTRO", nullable = false, length = 4)
    private String centro;

    @Column(name="DENOMINACION_CENTRO", length = 30)
    private String denominacionCentro;

    @Column(name = "FECHA_CONTABILIZACION")
    private Date fechaContabilizacion;

    @Column(name = "DOCUMENTO_MATERIAL", length = 10)
    private String documentoMaterial;

    @Column(name = "USUARIO_ANULO_SAP", length = 12)
    private String usuarioAnuloSap;

    @Column(name = "FECHA_ANULACION")
    private Date fechaAnulacion;

    @Column(name = "MOTIVO_RECHAZO", length = 1000)
    private String motivoRechazo;

    @Column(name = "OBSERVACIONES", length = 25)
    private String observaciones;

    @Column(name = "USUARIO_CREO", nullable = true, length = 50)
    private String usuarioCreo;

    @Column(name = "FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name = "USUARIO_MODIFICO", length = 50)
    private String usuarioModifico;

    @Column(name = "FECHA_DESCARTE")
    private Timestamp fechaDescarte;

    @Column(name = "FECHA_RECHAZO")
    private Timestamp fechaRechazo;

    @Column(name = "EJERCICIO")
    private String ejercicio;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroOrdenDespacho() {
        return numeroOrdenDespacho;
    }

    public void setNumeroOrdenDespacho(String numeroOrdenDespacho) {
        this.numeroOrdenDespacho = numeroOrdenDespacho;
    }

    public Integer getIdOrdenDespacho() {
        return idOrdenDespacho;
    }

    public void setIdOrdenDespacho(Integer idOrdenDespacho) {
        this.idOrdenDespacho = idOrdenDespacho;
    }

    public OrdenDespacho getOrdenDespacho() {
        return ordenDespacho;
    }

    public void setOrdenDespacho(OrdenDespacho ordenDespacho) {
        this.ordenDespacho = ordenDespacho;
    }

    public String getProveedorRuc() {return proveedorRuc;}

    public void setProveedorRuc(String proveedorRuc) {this.proveedorRuc = proveedorRuc;}

    public String getProveedorRazonSocial() {return proveedorRazonSocial;}

    public void setProveedorRazonSocial(String proveedorRazonSocial) {this.proveedorRazonSocial = proveedorRazonSocial;}

    public Integer getIdEstadoDespacho() {
        return idEstadoDespacho;
    }

    public void setIdEstadoDespacho(Integer idEstadoDespacho) {
        this.idEstadoDespacho = idEstadoDespacho;
    }

    public EstadoDespacho getEstadoDespacho() {
        return estadoDespacho;
    }

    public void setEstadoDespacho(EstadoDespacho estadoDespacho) {
        this.estadoDespacho = estadoDespacho;
    }

    public String getNumeroGuiaRemision() {
        return numeroGuiaRemision;
    }

    public void setNumeroGuiaRemision(String numeroGuiaRemision) {
        this.numeroGuiaRemision = numeroGuiaRemision;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getDenominacionCentro() {
        return denominacionCentro;
    }

    public void setDenominacionCentro(String denominacionCentro) {
        this.denominacionCentro = denominacionCentro;
    }

    public Date getFechaContabilizacion() {
        return fechaContabilizacion;
    }

    public void setFechaContabilizacion(Date fechaContabilizacion) {
        this.fechaContabilizacion = fechaContabilizacion;
    }

    public String getDocumentoMaterial() {
        return documentoMaterial;
    }

    public void setDocumentoMaterial(String documentoMaterial) {
        this.documentoMaterial = documentoMaterial;
    }

    public String getUsuarioAnuloSap() {
        return usuarioAnuloSap;
    }

    public void setUsuarioAnuloSap(String usuarioAnuloSap) {
        this.usuarioAnuloSap = usuarioAnuloSap;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getUsuarioCreo() {
        return usuarioCreo;
    }

    public void setUsuarioCreo(String usuarioCreo) {
        this.usuarioCreo = usuarioCreo;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModifico() {
        return usuarioModifico;
    }

    public void setUsuarioModifico(String usuarioModifico) {
        this.usuarioModifico = usuarioModifico;
    }

    public Timestamp getFechaDescarte() {
        return fechaDescarte;
    }

    public void setFechaDescarte(Timestamp fechaDescarte) {
        this.fechaDescarte = fechaDescarte;
    }

    public Timestamp getFechaRechazo() {
        return fechaRechazo;
    }

    public void setFechaRechazo(Timestamp fechaRechazo) {
        this.fechaRechazo = fechaRechazo;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    @Override
    public String toString() {
        return "GuiaDespacho{" +
                "id=" + id +
                ", numeroOrdenDespacho='" + numeroOrdenDespacho + '\'' +
                ", idOrdenDespacho=" + idOrdenDespacho +
                ", ordenDespacho=" + ordenDespacho +
                ", proveedorRuc='" + proveedorRuc + '\'' +
                ", proveedorRazonSocial='" + proveedorRazonSocial + '\'' +
                ", idEstadoDespacho=" + idEstadoDespacho +
                ", estadoDespacho=" + estadoDespacho +
                ", numeroGuiaRemision='" + numeroGuiaRemision + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaEntrega=" + fechaEntrega +
                ", lugarEntrega='" + lugarEntrega + '\'' +
                ", centro='" + centro + '\'' +
                ", denominacionCentro='" + denominacionCentro + '\'' +
                ", fechaContabilizacion=" + fechaContabilizacion +
                ", documentoMaterial='" + documentoMaterial + '\'' +
                ", usuarioAnuloSap='" + usuarioAnuloSap + '\'' +
                ", fechaAnulacion=" + fechaAnulacion +
                ", motivoRechazo='" + motivoRechazo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", usuarioCreo='" + usuarioCreo + '\'' +
                ", fechaModificacion=" + fechaModificacion +
                ", usuarioModifico='" + usuarioModifico + '\'' +
                ", fechaDescarte=" + fechaDescarte +
                ", fechaRechazo=" + fechaRechazo +
                ", ejercicio=" + ejercicio +
                '}';
    }
}
