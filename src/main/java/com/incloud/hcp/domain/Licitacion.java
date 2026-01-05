package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import org.apache.commons.lang.StringUtils;

import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the licitacion database table prueba
 * 
 */
@Entity
@Table(name="licitacion")
public class Licitacion extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_licitacion", unique=true, nullable=false)
	@GeneratedValue(generator = "licitacion_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "licitacion_id_seq", sequenceName = "licitacion_id_seq", allocationSize = 1)
	private Integer idLicitacion;

	@Column(name="comentario_anulacion", length=500)
	private String comentarioAnulacion;

	@Column(name="comentario_licitacion", length=500)
	private String comentarioLicitacion;

	@Column(name="estado_licitacion", nullable=false, length=2)
	private String estadoLicitacion;

	@Column(name="fecha_inicio_recepcion_oferta", nullable=false)
	private Timestamp fechaInicioRecepcionOferta;

	@Column(name="fecha_cierre_recepcion_oferta", nullable=false)
	private Timestamp fechaCierreRecepcionOferta;

	@Column(name="fecha_ultima_renegociacion", nullable=false)
	private Timestamp fechaUltimaRenegociacion;

	@Column(name="fecha_cierre_confirmacion_participacion")
	private Timestamp fechaCierreConfirmacionParticipacion;

	@Column(name="fecha_cierre_consulta_pregunta")
	private Timestamp fechaCierreConsultaPregunta;

	@Column(name="fecha_creacion", nullable=false)
	private Timestamp fechaCreacion;

	@Column(name="fecha_entrega_inicio")
	private Timestamp fechaEntregaInicio;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name="fecha_publicacion")
	private Timestamp fechaPublicacion;

	@Column(name="necesidad_urgencia", nullable=true, length=2)
	private String necesidadUrgencia;

	@Column(name="nro_licitacion", nullable=false)
	private Integer nroLicitacion;

	@Column(name="anio_licitacion", nullable=false)
	private Integer anioLicitacion;

	@Column(name="usuario_creacion", nullable=false)
	private String usuarioCreacion;

	@Column(name="usuario_modificacion")
	private String usuarioModificacion;

	@Column(name="punto_entrega", nullable=false, length=2)
	private String puntoEntrega;

	@Column(name="usuario_publicacion_id", nullable=true, length=20)
	private String usuarioPublicacionId;

	@Column(name="usuario_publicacion_name", nullable=true, length=100)
	private String usuarioPublicacionName;

	@Column(name="usuario_publicacion_email", nullable=true, length=100)
	private String usuarioPublicacionEmail;

	@Column(name="usuario_anulacion_id", length=20)
	private String usuarioAnulacionId;

	@Column(name="ind_republicado", nullable=true, length=1)
	private String indRepublicado;

	@Column(name="ind_activar_cotizacion", nullable=true, length=1)
	private String indActivarCotizacion;


	@Column(name="ind_ejecucion_sap_ok", length=1)
	private String indEjecucionSapOk;

	@Column(name="ind_creacion_proveedor_sap_ok", length=1)
	private String indCreacionProveedorSapOk;

	@Column(name="ind_creacion_oc_sap_ok", length=1)
	private String indCreacionOcSapOk;

	@Column(name="ind_creacion_cm_sap_ok", length=1)
	private String indCreacionCmSapOk;

    @Column(name="NUMERO_PETICION_OFERTA_LICITACION_SAP", length=500)
    private String numeroPeticionOfertaLicitacionSap;

	@Transient
	private String fechaInicioRecepcionOfertaString;

	@Transient
	private String fechaCierreRecepcionOfertaString;

	@Transient
	private String fechaCierreConfirmacionParticipacionString;

	@Transient
	private String fechaCierreConsultaPreguntaString;

	@Transient
	private String fechaCreacionString;

	@Transient
	private String fechaPublicacionString;

	@Transient
	private String nroLicitacionString;

	@Transient
	private String nroLicitacionCero;

	@Transient
	private Integer nroProveedoresACotizar;

	@Transient
	private Integer nroProveedoresEnviaronCotizacion;

	@Transient
	private String fechaEntregaInicioString;

	@Transient
	private String descripcionPuntoEntrega;

    @Transient
    private String descripcionEstado;

    @Transient
    private String descripcionNecesidadUrgencia;

	@Transient
	private String fechaUltimaRenegociacionString;



	//uni-directional many-to-one association to CentroAlmacen
	@ManyToOne
	@JoinColumn(name="id_centro")
	private CentroAlmacen centroAlmacen1;

	//uni-directional many-to-one association to CentroAlmacen
	@ManyToOne
	@JoinColumn(name="id_almacen")
	private CentroAlmacen centroAlmacen2;

	//uni-directional many-to-one association to ClaseDocumento
	@ManyToOne
	@JoinColumn(name="id_clase_documento", nullable=true)
	private ClaseDocumento claseDocumento;

	//uni-directional many-to-one association to Moneda
	@ManyToOne
	@JoinColumn(name="id_moneda", nullable=false)
	private Moneda moneda;

	public Licitacion() {
	}

	public Integer getIdLicitacion() {
		return this.idLicitacion;
	}

	public void setIdLicitacion(Integer idLicitacion) {
		this.idLicitacion = idLicitacion;
	}

	public String getComentarioAnulacion() {
		return this.comentarioAnulacion;
	}

	public void setComentarioAnulacion(String comentarioAnulacion) {
		this.comentarioAnulacion = comentarioAnulacion;
	}

	public String getComentarioLicitacion() {
		return this.comentarioLicitacion;
	}

	public void setComentarioLicitacion(String comentarioLicitacion) {
		this.comentarioLicitacion = comentarioLicitacion;
	}

	public String getEstadoLicitacion() {
		return this.estadoLicitacion;
	}

	public void setEstadoLicitacion(String estadoLicitacion) {
		this.estadoLicitacion = estadoLicitacion;
	}

	public Timestamp getFechaCierreRecepcionOferta() {
		return this.fechaCierreRecepcionOferta;
	}

	public void setFechaCierreRecepcionOferta(Timestamp fechaCierreRecepcionOferta) {
		this.fechaCierreRecepcionOferta = fechaCierreRecepcionOferta;
	}

	public Timestamp getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaEntregaInicio() {
		return this.fechaEntregaInicio;
	}

	public void setFechaEntregaInicio(Timestamp fechaEntregaInicio) {
		this.fechaEntregaInicio = fechaEntregaInicio;
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

	public String getNecesidadUrgencia() {
		return this.necesidadUrgencia;
	}

	public void setNecesidadUrgencia(String necesidadUrgencia) {
		this.necesidadUrgencia = necesidadUrgencia;
	}

	public Integer getNroLicitacion() {
		return this.nroLicitacion;
	}

	public void setNroLicitacion(Integer nroLicitacion) {
		this.nroLicitacion = nroLicitacion;
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

	public CentroAlmacen getCentroAlmacen1() {
		return this.centroAlmacen1;
	}

	public void setCentroAlmacen1(CentroAlmacen centroAlmacen1) {
		this.centroAlmacen1 = centroAlmacen1;
	}

	public CentroAlmacen getCentroAlmacen2() {
		return this.centroAlmacen2;
	}

	public void setCentroAlmacen2(CentroAlmacen centroAlmacen2) {
		this.centroAlmacen2 = centroAlmacen2;
	}

	public ClaseDocumento getClaseDocumento() {
		return this.claseDocumento;
	}

	public void setClaseDocumento(ClaseDocumento claseDocumento) {
		this.claseDocumento = claseDocumento;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getPuntoEntrega() {
		return puntoEntrega;
	}

	public void setPuntoEntrega(String puntoEntrega) {
		this.puntoEntrega = puntoEntrega;
	}

	public String getDescripcionPuntoEntrega() {
		return descripcionPuntoEntrega;
	}

	public void setDescripcionPuntoEntrega(String descripcionPuntoEntrega) {
		this.descripcionPuntoEntrega = descripcionPuntoEntrega;
	}

	public String getFechaEntregaInicioString() {
		return fechaEntregaInicioString;
	}

	public void setFechaEntregaInicioString(String fechaEntregaInicioString) {
		this.fechaEntregaInicioString = fechaEntregaInicioString;
	}

	public Integer getAnioLicitacion() {
		return anioLicitacion;
	}

	public void setAnioLicitacion(Integer anioLicitacion) {
		this.anioLicitacion = anioLicitacion;
	}

	public String getNroLicitacionString() {
		return nroLicitacionString;
	}

	public void setNroLicitacionString(String nroLicitacionString) {
		this.nroLicitacionString = nroLicitacionString;
	}

	public String getFechaCierreRecepcionOfertaString() {
		return fechaCierreRecepcionOfertaString;
	}

	public void setFechaCierreRecepcionOfertaString(String fechaCierreRecepcionOfertaString) {
		this.fechaCierreRecepcionOfertaString = fechaCierreRecepcionOfertaString;
	}

	public String getFechaCreacionString() {
		return fechaCreacionString;
	}

	public void setFechaCreacionString(String fechaCreacionString) {
		this.fechaCreacionString = fechaCreacionString;
	}

	public String getFechaPublicacionString() {
		return fechaPublicacionString;
	}

	public void setFechaPublicacionString(String fechaPublicacionString) {
		this.fechaPublicacionString = fechaPublicacionString;
	}

	public String getNroLicitacionCero() {
		return this.anioLicitacion + StringUtils.leftPad(this.nroLicitacion.toString().trim(), 8, '0');
	}

	public void setNroLicitacionCero(String nroLicitacionCero) {
		this.nroLicitacionCero = nroLicitacionCero;
	}

	public Integer getNroProveedoresACotizar() {
		return nroProveedoresACotizar;
	}

	public void setNroProveedoresACotizar(Integer nroProveedoresACotizar) {
		this.nroProveedoresACotizar = nroProveedoresACotizar;
	}

	public Integer getNroProveedoresEnviaronCotizacion() {
		return nroProveedoresEnviaronCotizacion;
	}

	public void setNroProveedoresEnviaronCotizacion(Integer nroProveedoresEnviaronCotizacion) {
		this.nroProveedoresEnviaronCotizacion = nroProveedoresEnviaronCotizacion;
	}

	public String getUsuarioPublicacionId() {
		return usuarioPublicacionId;
	}

	public void setUsuarioPublicacionId(String usuarioPublicacionId) {
		this.usuarioPublicacionId = usuarioPublicacionId;
	}

	public String getUsuarioPublicacionName() {
		return usuarioPublicacionName;
	}

	public void setUsuarioPublicacionName(String usuarioPublicacionName) {
		this.usuarioPublicacionName = usuarioPublicacionName;
	}

	public String getUsuarioPublicacionEmail() {
		return usuarioPublicacionEmail;
	}

	public void setUsuarioPublicacionEmail(String usuarioPublicacionEmail) {
		this.usuarioPublicacionEmail = usuarioPublicacionEmail;
	}

	public String getUsuarioAnulacionId() {
		return usuarioAnulacionId;
	}

	public void setUsuarioAnulacionId(String usuarioAnulacionId) {
		this.usuarioAnulacionId = usuarioAnulacionId;
	}

	public String getIndRepublicado() {
		return indRepublicado;
	}

	public void setIndRepublicado(String indRepublicado) {
		this.indRepublicado = indRepublicado;
	}

	public String getFechaUltimaRenegociacionString() {
		return fechaUltimaRenegociacionString;
	}

	public void setFechaUltimaRenegociacionString(String fechaUltimaRenegociacionString) {
		this.fechaUltimaRenegociacionString = fechaUltimaRenegociacionString;
	}

	public String getIndActivarCotizacion() {
		return indActivarCotizacion;
	}

	public void setIndActivarCotizacion(String indActivarCotizacion) {
		this.indActivarCotizacion = indActivarCotizacion;
	}


	public Timestamp getFechaInicioRecepcionOferta() {
		return fechaInicioRecepcionOferta;
	}

	public void setFechaInicioRecepcionOferta(Timestamp fechaInicioRecepcionOferta) {
		this.fechaInicioRecepcionOferta = fechaInicioRecepcionOferta;
	}

	public Timestamp getFechaCierreConfirmacionParticipacion() {
		return fechaCierreConfirmacionParticipacion;
	}

	public void setFechaCierreConfirmacionParticipacion(Timestamp fechaCierreConfirmacionParticipacion) {
		this.fechaCierreConfirmacionParticipacion = fechaCierreConfirmacionParticipacion;
	}

	public String getFechaInicioRecepcionOfertaString() {
		return fechaInicioRecepcionOfertaString;
	}

	public void setFechaInicioRecepcionOfertaString(String fechaInicioRecepcionOfertaString) {
		this.fechaInicioRecepcionOfertaString = fechaInicioRecepcionOfertaString;
	}

	public String getFechaCierreConfirmacionParticipacionString() {
		return fechaCierreConfirmacionParticipacionString;
	}

	public void setFechaCierreConfirmacionParticipacionString(String fechaCierreConfirmacionParticipacionString) {
		this.fechaCierreConfirmacionParticipacionString = fechaCierreConfirmacionParticipacionString;
	}

	public Timestamp getFechaUltimaRenegociacion() {
		return fechaUltimaRenegociacion;
	}

	public void setFechaUltimaRenegociacion(Timestamp fechaUltimaRenegociacion) {
		this.fechaUltimaRenegociacion = fechaUltimaRenegociacion;
	}

	public Timestamp getFechaCierreConsultaPregunta() {
		return fechaCierreConsultaPregunta;
	}

	public void setFechaCierreConsultaPregunta(Timestamp fechaCierreConsultaPregunta) {
		this.fechaCierreConsultaPregunta = fechaCierreConsultaPregunta;
	}

	public String getFechaCierreConsultaPreguntaString() {
		return fechaCierreConsultaPreguntaString;
	}

	public void setFechaCierreConsultaPreguntaString(String fechaCierreConsultaPreguntaString) {
		this.fechaCierreConsultaPreguntaString = fechaCierreConsultaPreguntaString;
	}

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public String getDescripcionNecesidadUrgencia() {
        return descripcionNecesidadUrgencia;
    }

    public void setDescripcionNecesidadUrgencia(String descripcionNecesidadUrgencia) {
        this.descripcionNecesidadUrgencia = descripcionNecesidadUrgencia;
    }

	public String getIndEjecucionSapOk() {
		return indEjecucionSapOk;
	}

	public void setIndEjecucionSapOk(String indEjecucionSapOk) {
		this.indEjecucionSapOk = indEjecucionSapOk;
	}

	public String getIndCreacionProveedorSapOk() {
		return indCreacionProveedorSapOk;
	}

	public void setIndCreacionProveedorSapOk(String indCreacionProveedorSapOk) {
		this.indCreacionProveedorSapOk = indCreacionProveedorSapOk;
	}

	public String getIndCreacionOcSapOk() {
		return indCreacionOcSapOk;
	}

	public void setIndCreacionOcSapOk(String indCreacionOcSapOk) {
		this.indCreacionOcSapOk = indCreacionOcSapOk;
	}

	public String getIndCreacionCmSapOk() {
		return indCreacionCmSapOk;
	}

	public void setIndCreacionCmSapOk(String indCreacionCmSapOk) {
		this.indCreacionCmSapOk = indCreacionCmSapOk;
	}

    public String getNumeroPeticionOfertaLicitacionSap() {
        return numeroPeticionOfertaLicitacionSap;
    }

    public void setNumeroPeticionOfertaLicitacionSap(String numeroPeticionOfertaLicitacionSap) {
        this.numeroPeticionOfertaLicitacionSap = numeroPeticionOfertaLicitacionSap;
    }

    @Override
	public String toString() {
		return "Licitacion{" +
				"idLicitacion=" + idLicitacion +
				", comentarioAnulacion='" + comentarioAnulacion + '\'' +
				", comentarioLicitacion='" + comentarioLicitacion + '\'' +
				", estadoLicitacion='" + estadoLicitacion + '\'' +
				", fechaInicioRecepcionOferta=" + fechaInicioRecepcionOferta +
				", fechaCierreRecepcionOferta=" + fechaCierreRecepcionOferta +
				", fechaUltimaRenegociacion=" + fechaUltimaRenegociacion +
				", fechaCierreConfirmacionParticipacion=" + fechaCierreConfirmacionParticipacion +
				", fechaCierreConsultaPregunta=" + fechaCierreConsultaPregunta +
				", fechaCreacion=" + fechaCreacion +
				", fechaEntregaInicio=" + fechaEntregaInicio +
				", fechaModificacion=" + fechaModificacion +
				", fechaPublicacion=" + fechaPublicacion +
				", necesidadUrgencia='" + necesidadUrgencia + '\'' +
				", nroLicitacion=" + nroLicitacion +
				", anioLicitacion=" + anioLicitacion +
				", usuarioCreacion='" + usuarioCreacion + '\'' +
				", usuarioModificacion='" + usuarioModificacion + '\'' +
				", puntoEntrega='" + puntoEntrega + '\'' +
				", usuarioPublicacionId='" + usuarioPublicacionId + '\'' +
				", usuarioPublicacionName='" + usuarioPublicacionName + '\'' +
				", usuarioPublicacionEmail='" + usuarioPublicacionEmail + '\'' +
				", usuarioAnulacionId='" + usuarioAnulacionId + '\'' +
				", indRepublicado='" + indRepublicado + '\'' +
				", indActivarCotizacion='" + indActivarCotizacion + '\'' +
				", indEjecucionSapOk='" + indEjecucionSapOk + '\'' +
				", indCreacionProveedorSapOk='" + indCreacionProveedorSapOk + '\'' +
				", indCreacionOcSapOk='" + indCreacionOcSapOk + '\'' +
				", indCreacionCmSapOk='" + indCreacionCmSapOk + '\'' +
                ", numeroPeticionOfertaLicitacionSap='" + numeroPeticionOfertaLicitacionSap + '\'' +
				", fechaInicioRecepcionOfertaString='" + fechaInicioRecepcionOfertaString + '\'' +
				", fechaCierreRecepcionOfertaString='" + fechaCierreRecepcionOfertaString + '\'' +
				", fechaCierreConfirmacionParticipacionString='" + fechaCierreConfirmacionParticipacionString + '\'' +
				", fechaCierreConsultaPreguntaString='" + fechaCierreConsultaPreguntaString + '\'' +
				", fechaCreacionString='" + fechaCreacionString + '\'' +
				", fechaPublicacionString='" + fechaPublicacionString + '\'' +
				", nroLicitacionString='" + nroLicitacionString + '\'' +
				", nroLicitacionCero='" + nroLicitacionCero + '\'' +
				", nroProveedoresACotizar=" + nroProveedoresACotizar +
				", nroProveedoresEnviaronCotizacion=" + nroProveedoresEnviaronCotizacion +
				", fechaEntregaInicioString='" + fechaEntregaInicioString + '\'' +
				", descripcionPuntoEntrega='" + descripcionPuntoEntrega + '\'' +
				", descripcionEstado='" + descripcionEstado + '\'' +
				", descripcionNecesidadUrgencia='" + descripcionNecesidadUrgencia + '\'' +
				", fechaUltimaRenegociacionString='" + fechaUltimaRenegociacionString + '\'' +
				", centroAlmacen1=" + centroAlmacen1 +
				", centroAlmacen2=" + centroAlmacen2 +
				", claseDocumento=" + claseDocumento +
				", moneda=" + moneda +
				'}';
	}
}