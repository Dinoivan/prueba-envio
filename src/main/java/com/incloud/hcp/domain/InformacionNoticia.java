package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the informacion_noticia database table.
 * 
 */
@Entity
@Table(name="informacion_noticia")
public class InformacionNoticia extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_informacion_noticia", unique=true, nullable=false)
	@GeneratedValue(generator = "informacion_noticia_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "informacion_noticia_id_seq", sequenceName = "informacion_noticia_id_seq", allocationSize = 1)
	private Integer idInformacionNoticia;

	//@Column(nullable=false, length=2147483647)
	@Column(nullable=false,length=64000)
	private String contenido;

	@Column(name="fecha_caducidad")
	private Timestamp fechaCaducidad;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="fecha_publicacion", nullable=false)
	private Timestamp fechaPublicacion;

	@Column(name="ind_activo", nullable=false, length=1)
	private String indActivo;

	@Column(name="ind_noticia_nuevo_proveedor", nullable=false, length=1)
	private String indNoticiaNuevoProveedor;

	@Column(name="ruta_adjunto", length=1000)
	private String rutaAdjunto;

	@Column(name="usuario_creacion", nullable=false)
	private Integer usuarioCreacion;

	@Column(name="usuario_modificacion")
	private Integer usuarioModificacion;

	@Column(name="icon_text")
	private String iconText;

	@Column(name="titulo")
	private String titulo;

	@Column(name="texto_info")
	private String textoInfo;

	@Column(name="archivo_nombre")
	private String archivoNombre;

	@Column(name="archivo_id")
	private String archivoId;

	@Column(name="archivo_id_cf")
	private String archivoIdCf;

	@Column(name="archivo_url_cf")
	private String archivoUrlCf;

	//uni-directional many-to-one association to TipoInformacionNoticia
	@ManyToOne
	@JoinColumn(name="id_tipo_informacion_noticia", nullable=false)
	private TipoInformacionNoticia tipoInformacionNoticia;

	public InformacionNoticia() {
	}

	public Integer getIdInformacionNoticia() {
		return this.idInformacionNoticia;
	}

	public void setIdInformacionNoticia(Integer idInformacionNoticia) {
		this.idInformacionNoticia = idInformacionNoticia;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Timestamp getFechaCaducidad() {
		return this.fechaCaducidad;
	}

	public void setFechaCaducidad(Timestamp fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
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

	public Timestamp getFechaPublicacion() {
		return this.fechaPublicacion;
	}

	public void setFechaPublicacion(Timestamp fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getIndActivo() {
		return this.indActivo;
	}

	public void setIndActivo(String indActivo) {
		this.indActivo = indActivo;
	}

	public String getIndNoticiaNuevoProveedor() {
		return this.indNoticiaNuevoProveedor;
	}

	public void setIndNoticiaNuevoProveedor(String indNoticiaNuevoProveedor) {
		this.indNoticiaNuevoProveedor = indNoticiaNuevoProveedor;
	}

	public String getRutaAdjunto() {
		return this.rutaAdjunto;
	}

	public void setRutaAdjunto(String rutaAdjunto) {
		this.rutaAdjunto = rutaAdjunto;
	}

	public Integer getUsuarioCreacion() {
		return this.usuarioCreacion;
	}

	public void setUsuarioCreacion(Integer usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Integer getUsuarioModificacion() {
		return this.usuarioModificacion;
	}

	public void setUsuarioModificacion(Integer usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public TipoInformacionNoticia getTipoInformacionNoticia() {
		return this.tipoInformacionNoticia;
	}

	public void setTipoInformacionNoticia(TipoInformacionNoticia tipoInformacionNoticia) {
		this.tipoInformacionNoticia = tipoInformacionNoticia;
	}

	public String getTextoInfo() {
		return textoInfo;
	}

	public void setTextoInfo(String textoInfo) {
		this.textoInfo = textoInfo;
	}

	public String getIconText() {
		return iconText;
	}

	public void setIconText(String iconText) {
		this.iconText = iconText;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArchivoNombre() {
		return archivoNombre;
	}

	public void setArchivoNombre(String archivoNombre) {
		this.archivoNombre = archivoNombre;
	}

	public String getArchivoId() {
		return archivoId;
	}

	public void setArchivoId(String archivoId) {
		this.archivoId = archivoId;
	}

	public String getArchivoIdCf() {
		return archivoIdCf;
	}

	public void setArchivoIdCf(String archivoIdCf) {
		this.archivoIdCf = archivoIdCf;
	}

	public String getArchivoUrlCf() {
		return archivoUrlCf;
	}

	public void setArchivoUrlCf(String archivoUrlCf) {
		this.archivoUrlCf = archivoUrlCf;
	}

	@Override
	public String toString() {
		return "InformacionNoticia{" +
				"idInformacionNoticia=" + idInformacionNoticia +
				", contenido='" + contenido + '\'' +
				", fechaCaducidad=" + fechaCaducidad +
				", fechaCreacion=" + fechaCreacion +
				", fechaModificacion=" + fechaModificacion +
				", fechaPublicacion=" + fechaPublicacion +
				", indActivo='" + indActivo + '\'' +
				", indNoticiaNuevoProveedor='" + indNoticiaNuevoProveedor + '\'' +
				", rutaAdjunto='" + rutaAdjunto + '\'' +
				", usuarioCreacion=" + usuarioCreacion +
				", usuarioModificacion=" + usuarioModificacion +
				", iconText='" + iconText + '\'' +
				", titulo='" + titulo + '\'' +
				", textoInfo='" + textoInfo + '\'' +
				", archivoNombre='" + archivoNombre + '\'' +
				", archivoId='" + archivoId + '\'' +
				", tipoInformacionNoticia=" + tipoInformacionNoticia +
				'}';
	}
}