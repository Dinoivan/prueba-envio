package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;

import java.io.Serializable;


/**
 * The persistent class for the banco database table.
 * 
 */
@Entity
@Table(name="banco")
public class Banco extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_banco", unique=true, nullable=false)
	@GeneratedValue(generator = "banco_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "banco_id_seq", sequenceName = "banco_id_seq", allocationSize = 1)
	private Integer idBanco;

	@Column(name="clave_banco", unique = true, nullable=false, length=15)
	private String claveBanco;

	@Column(unique = true, nullable=false, length=80)
	private String descripcion;

	@Digits(integer = 10, fraction = 0)
	@Column(name = "extension_cta", precision = 10)
	private Integer extensionCta;

	@Digits(integer = 10, fraction = 0)
	@Column(name = "extension_cci", precision = 10)
	private Integer extensionCci;

	@Column(name = "formato_cta",length=100)
	private String formatoCta;

	@Column(name = "ejemplo_formato_cta",length=100)
	private String ejemploFormatoCta;



	public Banco() {
	}

	public Integer getIdBanco() {
		return this.idBanco;
	}

	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}

	public String getClaveBanco() {
		return this.claveBanco;
	}

	public void setClaveBanco(String claveBanco) {
		this.claveBanco = claveBanco;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	// -- [extensionCta] ------------------------


	public Integer getExtensionCta() {
		return extensionCta;
	}

	public void setExtensionCta(Integer extensionCta) {
		this.extensionCta = extensionCta;
	}

	public Banco extensionCta(Integer extensionCta) {
		setExtensionCta(extensionCta);
		return this;
	}
	// -- [extensionCci] ------------------------

	public Integer getExtensionCci() {
		return extensionCci;
	}

	public void setExtensionCci(Integer extensionCci) {
		this.extensionCci = extensionCci;
	}

	public Banco extensionCci(Integer extensionCci) {
		setExtensionCci(extensionCci);
		return this;
	}

	public String getFormatoCta() {
		return formatoCta;
	}

	public void setFormatoCta(String formatoCta) {
		this.formatoCta = formatoCta;
	}

	public String getEjemploFormatoCta() {
		return ejemploFormatoCta;
	}

	public void setEjemploFormatoCta(String ejemploFormatoCta) {
		this.ejemploFormatoCta = ejemploFormatoCta;
	}

	@Override
	public String toString() {
		return "Banco{" +
				"idBanco=" + idBanco +
				", claveBanco='" + claveBanco + '\'' +
				", descripcion='" + descripcion + '\'' +
				", extensionCta=" + extensionCta +
				", extensionCci=" + extensionCci +
				", formatoCta='" + formatoCta + '\'' +
				", ejemploFormatoCta='" + ejemploFormatoCta + '\'' +
				'}';
	}
}