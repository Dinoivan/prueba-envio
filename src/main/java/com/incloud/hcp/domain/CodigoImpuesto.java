package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="CODIGO_IMPUESTO")
public class CodigoImpuesto extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO", unique=true, nullable=false, length=2)
	private String codigo;

	@Basic
	@Column(name="DESCRIPCION", nullable=false, length=50)
	private String descripcion;


	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@Override
	public String toString() {
		return "CodigoImpuesto{" +
				"codigo='" + codigo + '\'' +
				", descripcion='" + descripcion + '\'' +
				'}';
	}
}