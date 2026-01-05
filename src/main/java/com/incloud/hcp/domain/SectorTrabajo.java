package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the sector_trabajo database table.
 * 
 */
@Entity
@Table(name="sector_trabajo")
public class SectorTrabajo extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_sector_trabajo", unique=true, nullable=false)
	@GeneratedValue(generator = "sector_trabajo_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sector_trabajo_id_seq", sequenceName = "sector_trabajo_id_seq", allocationSize = 1)
	private Integer idSectorTrabajo;

	@Column(nullable=false, length=30)
	private String descripcion;

	public SectorTrabajo() {
	}

	public Integer getIdSectorTrabajo() {
		return this.idSectorTrabajo;
	}

	public void setIdSectorTrabajo(Integer idSectorTrabajo) {
		this.idSectorTrabajo = idSectorTrabajo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@Override
	public String toString() {
		return "SectorTrabajo{" +
				"idSectorTrabajo=" + idSectorTrabajo +
				", descripcion='" + descripcion + '\'' +
				'}';
	}
}