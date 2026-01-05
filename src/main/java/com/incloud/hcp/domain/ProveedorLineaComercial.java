package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the proveedor_linea_comercial database table.
 * 
 */
@Entity
@Table(name="proveedor_linea_comercial")
public class ProveedorLineaComercial extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_proveedor_linea_comercial", unique=true, nullable=false)
	@GeneratedValue(generator = "proveedor_linea_comercial_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "proveedor_linea_comercial_id_seq", sequenceName = "proveedor_linea_comercial_id_seq", allocationSize = 1)
	private Integer idProveedorLineaComercial;

	@Column(name="otros_linea_comercial", length=1000)
	private String otrosLineaComercial;

	//uni-directional many-to-one association to LineaComercial
	@ManyToOne
	@JoinColumn(name="id_linea_comercial", nullable=false)
	private LineaComercial lineaComercial;

	//uni-directional many-to-one association to LineaComercial
	@ManyToOne
	@JoinColumn(name="id_familia", nullable=true)
	private LineaComercial familia;

	//uni-directional many-to-one association to LineaComercial
	@ManyToOne
	@JoinColumn(name="id_subfamilia", nullable=true)
	private LineaComercial subFamilia;

	//uni-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false)
	private Proveedor proveedor;

	public ProveedorLineaComercial() {
	}

	public Integer getIdProveedorLineaComercial() {
		return this.idProveedorLineaComercial;
	}

	public void setIdProveedorLineaComercial(Integer idProveedorLineaComercial) {
		this.idProveedorLineaComercial = idProveedorLineaComercial;
	}

	public String getOtrosLineaComercial() {
		return this.otrosLineaComercial;
	}

	public void setOtrosLineaComercial(String otrosLineaComercial) {
		this.otrosLineaComercial = otrosLineaComercial;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public LineaComercial getLineaComercial() {
		return lineaComercial;
	}

	public void setLineaComercial(LineaComercial lineaComercial) {
		this.lineaComercial = lineaComercial;
	}

	public LineaComercial getFamilia() {
		return familia;
	}

	public void setFamilia(LineaComercial familia) {
		this.familia = familia;
	}

	public LineaComercial getSubFamilia() {
		return subFamilia;
	}

	public void setSubFamilia(LineaComercial subFamilia) {
		this.subFamilia = subFamilia;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	@Override
	public String toString() {
		return "ProveedorLineaComercial{" +
				"idProveedorLineaComercial=" + idProveedorLineaComercial +
				", otrosLineaComercial='" + otrosLineaComercial + '\'' +
				", lineaComercial=" + lineaComercial +
				", familia=" + familia +
				", subFamilia=" + subFamilia +
				", proveedor=" + proveedor +
				'}';
	}
}