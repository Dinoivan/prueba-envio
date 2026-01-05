package com.incloud.hcp.domain.almacen;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the lugar_entrega database table.
 * 
 */
@Entity
@Table(name="LUGAR_ENTREGA")
public class LugarEntrega extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_lugar_entrega", unique=true, nullable=false)
	@GeneratedValue(generator = "lugar_entrega_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "lugar_entrega_id_seq", sequenceName = "lugar_entrega_id_seq", allocationSize = 1)
	private Integer idLugarEntrega;

	@Column(name="lugar_entrega", nullable=false, length=12)
	private String lugarEntrega;

	@Column(name="centro", length=4)
	private String centro;

	@Column(name="numero_cuenta", length=10)
	private String numeroCuenta;


	@Column(name = "calle", length = 60)
	private String calle;

	@Column(name = "calle_cuatro", length = 40)
	private String calleCuatro;

	@Column(name = "poblacion", length = 40)
	private String poblacion;

	@Column(name = "distrito", length = 40)
	private String distrito;

	@Column(name = "zregion", length = 3)
	private String zRegion;

	public LugarEntrega() {
	}

	public Integer getIdLugarEntrega() {
		return idLugarEntrega;
	}

	public void setIdLugarEntrega(Integer idLugarEntrega) {
		this.idLugarEntrega = idLugarEntrega;
	}

	public String getLugarEntrega() {
		return lugarEntrega;
	}

	public void setLugarEntrega(String lugarEntrega) {
		this.lugarEntrega = lugarEntrega;
	}

	public String getCentro() {
		return centro;
	}

	public void setCentro(String centro) {
		this.centro = centro;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getCalleCuatro() {
		return calleCuatro;
	}

	public void setCalleCuatro(String calleCuatro) {
		this.calleCuatro = calleCuatro;
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

	public String getzRegion() {
		return zRegion;
	}

	public void setzRegion(String zRegion) {
		this.zRegion = zRegion;
	}

	@Override
	public String toString() {
		return "LugarEntrega{" +
				"idLugarEntrega=" + idLugarEntrega +
				", lugarEntrega='" + lugarEntrega + '\'' +
				", centro='" + centro + '\'' +
				", numeroCuenta='" + numeroCuenta + '\'' +
				", calle='" + calle + '\'' +
				", calleCuatro='" + calleCuatro + '\'' +
				", poblacion='" + poblacion + '\'' +
				", distrito='" + distrito + '\'' +
				", zRegion='" + zRegion + '\'' +
				'}';
	}
}