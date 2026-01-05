package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the moneda database table.
 * 
 */
@Entity
@Table(name="moneda")
public class Moneda extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_moneda", unique=true, nullable=false)
	@GeneratedValue(generator = "moneda_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "moneda_id_seq", sequenceName = "moneda_id_seq", allocationSize = 1)
	private Integer idMoneda;

	@Column(name="codigo_moneda", nullable=false, length=5)
	private String codigoMoneda;

	@Column(nullable=false, length=10)
	private String sigla;

	@Column(name="texto_breve", nullable=false, length=50)
	private String textoBreve;

	@Column(name="texto_explicativo", nullable=false, length=50)
	private String textoExplicativo;


	@Column(name = "tasa_moneda", precision = 5, scale = 2)
	public BigDecimal tasaMoneda;


	public Moneda() {
	}

	public Integer getIdMoneda() {
		return this.idMoneda;
	}

	public void setIdMoneda(Integer idMoneda) {
		this.idMoneda = idMoneda;
	}

	public String getCodigoMoneda() {
		return this.codigoMoneda;
	}

	public void setCodigoMoneda(String codigoMoneda) {
		this.codigoMoneda = codigoMoneda;
	}

	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getTextoBreve() {
		return this.textoBreve;
	}

	public void setTextoBreve(String textoBreve) {
		this.textoBreve = textoBreve;
	}

	public String getTextoExplicativo() {
		return this.textoExplicativo;
	}

	public void setTextoExplicativo(String textoExplicativo) {
		this.textoExplicativo = textoExplicativo;
	}

	public BigDecimal getTasaMoneda() {
		return tasaMoneda;
	}

	public void setTasaMoneda(BigDecimal tasaMoneda) {
		this.tasaMoneda = tasaMoneda;
	}

	@Override
	public String toString() {
		return "Moneda{" +
				"idMoneda=" + idMoneda +
				", codigoMoneda='" + codigoMoneda + '\'' +
				", sigla='" + sigla + '\'' +
				", textoBreve='" + textoBreve + '\'' +
				", textoExplicativo='" + textoExplicativo + '\'' +
				", tasaMoneda=" + tasaMoneda +
				'}';
	}


}