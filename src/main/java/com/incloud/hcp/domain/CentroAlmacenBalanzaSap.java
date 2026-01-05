package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the banco database table.
 * 
 */
@Entity
@Table(name="centro_almacen_balanza_sap")
public class CentroAlmacenBalanzaSap extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(generator = "centro_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "centro_id_seq", sequenceName = "centro_id_seq", allocationSize = 1)
	private Integer id;
	@Column(name="centro", nullable=false, length=255)
	private String centro;
	@Column(name="poblacion", nullable=false, length=255)
	private String poblacion;
	@Column(name="distrito", nullable=false, length=255)
	private String distrito;
	@Column(name="direccion", nullable=false, length=255)
	private String direccion;
	@Column(name="direccion2", nullable=false, length=255)
	private String direccion2;
	@Column(name="direccion3", nullable=false, length=255)
	private String direccion3;
	@Column(name="codigo_almacen", nullable=false, length=255)
	private String codigoAlmacen;
	@Column(name="descripcion_almacen", nullable=false, length=255)
	private String descripcionAlmacen;
	@Column(name="direccion_centro", nullable=false, length=255)
	private String direccionCentro;
	@Column(name="nombre", nullable=true, length=255)
	private String nombre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccion2() {
		return direccion2;
	}

	public void setDireccion2(String direccion2) {
		this.direccion2 = direccion2;
	}

	public String getDireccion3() {
		return direccion3;
	}

	public void setDireccion3(String direccion3) {
		this.direccion3 = direccion3;
	}

	public String getCodigoAlmacen() {
		return codigoAlmacen;
	}

	public void setCodigoAlmacen(String codigoAlmacen) {
		this.codigoAlmacen = codigoAlmacen;
	}

	public String getDescripcionAlmacen() {
		return descripcionAlmacen;
	}

	public void setDescripcionAlmacen(String descripcionAlmacen) {
		this.descripcionAlmacen = descripcionAlmacen;
	}

	public String getDireccionCentro() {
		return direccionCentro;
	}

	public void setDireccionCentro(String direccionCentro) {
		this.direccionCentro = direccionCentro;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}