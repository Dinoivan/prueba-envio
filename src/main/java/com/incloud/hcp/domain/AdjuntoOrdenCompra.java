package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;


/**
 * The persistent class for the proveedor_catalogo database table. prueba
 * 
 */
@Entity
@Table(name="adjunto_orden_compra")
public class AdjuntoOrdenCompra extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_adjunto", unique=true, nullable=false, length=20)
	private String idAdjunto;



	@Column(name="archivo_nombre", nullable=true, length=100)
	private String archivoNombre;

	@Column(name="archivo_tipo", nullable=true, length=100)
	private String archivoTipo;


	@Column(name="ordencompra", nullable=true, length=20)
	private String ordencompra;

	@Column(name="archivo", nullable=true)
	private String archivo;

	public AdjuntoOrdenCompra() {
	}

	public String getIdAdjunto() {
		return idAdjunto;
	}

	public void setIdAdjunto(String idAdjunto) {
		this.idAdjunto = idAdjunto;
	}

	public String getArchivoNombre() {
		return archivoNombre;
	}

	public void setArchivoNombre(String archivoNombre) {
		this.archivoNombre = archivoNombre;
	}

	public String getArchivoTipo() {
		return archivoTipo;
	}

	public void setArchivoTipo(String archivoTipo) {
		this.archivoTipo = archivoTipo;
	}


	public String getOrdencompra() {
		return ordencompra;
	}

	public void setOrdencompra(String ordencompra) {
		this.ordencompra = ordencompra;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	@Override
	public String toString() {
		return "AdjuntoOrdenCompra{" +
				"idAdjunto='" + idAdjunto + '\'' +
				", archivoNombre='" + archivoNombre + '\'' +
				", archivoTipo='" + archivoTipo + '\'' +
				", ordencompra='" + ordencompra + '\'' +
				", archivo='" + archivo + '\'' +
				'}';
	}
}