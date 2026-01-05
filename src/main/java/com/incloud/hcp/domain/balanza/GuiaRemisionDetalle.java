package com.incloud.hcp.domain.balanza;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_GUIA_REMISION_DETALLE")
public class GuiaRemisionDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "GUIA_REMISION_DETALLE_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "GUIA_REMISION_DETALLE_ID_SEQ", sequenceName = "GUIA_REMISION_DETALLE_ID_SEQ", allocationSize = 1)
    @Column(name="ID_GUIA_REMISION_DETALLE", unique=true, nullable=false)
    private Integer id;

    @Column(name = "SUBTICKET")
    private String subticket;
    @Column(name="ID_GUIA", nullable=false)
    private Integer idGuia;
    @Column(name="DOC_MATERIAL", nullable=false)
    private String docMaterial;
    @Column(name="EJERCICIO", nullable=false)
    private Integer ejercicio;
    @Column(name="POSICION", nullable=false)
    private String posicion;

    @Column(name="POSICION_DOCUMENTO", nullable=false)
    private Integer posicionDocumento;

    @Column(name="MATERIAL", nullable=false)
    private String material;
    @Column(name="TIPO_PRODUCTO")
    private String tipoProducto;
    @Column(name="ALMACEN")
    private String almacen;
    @Column(name="LOTE", nullable=false)
    private String lote;
    @Column(name="CANTIDAD")
    private Double cantidad;
    @Column(name="UM_NETO")
    private String unidadMedidaNeto;
    @Column(name="PESO_NETO")
    private Double pesoNeto;
    @Column(name="UM_SAP")
    private String unidadMedidaSap;
    @Column(name="PESO_SAP")
    private Double pesoSap;
    @Column(name="UM")
    private String unidadMedida;
    @Column(name="PEDIDO_VENTA")
    private String pedidoVenta;
    @Column(name = "FECHA_PRODUCCION")
    private Date fechaProduccion;
    @Column(name="DAM")
    private String dam;

    @Column(name = "DOC_TRASLADO")
    private String documentoTraslado;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdGuia() {
        return idGuia;
    }

    public void setIdGuia(Integer idGuia) {
        this.idGuia = idGuia;
    }

    public String getDocMaterial() {
        return docMaterial;
    }

    public void setDocMaterial(String docMaterial) {
        this.docMaterial = docMaterial;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Integer getPosicionDocumento() {
        return posicionDocumento;
    }

    public void setPosicionDocumento(Integer posicionDocumento) {
        this.posicionDocumento = posicionDocumento;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getSubticket() {
        return subticket;
    }

    public void setSubticket(String subticket) {
        this.subticket = subticket;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getUnidadMedidaNeto() {
        return unidadMedidaNeto;
    }

    public void setUnidadMedidaNeto(String unidadMedidaNeto) {
        this.unidadMedidaNeto = unidadMedidaNeto;
    }

    public Double getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(Double pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public String getUnidadMedidaSap() {
        return unidadMedidaSap;
    }

    public void setUnidadMedidaSap(String unidadMedidaSap) {
        this.unidadMedidaSap = unidadMedidaSap;
    }

    public Double getPesoSap() {
        return pesoSap;
    }

    public void setPesoSap(Double pesoSap) {
        this.pesoSap = pesoSap;
    }

    public String getPedidoVenta() {
        return pedidoVenta;
    }

    public void setPedidoVenta(String pedidoVenta) {
        this.pedidoVenta = pedidoVenta;
    }

    public Date getFechaProduccion() {
        return fechaProduccion;
    }

    public void setFechaProduccion(Date fechaProduccion) {
        this.fechaProduccion = fechaProduccion;
    }

    public String getDam() {
        return dam;
    }

    public void setDam(String dam) {
        this.dam = dam;
    }

    public String getDocumentoTraslado() {
        return documentoTraslado;
    }

    public void setDocumentoTraslado(String documentoTraslado) {
        this.documentoTraslado = documentoTraslado;
    }

    @Override
    public String toString() {
        return "GuiaRemisionDetalle{" +
                "id=" + id +
                ", subticket='" + subticket + '\'' +
                ", idGuia=" + idGuia +
                ", docMaterial='" + docMaterial + '\'' +
                ", ejercicio=" + ejercicio +
                ", posicion=" + posicion +
                ", material='" + material + '\'' +
                ", tipoProducto='" + tipoProducto + '\'' +
                ", almacen='" + almacen + '\'' +
                ", lote='" + lote + '\'' +
                ", cantidad=" + cantidad +
                ", unidadMedidaNeto='" + unidadMedidaNeto + '\'' +
                ", pesoNeto=" + pesoNeto +
                ", unidadMedidaSap='" + unidadMedidaSap + '\'' +
                ", pesoSap=" + pesoSap +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", pedidoVenta='" + pedidoVenta + '\'' +
                ", fechaProduccion=" + fechaProduccion +
                '}';
    }
}
