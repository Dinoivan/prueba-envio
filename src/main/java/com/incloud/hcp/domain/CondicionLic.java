package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the condicion_lic database table.
 * 
 */
@Entity
@Table(name="condicion_lic")
public class CondicionLic extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_condicion", unique=true, nullable=false)
	@GeneratedValue(generator = "condicion_lic_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "condicion_lic_id_seq", sequenceName = "condicion_lic_id_seq", allocationSize = 1)
	private Integer idCondicion;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="ind_bloqueado_proveedor", length=1)
	private String indBloqueadoProveedor;

	@Column(name="peso", nullable=false, precision=5, scale=2)
	private BigDecimal peso;

	@Column(nullable=false, length=300)
	private String pregunta;

	@Column(name="tipo_campo", nullable=false, length=1)
	private String tipoCampo;

	@Column(name="usuario_creacion", nullable=false)
	private String usuarioCreacion;

	@Column(name="usuario_modificacion")
	private String usuarioModificacion;

	//uni-directional many-to-one association to TipoLicitacion
	@ManyToOne
	@JoinColumn(name="id_tipo_licitacion", nullable=false, insertable = false, updatable = false)
	private TipoLicitacion tipoLicitacion1;

    @Column(name="id_tipo_licitacion")
	private Integer idTipoLicitacion1;

	//uni-directional many-to-one association to TipoLicitacion
	@ManyToOne
	@JoinColumn(name="id_tipo_cuestionario", nullable=false, insertable = false, updatable = false)
	private TipoLicitacion tipoLicitacion2;

    @Column(name="id_tipo_cuestionario")
    private Integer idTipoLicitacion2;

	@Transient
	private String descripcionTipoLicitacion;

	@Transient
	private String descripcionTipoCuestionario;


	public CondicionLic() {
	}

	public Integer getIdCondicion() {
		return this.idCondicion;
	}

	public void setIdCondicion(Integer idCondicion) {
		this.idCondicion = idCondicion;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModificacion() {
		return this.fechaModificacion;
	}

	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
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

		String strTipoCampo = ("L").equals(tipoCampo) ? "Lista" : "Texto";
		return strTipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public TipoLicitacion getTipoLicitacion1() {
		return this.tipoLicitacion1;
	}

	public void setTipoLicitacion1(TipoLicitacion tipoLicitacion1) {
		this.tipoLicitacion1 = tipoLicitacion1;
	}

    public Integer getIdTipoLicitacion1() {
        return idTipoLicitacion1;
    }

    public void setIdTipoLicitacion1(Integer idTipoLicitacion1) {
        this.idTipoLicitacion1 = idTipoLicitacion1;
    }

    public TipoLicitacion getTipoLicitacion2() {
		return this.tipoLicitacion2;
	}

	public void setTipoLicitacion2(TipoLicitacion tipoLicitacion2) {
		this.tipoLicitacion2 = tipoLicitacion2;
	}

    public Integer getIdTipoLicitacion2() {
        return idTipoLicitacion2;
    }

    public void setIdTipoLicitacion2(Integer idTipoLicitacion2) {
        this.idTipoLicitacion2 = idTipoLicitacion2;
    }

    public String getDescripcionTipoLicitacion() {
		return descripcionTipoLicitacion;
	}

	public void setDescripcionTipoLicitacion(String descripcionTipoLicitacion) {
		this.descripcionTipoLicitacion = descripcionTipoLicitacion;
	}

	public String getDescripcionTipoCuestionario() {
		return descripcionTipoCuestionario;
	}

	public void setDescripcionTipoCuestionario(String descripcionTipoCuestionario) {
		this.descripcionTipoCuestionario = descripcionTipoCuestionario;
	}

	@Override
	public String toString() {
		return "CondicionLic{" +
				"idCondicion=" + idCondicion +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", indBloqueadoProveedor='" + indBloqueadoProveedor + '\'' +
				", peso=" + peso +
				", pregunta='" + pregunta + '\'' +
				", tipoCampo='" + tipoCampo + '\'' +
				", usuarioCreacion='" + usuarioCreacion + '\'' +
				", usuarioModificacion='" + usuarioModificacion + '\'' +
                ", tipoLicitacion1=" + tipoLicitacion1 +
                ", idTipoLicitacion1=" + idTipoLicitacion1 +
                ", tipoLicitacion2=" + tipoLicitacion2 +
                ", idTipoLicitacion2=" + idTipoLicitacion2 +
				", descripcionTipoLicitacion='" + descripcionTipoLicitacion + '\'' +
				", descripcionTipoCuestionario='" + descripcionTipoCuestionario + '\'' +
				'}';
	}
}