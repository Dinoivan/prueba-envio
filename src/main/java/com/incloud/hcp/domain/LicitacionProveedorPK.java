package com.incloud.hcp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

/**
 * The primary key class for the licitacion_proveedor database table.
 * 
 */
@Embeddable
public class LicitacionProveedorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_proveedor")
	private Integer idProveedor;

	@Column(name="id_licitacion")
	private Integer idLicitacion;

	public LicitacionProveedorPK() {
	}

    public LicitacionProveedorPK(Integer idProveedor, Integer idLicitacion) {
        this.idProveedor = idProveedor;
        this.idLicitacion = idLicitacion;
    }

    public Integer getIdProveedor() {
		return this.idProveedor;
	}
	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}
	public Integer getIdLicitacion() {
		return this.idLicitacion;
	}
	public void setIdLicitacion(Integer idLicitacion) {
		this.idLicitacion = idLicitacion;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LicitacionProveedorPK)) {
			return false;
		}
		LicitacionProveedorPK castOther = (LicitacionProveedorPK)other;
		return 
			this.idProveedor.equals(castOther.idProveedor)
			&& this.idLicitacion.equals(castOther.idLicitacion);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idProveedor.hashCode();
		hash = hash * prime + this.idLicitacion.hashCode();
		
		return hash;
	}

	@Override
	public String toString() {
		return "LicitacionProveedorPK{" +
				"idProveedor=" + idProveedor +
				", idLicitacion=" + idLicitacion +
				'}';
	}
}