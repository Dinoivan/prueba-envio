package com.incloud.hcp.domain.almacen;

import com.incloud.hcp.domain.almacen.OrdenDespachoDetalle;
import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entidad que representa el detalle (posiciones) de un despacho en el módulo de Almacenes
 * Contiene las líneas/items de cada despacho relacionadas con las posiciones de la orden de despacho
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "GA_GUIA_DESPACHO_DETALLE")
public class GuiaDespachoDetalle extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "GA_DESPACHO_DETALLE_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "GA_DESPACHO_DETALLE_ID_SEQ", sequenceName = "GA_DESPACHO_DETALLE_ID_SEQ", allocationSize = 1)
    @Column(name = "ID_DESPACHO_DETALLE", unique = true, nullable = false)
    private Integer id;

    @Column(name = "ID_GUIA_DESPACHO", nullable = false)
    private Integer idGuiaDespacho;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = GuiaDespacho.class)
    @JoinColumn(name = "ID_GUIA_DESPACHO", referencedColumnName = "ID_GUIA_DESPACHO", insertable = false, updatable = false)
    private GuiaDespacho despacho;

    @Column(name = "ID_ORDEN_DESPACHO_DETALLE", nullable = false)
    private Integer idOrdenDespachoDetalle;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = OrdenDespachoDetalle.class)
    @JoinColumn(name = "ID_ORDEN_DESPACHO_DETALLE", referencedColumnName = "ID_ORDEN_DESPACHO_DETALLE", insertable = false, updatable = false)
    private OrdenDespachoDetalle ordenDespachoDetalle;

    @Column(name = "POSICION", nullable = false, length = 5)
    private String posicion;

    @Column(name = "CODIGO_MATERIAL", nullable = false, length = 18)
    private String codigoMaterial;

    @Column(name = "DESCRIPCION_MATERIAL", length = 40)
    private String descripcionMaterial;

    @Column(name = "CANTIDAD_PEDIDO", nullable = true, precision = 13, scale = 3)
    private BigDecimal cantidadPedido;

    @Column(name = "CANTIDAD_ENTREGADA_ANTERIOR", precision = 13, scale = 3)
    private BigDecimal cantidadEntregadaAnterior;

    @Column(name = "CANTIDAD_ESTE_DESPACHO", nullable = false, precision = 13, scale = 3)
    private BigDecimal cantidadEsteDespacho;

    @Column(name = "CANTIDAD_ORIGINAL", precision = 13, scale = 3)
    private BigDecimal cantidadOriginal;

    @Column(name = "UNIDAD_MEDIDA", nullable = false, length = 3)
    private String unidadMedida;

    @Column(name = "FECHA_ENTREGA_OC")
    private Date fechaEntregaOc;

    @Column(name = "CENTRO", length = 4)
    private String centro;

    @Column(name="DENOMINACION_CENTRO", length = 30)
    private String denominacionCentro;

    @Column(name = "NUMERO_ORDEN_DESPACHO", nullable = false, length = 20)
    private String numeroOrdenDespacho;

    @Column(name="CODIGO_SAP_ALMACEN", length = 4)
    private String codigoSapAlmacen;

    @Column(name="DENOMINACION_ALMACEN", length = 20)
    private String denominacionAlmacen;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdGuiaDespacho() {
        return idGuiaDespacho;
    }

    public void setIdGuiaDespacho(Integer idGuiaDespacho) {
        this.idGuiaDespacho = idGuiaDespacho;
    }

    public GuiaDespacho getDespacho() {
        return despacho;
    }

    public void setDespacho(GuiaDespacho despacho) {
        this.despacho = despacho;
    }

    public Integer getIdOrdenDespachoDetalle() {
        return idOrdenDespachoDetalle;
    }

    public void setIdOrdenDespachoDetalle(Integer idOrdenDespachoDetalle) {
        this.idOrdenDespachoDetalle = idOrdenDespachoDetalle;
    }

    public OrdenDespachoDetalle getOrdenDespachoDetalle() {
        return ordenDespachoDetalle;
    }

    public void setOrdenDespachoDetalle(OrdenDespachoDetalle ordenDespachoDetalle) {
        this.ordenDespachoDetalle = ordenDespachoDetalle;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getCodigoMaterial() {
        return codigoMaterial;
    }

    public void setCodigoMaterial(String codigoMaterial) {
        this.codigoMaterial = codigoMaterial;
    }

    public String getDescripcionMaterial() {
        return descripcionMaterial;
    }

    public void setDescripcionMaterial(String descripcionMaterial) {
        this.descripcionMaterial = descripcionMaterial;
    }

    public BigDecimal getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(BigDecimal cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public BigDecimal getCantidadEntregadaAnterior() {
        return cantidadEntregadaAnterior;
    }

    public void setCantidadEntregadaAnterior(BigDecimal cantidadEntregadaAnterior) {
        this.cantidadEntregadaAnterior = cantidadEntregadaAnterior;
    }

    public BigDecimal getCantidadEsteDespacho() {
        return cantidadEsteDespacho;
    }

    public void setCantidadEsteDespacho(BigDecimal cantidadEsteDespacho) {
        this.cantidadEsteDespacho = cantidadEsteDespacho;
    }

    public BigDecimal getCantidadOriginal() {
        return cantidadOriginal;
    }

    public void setCantidadOriginal(BigDecimal cantidadOriginal) {
        this.cantidadOriginal = cantidadOriginal;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Date getFechaEntregaOc() {
        return fechaEntregaOc;
    }

    public void setFechaEntregaOc(Date fechaEntregaOc) {
        this.fechaEntregaOc = fechaEntregaOc;
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

    public String getNumeroOrdenDespacho() {
        return numeroOrdenDespacho;
    }

    public void setNumeroOrdenDespacho(String numeroOrdenDespacho) {
        this.numeroOrdenDespacho = numeroOrdenDespacho;
    }

    public String getCodigoSapAlmacen() {
        return codigoSapAlmacen;
    }

    public void setCodigoSapAlmacen(String codigoSapAlmacen) {
        this.codigoSapAlmacen = codigoSapAlmacen;
    }

    public String getDenominacionAlmacen() {
        return denominacionAlmacen;
    }

    public void setDenominacionAlmacen(String denominacionAlmacen) {
        this.denominacionAlmacen = denominacionAlmacen;
    }

    @Override
    public String toString() {
        return "GuiaDespachoDetalle{" +
                "id=" + id +
                ", idGuiaDespacho=" + idGuiaDespacho +
                ", despacho=" + despacho +
                ", idOrdenDespachoDetalle=" + idOrdenDespachoDetalle +
                ", ordenDespachoDetalle=" + ordenDespachoDetalle +
                ", posicion='" + posicion + '\'' +
                ", codigoMaterial='" + codigoMaterial + '\'' +
                ", descripcionMaterial='" + descripcionMaterial + '\'' +
                ", cantidadPedido=" + cantidadPedido +
                ", cantidadEntregadaAnterior=" + cantidadEntregadaAnterior +
                ", cantidadEsteDespacho=" + cantidadEsteDespacho +
                ", cantidadOriginal=" + cantidadOriginal +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", fechaEntregaOc=" + fechaEntregaOc +
                ", centro='" + centro + '\'' +
                ", denominacionCentro='" + denominacionCentro + '\'' +
                ", numeroOrdenDespacho='" + numeroOrdenDespacho + '\'' +
                ", codigoSapAlmacen='" + codigoSapAlmacen + '\'' +
                ", denominacionAlmacen='" + denominacionAlmacen + '\'' +
                '}';
    }
}
