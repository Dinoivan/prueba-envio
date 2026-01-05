package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the proveedor_tipo_retencion database table.
 * 
 */
@Entity
@Table(name="proveedor_tipo_retencion")
public class ProveedorTipoRetencion extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_proveedor_tipo_retencion", unique=true, nullable=false)
	@GeneratedValue(generator = "proveedor_tipo_retencion_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "proveedor_tipo_retencion_id_seq", sequenceName = "proveedor_tipo_retencion_id_seq", allocationSize = 1)
	private Integer idProveedorTipoRetencion;

	@Column(name="tipo_retencion", nullable=false, length=2)
	private String tipoRetencion;

	//uni-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false)
	private Proveedor proveedor;

	public ProveedorTipoRetencion() {
	}

	public Integer getIdProveedorTipoRetencion() {
		return this.idProveedorTipoRetencion;
	}

	public void setIdProveedorTipoRetencion(Integer idProveedorTipoRetencion) {
		this.idProveedorTipoRetencion = idProveedorTipoRetencion;
	}

	public String getTipoRetencion() {
		return this.tipoRetencion;
	}

	public void setTipoRetencion(String tipoRetencion) {
		this.tipoRetencion = tipoRetencion;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	@Override
	public String toString() {
		return "ProveedorTipoRetencion{" +
				"idProveedorTipoRetencion=" + idProveedorTipoRetencion +
				", tipoRetencion='" + tipoRetencion + '\'' +
				", proveedor=" + proveedor +
				'}';
	}
}