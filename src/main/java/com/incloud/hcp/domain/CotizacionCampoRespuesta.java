package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the cotizacion_campo_respuesta database table.
 * 
 */
@Entity
@Table(name="cotizacion_campo_respuesta")
public class CotizacionCampoRespuesta extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CotizacionCampoRespuestaPK id;

	//@Column(length=2147483647)
	@Column(length=64000)
	private String comentario;

	@Column(name="fecha_registro")
	private Timestamp fechaRegistro;


	@Column(name="peso", nullable=false, precision=15, scale=2)
	private BigDecimal peso;

	@Column(name="puntaje", nullable=false, precision=15, scale=2)
	private BigDecimal puntaje;

	@Column(name="texto_respuesta_libre", length=250)
	private String textoRespuestaLibre;

	//uni-directional many-to-one association to Cotizacion
	@ManyToOne
	@JoinColumn(name="id_cotizacion", nullable=false, insertable=false, updatable=false)
	private Cotizacion cotizacion;

	//uni-directional many-to-one association to GrupoCondicionLicRespuesta
	@ManyToOne
	@JoinColumn(name="id_condicion_respuesta")
	private GrupoCondicionLicRespuesta grupoCondicionLicRespuesta;

	//uni-directional many-to-one association to LicitacionGrupoCondicionLic
	@ManyToOne
	@JoinColumn(name="id_licitacion_grupo_condicion", nullable=false, insertable=false, updatable=false)
	private LicitacionGrupoCondicionLic licitacionGrupoCondicionLic;

	public CotizacionCampoRespuesta() {
	}

	public CotizacionCampoRespuestaPK getId() {
		return this.id;
	}

	public void setId(CotizacionCampoRespuestaPK id) {
		this.id = id;
	}

	public String getComentario() {
		return this.comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public BigDecimal getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(BigDecimal puntaje) {
		this.puntaje = puntaje;
	}

	public String getTextoRespuestaLibre() {
		return this.textoRespuestaLibre;
	}

	public void setTextoRespuestaLibre(String textoRespuestaLibre) {
		this.textoRespuestaLibre = textoRespuestaLibre;
	}

	public Cotizacion getCotizacion() {
		return this.cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	public GrupoCondicionLicRespuesta getGrupoCondicionLicRespuesta() {
		return this.grupoCondicionLicRespuesta;
	}

	public void setGrupoCondicionLicRespuesta(GrupoCondicionLicRespuesta grupoCondicionLicRespuesta) {
		this.grupoCondicionLicRespuesta = grupoCondicionLicRespuesta;
	}

	public LicitacionGrupoCondicionLic getLicitacionGrupoCondicionLic() {
		return this.licitacionGrupoCondicionLic;
	}

	public void setLicitacionGrupoCondicionLic(LicitacionGrupoCondicionLic licitacionGrupoCondicionLic) {
		this.licitacionGrupoCondicionLic = licitacionGrupoCondicionLic;
	}

	@Override
	public String toString() {
		return "CotizacionCampoRespuesta{" +
				"id=" + id +
				", comentario='" + comentario + '\'' +
				", fechaRegistro=" + fechaRegistro +
				", peso=" + peso +
				", puntaje=" + puntaje +
				", textoRespuestaLibre='" + textoRespuestaLibre + '\'' +
				", cotizacion=" + cotizacion +
				", grupoCondicionLicRespuesta=" + grupoCondicionLicRespuesta +
				", licitacionGrupoCondicionLic=" + licitacionGrupoCondicionLic +
				'}';
	}
}