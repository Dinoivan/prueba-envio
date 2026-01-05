package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the licitacion_grupo_condicion_lic database table.
 * 
 */
@Entity
@Table(name="licitacion_grupo_condicion_lic")
public class LicitacionGrupoCondicionLic extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_licitacion_grupo_condicion", unique=true, nullable=false)
	@GeneratedValue(generator = "licitacion_grupo_condicion_lic_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "licitacion_grupo_condicion_lic_id_seq", sequenceName = "licitacion_grupo_condicion_lic_id_seq", allocationSize = 1)
	private Integer idLicitacionGrupoCondicion;

	@Column(name="id_condicion", nullable=false)
	private Integer idCondicion;

	@Column(name="ind_bloqueado_proveedor", length=1)
	private String indBloqueadoProveedor;

	@Column(name="peso", nullable=false, precision=5, scale=2)
	private BigDecimal peso;

	@Column(nullable=false, length=300)
	private String pregunta;

	@Column(name="tipo_campo", nullable=false, length=1)
	private String tipoCampo;

	//uni-directional many-to-one association to Licitacion
	@ManyToOne
	@JoinColumn(name="id_licitacion", nullable=false)
	private Licitacion licitacion;

	//uni-directional many-to-one association to TipoLicitacion
	@ManyToOne
	@JoinColumn(name="id_tipo_licitacion", nullable=false)
	private TipoLicitacion tipoLicitacion1;

	//uni-directional many-to-one association to TipoLicitacion
	@ManyToOne
	@JoinColumn(name="id_tipo_cuestionario", nullable=false)
	private TipoLicitacion tipoLicitacion2;


	public LicitacionGrupoCondicionLic() {
	}

	public LicitacionGrupoCondicionLic(Integer idCondicion, String indBloqueadoProveedor, BigDecimal peso, String pregunta, String tipoCampo, Licitacion licitacion, TipoLicitacion tipoLicitacion1, TipoLicitacion tipoLicitacion2) {
		this.idCondicion = idCondicion;
		this.indBloqueadoProveedor = indBloqueadoProveedor;
		this.peso = peso;
		this.pregunta = pregunta;
		this.tipoCampo = tipoCampo;
		this.licitacion = licitacion;
		this.tipoLicitacion1 = tipoLicitacion1;
		this.tipoLicitacion2 = tipoLicitacion2;
	}



	public Integer getIdLicitacionGrupoCondicion() {
		return this.idLicitacionGrupoCondicion;
	}

	public void setIdLicitacionGrupoCondicion(Integer idLicitacionGrupoCondicion) {
		this.idLicitacionGrupoCondicion = idLicitacionGrupoCondicion;
	}

	public Integer getIdCondicion() {
		return this.idCondicion;
	}

	public void setIdCondicion(Integer idCondicion) {
		this.idCondicion = idCondicion;
	}

	public String getIndBloqueadoProveedor() {
		return this.indBloqueadoProveedor;
	}

	public void setIndBloqueadoProveedor(String indBloqueadoProveedor) {
		this.indBloqueadoProveedor = indBloqueadoProveedor;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public String getPregunta() {
		return this.pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getTipoCampo() {
		return this.tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public Licitacion getLicitacion() {
		return this.licitacion;
	}

	public void setLicitacion(Licitacion licitacion) {
		this.licitacion = licitacion;
	}

	public TipoLicitacion getTipoLicitacion1() {
		return this.tipoLicitacion1;
	}

	public void setTipoLicitacion1(TipoLicitacion tipoLicitacion1) {
		this.tipoLicitacion1 = tipoLicitacion1;
	}

	public TipoLicitacion getTipoLicitacion2() {
		return this.tipoLicitacion2;
	}

	public void setTipoLicitacion2(TipoLicitacion tipoLicitacion2) {
		this.tipoLicitacion2 = tipoLicitacion2;
	}

	@Override
	public String toString() {
		return "LicitacionGrupoCondicionLic{" +
				"idLicitacionGrupoCondicion=" + idLicitacionGrupoCondicion +
				", idCondicion=" + idCondicion +
				", indBloqueadoProveedor='" + indBloqueadoProveedor + '\'' +
				", peso=" + peso +
				", pregunta='" + pregunta + '\'' +
				", tipoCampo='" + tipoCampo + '\'' +
				", licitacion=" + licitacion +
				", tipoLicitacion1=" + tipoLicitacion1 +
				", tipoLicitacion2=" + tipoLicitacion2 +
				'}';
	}
}