package com.incloud.hcp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Access(AccessType.FIELD)
@Table(name="DOCUMENTO_ACEPTACION_DETALLE")
public class DocumentoAceptacionDetalle extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "DOCUMENTO_ACEPTACION_DETALLE_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "DOCUMENTO_ACEPTACION_DETALLE_ID_SEQ", sequenceName = "DOCUMENTO_ACEPTACION_DETALLE_ID_SEQ", allocationSize = 1)
	@Column(name="ID_DOCUMENTO_ACEPTACION_DETALLE", unique=true, nullable=false)
	private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DocumentoAceptacion.class)
    @JoinColumn(name="ID_DOCUMENTO_ACEPTACION", referencedColumnName = "ID_DOCUMENTO_ACEPTACION", insertable = false, updatable = false)
    private DocumentoAceptacion documentoAceptacion;

    @Column(name="ID_DOCUMENTO_ACEPTACION", nullable=false)
    private Integer idDocumentoAceptacion;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = EstadoDocumentoAceptacion.class)
    @JoinColumn(name="ID_ESTADO_DOCUMENTO_ACEPTACION_DETALLE", referencedColumnName = "ID_ESTADO_DOCUMENTO_ACEPTACION", insertable = false, updatable = false)
    private EstadoDocumentoAceptacion estadoDocumentoAceptacion;

    @Column(name="ID_ESTADO_DOCUMENTO_ACEPTACION_DETALLE", nullable=false)
    private Integer idEstadoDocumentoAceptacionDetalle;

    @Column(name="NUMERO_DOCUMENTO_ACEPTACION", length=10)
    private String numeroDocumentoAceptacion;

    @Column(name="NUMERO_ITEM")
    private Integer numeroItem;

	@Column(name="NUMERO_ORDEN_COMPRA", length=10)
	private String numeroOrdenCompra;

    @Column(name="POSICION_ORDEN_COMPRA", length=5)
    private String posicionOrdenCompra;

    @Column(name="MOVIMIENTO", length=3)
    private String movimiento;

    @Column(name="CODIGO_SAP_BIEN_SERVICIO", length=18)
    private String codigoSapBienServicio;

    @Column(name="DESCRIPCION_BIEN_SERVICIO", length=50)
    private String descripcionBienServicio;

    @Column(name="UNIDAD_MEDIDA", length=3)
    private String unidadMedida;

    @Column(name="CANTIDAD_ACEPTADA_CLIENTE", precision = 14, scale = 4)
    private BigDecimal cantidadAceptadaCliente;

    @Column(name="CANTIDAD_PENDIENTE", precision = 14, scale = 4)
    private BigDecimal cantidadPendiente;

    @Column(name="PRECIO_UNITARIO", precision = 14, scale = 4)
    private BigDecimal precioUnitario;

    @Column(name="VALOR_RECIBIDO", precision = 14, scale = 4)
    private BigDecimal valorRecibido;

    @Column(name="VALOR_RECIBIDO_MLOCAL", precision = 14, scale = 4)
    private BigDecimal valorRecibidoMonedalocal;

    @Column(name="INDICADOR_IMPUESTO", length = 2)
    private String indicadorImpuesto;

    @Column(name="NUM_DOC_ACEPTACION_RELACIONADO", length=10)
    private String numDocApectacionRelacionado;

    @Column(name="NUM_ITEM_RELACIONADO")
    private Integer numItemRelacionado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DocumentoAceptacion getDocumentoAceptacion() {
        return documentoAceptacion;
    }

    public void setDocumentoAceptacion(DocumentoAceptacion documentoAceptacion) {
        this.documentoAceptacion = documentoAceptacion;
    }

    public Integer getIdDocumentoAceptacion() {
        return idDocumentoAceptacion;
    }

    public void setIdDocumentoAceptacion(Integer idDocumentoAceptacion) {
        this.idDocumentoAceptacion = idDocumentoAceptacion;
    }

    public EstadoDocumentoAceptacion getEstadoDocumentoAceptacion() {
        return estadoDocumentoAceptacion;
    }

    public void setEstadoDocumentoAceptacion(EstadoDocumentoAceptacion estadoDocumentoAceptacion) {
        this.estadoDocumentoAceptacion = estadoDocumentoAceptacion;
    }

    public Integer getIdEstadoDocumentoAceptacionDetalle() {
        return idEstadoDocumentoAceptacionDetalle;
    }

    public void setIdEstadoDocumentoAceptacionDetalle(Integer idEstadoDocumentoAceptacionDetalle) {
        this.idEstadoDocumentoAceptacionDetalle = idEstadoDocumentoAceptacionDetalle;
    }

    public String getNumeroDocumentoAceptacion() {
        return numeroDocumentoAceptacion;
    }

    public void setNumeroDocumentoAceptacion(String numeroDocumentoAceptacion) {
        this.numeroDocumentoAceptacion = numeroDocumentoAceptacion;
    }

    public Integer getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(Integer numeroItem) {
        this.numeroItem = numeroItem;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getPosicionOrdenCompra() {
        return posicionOrdenCompra;
    }

    public void setPosicionOrdenCompra(String posicionOrdenCompra) {
        this.posicionOrdenCompra = posicionOrdenCompra;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getCodigoSapBienServicio() {
        return codigoSapBienServicio;
    }

    public void setCodigoSapBienServicio(String codigoSapBienServicio) {
        this.codigoSapBienServicio = codigoSapBienServicio;
    }

    public String getDescripcionBienServicio() {
        return descripcionBienServicio;
    }

    public void setDescripcionBienServicio(String descripcionBienServicio) {
        this.descripcionBienServicio = descripcionBienServicio;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getCantidadAceptadaCliente() {
        return cantidadAceptadaCliente;
    }

    public void setCantidadAceptadaCliente(BigDecimal cantidadAceptadaCliente) {
        this.cantidadAceptadaCliente = cantidadAceptadaCliente;
    }

    public BigDecimal getCantidadPendiente() {
        return cantidadPendiente;
    }

    public void setCantidadPendiente(BigDecimal cantidadPendiente) {
        this.cantidadPendiente = cantidadPendiente;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getValorRecibido() {
        return valorRecibido;
    }

    public void setValorRecibido(BigDecimal valorRecibido) {
        this.valorRecibido = valorRecibido;
    }

    public BigDecimal getValorRecibidoMonedalocal() {
        return valorRecibidoMonedalocal;
    }

    public void setValorRecibidoMonedalocal(BigDecimal valorRecibidoMonedalocal) {
        this.valorRecibidoMonedalocal = valorRecibidoMonedalocal;
    }

    public String getIndicadorImpuesto() {
        return indicadorImpuesto;
    }

    public void setIndicadorImpuesto(String indicadorImpuesto) {
        this.indicadorImpuesto = indicadorImpuesto;
    }

    public String getNumDocApectacionRelacionado() {
        return numDocApectacionRelacionado;
    }

    public void setNumDocApectacionRelacionado(String numDocApectacionRelacionado) {
        this.numDocApectacionRelacionado = numDocApectacionRelacionado;
    }

    public Integer getNumItemRelacionado() {
        return numItemRelacionado;
    }

    public void setNumItemRelacionado(Integer numItemRelacionado) {
        this.numItemRelacionado = numItemRelacionado;
    }


    @Override
    public String toString() {
        return "DocumentoAceptacionDetalle{" +
                "id=" + id +
//                ", documentoAceptacion=" + documentoAceptacion +
                ", idDocumentoAceptacion=" + idDocumentoAceptacion +
                ", estadoDocumentoAceptacion=" + estadoDocumentoAceptacion +
                ", idEstadoDocumentoAceptacionDetalle=" + idEstadoDocumentoAceptacionDetalle +
                ", numeroDocumentoAceptacion='" + numeroDocumentoAceptacion + '\'' +
                ", numeroItem=" + numeroItem +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", posicionOrdenCompra='" + posicionOrdenCompra + '\'' +
                ", movimiento='" + movimiento + '\'' +
                ", codigoSapBienServicio='" + codigoSapBienServicio + '\'' +
                ", descripcionBienServicio='" + descripcionBienServicio + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", cantidadAceptadaCliente=" + cantidadAceptadaCliente +
                ", cantidadPendiente=" + cantidadPendiente +
                ", precioUnitario=" + precioUnitario +
                ", valorRecibido=" + valorRecibido +
                ", valorRecibidoMonedalocal=" + valorRecibidoMonedalocal +
                ", indicadorImpuesto='" + indicadorImpuesto + '\'' +
                ", numDocApectacionRelacionado='" + numDocApectacionRelacionado + '\'' +
                ", numItemRelacionado=" + numItemRelacionado +
                '}';
    }
}