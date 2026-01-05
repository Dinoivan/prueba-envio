package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the tipo_proveedor database table.
 * 
 */
@Entity
@Table(name="tipo_proveedor")
public class TipoProveedor extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_proveedor", unique=true, nullable=false)
	@GeneratedValue(generator = "tipo_proveedor_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "tipo_proveedor_id_seq", sequenceName = "tipo_proveedor_id_seq", allocationSize = 1)
	private Integer idTipoProveedor;

	@Column(name="codigo_sap", nullable=false, length=10)
	private String codigoSap;

	@Column(nullable=false, length=30)
	private String descripcion;

	public TipoProveedor() {
	}

	public Integer getIdTipoProveedor() {
		return this.idTipoProveedor;
	}

	public void setIdTipoProveedor(Integer idTipoProveedor) {
		this.idTipoProveedor = idTipoProveedor;
	}

	public String getCodigoSap() {
		return this.codigoSap;
	}

	public void setCodigoSap(String codigoSap) {
		this.codigoSap = codigoSap;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "TipoProveedor{" +
				"idTipoProveedor=" + idTipoProveedor +
				", codigoSap='" + codigoSap + '\'' +
				", descripcion='" + descripcion + '\'' +
				'}';
	}
}