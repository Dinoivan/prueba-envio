package com.incloud.hcp.domain.almacen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Access(AccessType.FIELD)
@Table(name="GA_ORDEN_DESPACHO_TEXTO_CABECERA")
public class OrdenDespachoTextoCabecera extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDEN_DESPACHO_TEXTO_CABECERA_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ORDEN_DESPACHO_TEXTO_CABECERA_ID_SEQ", sequenceName = "ORDEN_DESPACHO_TEXTO_CABECERA_ID_SEQ", allocationSize = 1)
	@Column(name="ID_ORDEN_DESPACHO_TEXTO_CABECERA", unique=true, nullable=false)
	private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = OrdenDespacho.class)
    @JoinColumn(name="ID_ORDEN_DESPACHO", referencedColumnName = "ID_ORDEN_DESPACHO", insertable = false, updatable = false)
    private OrdenDespacho ordenDespacho;

	@Column(name="ID_ORDEN_DESPACHO", nullable=false)
	private Integer idOrdenDespacho;

	@Column(name="NUMERO_ORDEN_COMPRA", nullable=false, length=10)
	private String numeroOrdenCompra;

    @Column(name="LINEA", length=1000)
    private String linea;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenDespacho getOrdenDespacho() {
        return ordenDespacho;
    }

    public void setOrdenDespacho(OrdenDespacho ordenDespacho) {
        this.ordenDespacho = ordenDespacho;
    }

    public Integer getIdOrdenDespacho() {
        return idOrdenDespacho;
    }

    public void setIdOrdenDespacho(Integer idOrdenDespacho) {
        this.idOrdenDespacho = idOrdenDespacho;
    }

    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(String numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    @Override
    public String toString() {
        return "OrdenDespachoTextoCabecera{" +
                "id=" + id +
                ", ordenDespacho=" + ordenDespacho +
                ", idOrdenDespacho=" + idOrdenDespacho +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", linea='" + linea + '\'' +
                '}';
    }
}