package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the proveedor_producto database table.
 * 
 */
@Entity
@Table(name="proveedor_producto")
public class ProveedorProducto extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_proveedor_producto", unique=true, nullable=false)
	@GeneratedValue(generator = "proveedor_producto_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "proveedor_producto_id_seq", sequenceName = "proveedor_producto_id_seq", allocationSize = 1)
	private Integer idProveedorProducto;

	@Column(name="descripcion_adicional", length=1000)
	private String descripcionAdicional;

	@Column(length=100)
	private String marca;

	@Column(length=100)
	private String producto;

	//uni-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false)
	private Proveedor proveedor;

	public ProveedorProducto() {
	}

	public Integer getIdProveedorProducto() {
		return this.idProveedorProducto;
	}

	public void setIdProveedorProducto(Integer idProveedorProducto) {
		this.idProveedorProducto = idProveedorProducto;
	}

	public String getDescripcionAdicional() {
		return this.descripcionAdicional;
	}

	public void setDescripcionAdicional(String descripcionAdicional) {
		this.descripcionAdicional = descripcionAdicional;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getProducto() {
		return this.producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	@Override
	public String toString() {
		return "ProveedorProducto{" +
				"idProveedorProducto=" + idProveedorProducto +
				", descripcionAdicional='" + descripcionAdicional + '\'' +
				", marca='" + marca + '\'' +
				", producto='" + producto + '\'' +
				", proveedor=" + proveedor +
				'}';
	}
}