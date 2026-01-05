package com.incloud.hcp.domain.almacen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Access(AccessType.FIELD)
@Table(name="GA_ORDEN_DESPACHO_DETALLE_TEXTO")
public class OrdenDespachoDetalleTexto extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDEN_DESPACHO_DETALLE_TEXTO_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ORDEN_DESPACHO_DETALLE_TEXTO_ID_SEQ", sequenceName = "ORDEN_DESPACHO_DETALLE_TEXTO_ID_SEQ", allocationSize = 1)
	@Column(name="ID_ORDEN_DESPACHO_DETALLE_TEXTO", unique=true, nullable=false)
	private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = OrdenDespachoDetalle.class)
    @JoinColumn(name="ID_ORDEN_DESPACHO_DETALLE", referencedColumnName = "ID_ORDEN_DESPACHO_DETALLE", insertable = false, updatable = false)
    private OrdenDespachoDetalle ordenDespachoDetalle;

	@Column(name="ID_ORDEN_DESPACHO_DETALLE", nullable=false)
	private Integer idOrdenDespachoDetalle;

	@Column(name="NUMERO_ORDEN_COMPRA", nullable=false, length=10)
	private String numeroOrdenCompra;

    @Column(name="POSICION", length=5)
    private String posicion;

    @Column(name="LINEA", length=1000)
    private String linea;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenDespachoDetalle getOrdenDespachoDetalle() {
        return ordenDespachoDetalle;
    }

    public void setOrdenDespachoDetalle(OrdenDespachoDetalle ordenDespachoDetalle) {
        this.ordenDespachoDetalle = ordenDespachoDetalle;
    }

    public Integer getIdOrdenDespachoDetalle() {
        return idOrdenDespachoDetalle;
    }

    public void setIdOrdenDespachoDetalle(Integer idOrdenDespachoDetalle) {
        this.idOrdenDespachoDetalle = idOrdenDespachoDetalle;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }


    @Override
    public String toString() {
        return "OrdenDespachoDetalleTexto{" +
                "id=" + id +
                ", ordenDespachoDetalle=" + ordenDespachoDetalle +
                ", idOrdenDespachoDetalle=" + idOrdenDespachoDetalle +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", posicion='" + posicion + '\'' +
                ", linea='" + linea + '\'' +
                '}';
    }
}