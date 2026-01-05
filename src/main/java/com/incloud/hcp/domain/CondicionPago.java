package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the condicion_pago database table.
 * 
 */
@Entity
@Table(name="condicion_pago")
public class CondicionPago extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_condicion_pago", unique=true, nullable=false)
	@GeneratedValue(generator = "condicion_pago_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "condicion_pago_id_seq", sequenceName = "condicion_pago_id_seq", allocationSize = 1)
	private Integer idCondicionPago;

	@Column(name="codigo_sap", nullable=false, length=4)
	private String codigoSap;

	@Column(nullable=false, length=50)
	private String descripcion;

	@Column(name="dias_pago", precision = 10)
	private Integer diasPago;

	@Column(name="factor_denominador_tasa", precision = 10)
	private Integer factorDenominadorTasa;

	public CondicionPago() {
	}

	public Integer getIdCondicionPago() {
		return this.idCondicionPago;
	}

	public void setIdCondicionPago(Integer idCondicionPago) {
		this.idCondicionPago = idCondicionPago;
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

	public Integer getDiasPago() {
		return diasPago;
	}

	public void setDiasPago(Integer diasPago) {
		this.diasPago = diasPago;
	}

	public Integer getFactorDenominadorTasa() {
		return factorDenominadorTasa;
	}

	public void setFactorDenominadorTasa(Integer factorDenominadorTasa) {
		this.factorDenominadorTasa = factorDenominadorTasa;
	}

	@Override
	public String toString() {
		return "CondicionPago{" +
				"idCondicionPago=" + idCondicionPago +
				", codigoSap='" + codigoSap + '\'' +
				", descripcion='" + descripcion + '\'' +
				", diasPago=" + diasPago +
				", factorDenominadorTasa=" + factorDenominadorTasa +
				'}';
	}
}