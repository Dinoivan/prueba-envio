package com.incloud.hcp.domain.balanza;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.incloud.hcp.domain.balanza.convertir.BooleanConverters;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_DETALLE_TICKET")
public class DetalleTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "DETALLE_TICKET_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "DETALLE_TICKET_ID_SEQ", sequenceName = "DETALLE_TICKET_ID_SEQ", allocationSize = 1)
    @Column(name="ID_DETALLE_TICKET", unique=true, nullable=false)
    private Integer id;

    @Column(name="SUBTICKET")
    private String subticket;

    @Column(name="PESO_INICIAL")
    private Double peso_inicial;

    @Column(name="PESO_FINAL")
    private Double peso_final;

    @Column(name="PESO_NETO")
    private Double peso_neto;

    @Column(name="POSICION")
    private String posicion;

    @Column(name="POSICION_DOCUMENTO")
    private Integer posicionDocumento;


    @Column(name="TIPO_PESAJE")
    private String tipoPesaje;

    @Column(name="FECHA_REGISTRO")
    private Timestamp fechaCreacion;

    @Column(name="FECHA_MODIFICACION")
    private Timestamp fechaModificacion;

    @Column(name="TIPO_PRODUCTO")
    private String tipoProducto;

    @Column(name="ESTADO_DISPO_ELIMI")
    @Convert(converter= BooleanConverters.CharacterConverter.class )
    private Boolean estadoDispoElimi;

    @JsonIgnoreProperties(value={"detalleTicketList", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TicketPesaje.class)
    @JoinColumn(name="ID_TICKET_PESAJE", referencedColumnName = "ID_TICKET_PESAJE")
    @JsonBackReference
    private TicketPesaje ticketPesaje;

    @Column(name = "DOC_MATERIAL")
    private String docMaterial;

    @Column(name = "EJERCICIO")
    private String ejercicio;

    @Column(name = "NUMERO_PEDIDO")
    private String numPedido;

    @Column(name = "MATERIAL")
    private String material;

    @Column(name = "LOTE")
    private String lote;

    @Column(name = "UNIDAD_MEDIDA")
    private String unidadMedida;

    @Column(name = "DOC_TRASLADO")
    private String documentoTraslado;

    @Column(name = "POS_DOC_MATERIAL")
    private String posDocMaterial;

    @Column(name="USUARIO_CREADOR")
    private String usuarioCreador;

    @Column(name="USUARIO_MODIFICADOR")
    private String usuarioModificador;

    @Column(name = "TIENE_GUIA")
    private String tieneGuia;

    @Column(name="CODIGO_ALMACEN" )
    private String codigoAlmacen;
    @NotNull
    @OneToOne
    @JoinColumn(name = "ID_ESTADO")
    private Estado estado;

    @Column(name="FECHA_EMISION_PDF")
    private Timestamp fechaEmisionPdf;

    @Column(name="DAM")
    private String dam;

    @Column(name="UM_SAP")
    private String unidadMedidaSap;
    @Column(name="PESO_SAP")
    private Double pesoSap;

    @Column(name="CANTIDAD")
    private Double cantidad;

    @Column(name="ISPOSICION")
    private String isPosicion;


    public DetalleTicket() {
    }

    public String getPosDocMaterial() {
        return posDocMaterial;
    }

    public void setPosDocMaterial(String posDocMaterial) {
        this.posDocMaterial = posDocMaterial;
    }

    public String getDocumentoTraslado() {
        return documentoTraslado;
    }

    public void setDocumentoTraslado(String documentoTraslado) {
        this.documentoTraslado = documentoTraslado;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
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

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }

    public Integer getId() {
        return id;
    }

    public String getDocMaterial() {
        return docMaterial;
    }

    public void setDocMaterial(String docMaterial) {
        this.docMaterial = docMaterial;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPeso_inicial() {
        return peso_inicial;
    }

    public void setPeso_inicial(Double peso_inicial) {
        this.peso_inicial = peso_inicial;
    }

    public String getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(String codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public Double getPeso_neto() {
        return peso_neto;
    }

    public void setPeso_neto(Double peso_neto) {
        this.peso_neto = peso_neto;
    }


    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getTipoPesaje() {
        return tipoPesaje;
    }

    public void setTipoPesaje(String tipoPesaje) {
        this.tipoPesaje = tipoPesaje;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Boolean getEstadoDispoElimi() {
        return estadoDispoElimi;
    }

    public void setEstadoDispoElimi(Boolean estadoDispoElimi) {
        this.estadoDispoElimi = estadoDispoElimi;
    }

    public TicketPesaje getTicketPesaje() {
        return ticketPesaje;
    }

    public void setTicketPesaje(TicketPesaje ticketPesaje) {
        this.ticketPesaje = ticketPesaje;
    }
    public String getSubticket() {
        return subticket;
    }

    public void setSubticket(String subticket) {
        this.subticket = subticket;
    }

    public Double getPeso_final() {
        return peso_final;
    }

    public void setPeso_final(Double peso_final) {
        this.peso_final = peso_final;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getUsuarioModificador() {
        return usuarioModificador;
    }

    public void setUsuarioModificador(String usuarioModificador) {
        this.usuarioModificador = usuarioModificador;
    }

    public String getTieneGuia() {
        return tieneGuia;
    }

    public void setTieneGuia(String tieneGuia) {
        this.tieneGuia = tieneGuia;
    }

    public Timestamp getFechaEmisionPdf() {
        return fechaEmisionPdf;
    }

    public void setFechaEmisionPdf(Timestamp fechaEmisionPdf) {
        this.fechaEmisionPdf = fechaEmisionPdf;
    }

    public String getDam() {
        return dam;
    }

    public void setDam(String dam) {
        this.dam = dam;
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

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getIsPosicion() {
        return isPosicion;
    }

    public void setIsPosicion(String isPosicion) {
        this.isPosicion = isPosicion;
    }

    public Integer getPosicionDocumento() {
        return posicionDocumento;
    }

    public void setPosicionDocumento(Integer posicionDocumento) {
        this.posicionDocumento = posicionDocumento;
    }
//    @Override
//    public String toString() {
//        return "DetalleTicket{" +
//                "id=" + id +
//                ", peso_neto=" + peso_neto +
//                ", posicion=" + posicion +
//                ", tipoPesaje='" + tipoPesaje + '\'' +
//                ", fechaCreacion=" + fechaCreacion +
//                ", fechaModificacion=" + fechaModificacion +
//                ", tipoProducto='" + tipoProducto + '\'' +
//                ", estadoDispoElimi=" + estadoDispoElimi +
//                ", ticketPesaje=" + ticketPesaje +
//                ", docMaterial='" + docMaterial + '\'' +
//                ", ejercicio='" + ejercicio + '\'' +
//                ", numPedido='" + numPedido + '\'' +
//                ", material='" + material + '\'' +
//                ", lote='" + lote + '\'' +
//                ", unidadMedida='" + unidadMedida + '\'' +
//                ", documentoTraslado='" + documentoTraslado + '\'' +
//                ", posDocMaterial='" + posDocMaterial + '\'' +
//                ", estado=" + estado +
//                '}';
//    }
}
