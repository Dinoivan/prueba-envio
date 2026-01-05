package com.incloud.hcp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Access(AccessType.FIELD)
@Table(name="ORDEN_COMPRA_TEXTO_CABECERA")
public class OrdenCompraTextoCabecera extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDEN_COMPRA_TEXTO_CABECERA_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ORDEN_COMPRA_TEXTO_CABECERA_ID_SEQ", sequenceName = "ORDEN_COMPRA_TEXTO_CABECERA_ID_SEQ", allocationSize = 1)
	@Column(name="ID_ORDEN_COMPRA_TEXTO_CABECERA", unique=true, nullable=false)
	private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = OrdenCompra.class)
    @JoinColumn(name="ID_ORDEN_COMPRA", referencedColumnName = "ID_ORDEN_COMPRA", insertable = false, updatable = false)
    private OrdenCompra ordenCompra;

	@Column(name="ID_ORDEN_COMPRA", nullable=false)
	private Integer idOrdenCompra;

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

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
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
        return "OrdenCompraTextoCabecera{" +
                "id=" + id +
//                ", ordenCompra=" + ordenCompra +
                ", idOrdenCompra=" + idOrdenCompra +
                ", numeroOrdenCompra='" + numeroOrdenCompra + '\'' +
                ", linea='" + linea + '\'' +
                '}';
    }
}