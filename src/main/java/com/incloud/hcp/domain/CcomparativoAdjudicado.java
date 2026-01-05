package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The persistent class for the ccomparativo_adjudicado database table.
 * 
 */
@Entity
@Table(name="ccomparativo_adjudicado")
public class CcomparativoAdjudicado extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_ccomparativo_adjudicado", unique=true, nullable=false)
	@GeneratedValue(generator = "ccomparativo_adjudicado_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ccomparativo_adjudicado_id_seq", sequenceName = "ccomparativo_adjudicado_id_seq", allocationSize = 1)
	private Integer idCcomparativoAdjudicado;

	@Column(name="precio_unitario", nullable=false, precision=15, scale=3)
	private BigDecimal precioUnitario;

	@Column(name="cantidad_real", nullable=false, precision=15, scale=2)
	private BigDecimal cantidadReal;

	@Column(name="precio_unitario_real",  precision=15, scale=3)
	private BigDecimal precioUnitarioReal;


	//uni-directional many-to-one association to CotizacionDetalle
	@ManyToOne
	@JoinColumn(name="id_cotizacion_detalle", nullable=false)
	private CotizacionDetalle cotizacionDetalle;

	@ManyToOne
	@JoinColumn(name="id_ccomparativo_proveedor", nullable=false)
	private CcomparativoProveedor ccomparativoProveedor;



	public CcomparativoAdjudicado() {
	}


	public Integer getIdCcomparativoAdjudicado() {
		return idCcomparativoAdjudicado;
	}

	public void setIdCcomparativoAdjudicado(Integer idCcomparativoAdjudicado) {
		this.idCcomparativoAdjudicado = idCcomparativoAdjudicado;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getCantidadReal() {
		return cantidadReal;
	}

	public void setCantidadReal(BigDecimal cantidadReal) {
		this.cantidadReal = cantidadReal;
	}

	public CotizacionDetalle getCotizacionDetalle() {
		return cotizacionDetalle;
	}

	public void setCotizacionDetalle(CotizacionDetalle cotizacionDetalle) {
		this.cotizacionDetalle = cotizacionDetalle;
	}

	public CcomparativoProveedor getCcomparativoProveedor() {
		return ccomparativoProveedor;
	}

	public void setCcomparativoProveedor(CcomparativoProveedor ccomparativoProveedor) {
		this.ccomparativoProveedor = ccomparativoProveedor;
	}

	public BigDecimal getPrecioUnitarioReal() {
		return precioUnitarioReal;
	}

	public void setPrecioUnitarioReal(BigDecimal precioUnitarioReal) {
		this.precioUnitarioReal = precioUnitarioReal;
	}

	@Override
	public String toString() {
		return "CcomparativoAdjudicado{" +
				"idCcomparativoAdjudicado=" + idCcomparativoAdjudicado +
				", precioUnitario=" + precioUnitario +
				", cantidadReal=" + cantidadReal +
				", precioUnitarioReal=" + precioUnitarioReal +
				", cotizacionDetalle=" + cotizacionDetalle +
				", ccomparativoProveedor=" + ccomparativoProveedor +
				'}';
	}


}