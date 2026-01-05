package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.io.Serializable;


/**
 * The persistent class for the pre_registro_proveedor database table.
 * 
 */
@Entity
@Table(name="pre_registro_proveedor")
public class PreRegistroProveedor extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_registro", unique=true, nullable=false)
	@GeneratedValue(generator = "pre_registro_proveedor_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "pre_registro_proveedor_id_seq", sequenceName = "pre_registro_proveedor_id_seq", allocationSize = 1)
	private Integer idRegistro;

	@Column(name="ruc", nullable=false, length=20)
	private String ruc;

	@Column(name="razon_social", nullable=false, length=300)
	private String razonSocial;

	@Column(name="contacto", nullable=false, length=600)
	private String contacto;

	@Column(name="telefono", nullable=false, length=30)
	private String telefono;

	@Column(name="email", nullable=false, length=100)
	private String email;

	@Column(name="estado", nullable=false, length=2)
	private String estado;

	@Column(name="hcp_id", nullable=false, length=60)
	private String idHcp;

	@Column(name="activo", length=1)
	private String activo;

	@Column(name="habido", length=1)
	private String habido;

	@Column(name="region", length=35)
	private String region;

	@Column(name="provincia",  length=35)
	private String provincia;

	@Column(name="distrito", length=35)
	private String distrito;

	@Column(name="ubigeo",  length=10)
	private String ubigeo;

	@Column(name="direccion", length=500)
	private String direccion;

	@Column(name="sunat",  length=1)
	private String sunat;

	@Column(name="datospersonales",  length=1)
	private String datospersonales;

	@Column(name="comentario",  length=1000)
	private String comentario;

	@ManyToOne
	@JoinColumn(name="id_tipo_proveedor", nullable=false)
	private TipoProveedor tipoProveedor;

	@Size(max = 20)
	@Column(name = "fecha_inicio_acti_sunat", length = 20)
	private String fechaInicioActiSunat;

	@Size(max = 1000)
	@Column(name = "codigo_sistema_emision_elect", length = 1000)
	private String codigoSistemaEmisionElect;

	@Size(max = 1000)
	@Column(name = "codigo_comprobante_pago", length = 1000)
	private String codigoComprobantePago;

	@Size(max = 1000)
	@Column(name = "codigo_padron", length = 1000)
	private String codigoPadron;


	public PreRegistroProveedor() {
	}

	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdHcp() {
		return idHcp;
	}

	public void setIdHcp(String idHcp) {
		this.idHcp = idHcp;
	}

	public TipoProveedor getTipoProveedor() {
		return tipoProveedor;
	}

	public void setTipoProveedor(TipoProveedor tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getHabido() {
		return habido;
	}

	public void setHabido(String habido) {
		this.habido = habido;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getSunat() {
		return sunat;
	}

	public void setSunat(String sunat) {
		this.sunat = sunat;
	}

	public String getDatospersonales() {
		return datospersonales;
	}

	public void setDatospersonales(String datospersonales) {
		this.datospersonales = datospersonales;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	// -- [fechaInicioActiSunat] ------------------------

	public String getFechaInicioActiSunat() {
		return fechaInicioActiSunat;
	}

	public void setFechaInicioActiSunat(String fechaInicioActiSunat) {
		this.fechaInicioActiSunat = fechaInicioActiSunat;
	}

	public PreRegistroProveedor fechaInicioActiSunat(String fechaInicioActiSunat) {
		setFechaInicioActiSunat(fechaInicioActiSunat);
		return this;
	}
	// -- [codigoSistemaEmisionElect] ------------------------


	public String getCodigoSistemaEmisionElect() {
		return codigoSistemaEmisionElect;
	}

	public void setCodigoSistemaEmisionElect(String codigoSistemaEmisionElect) {
		this.codigoSistemaEmisionElect = codigoSistemaEmisionElect;
	}

	public PreRegistroProveedor codigoSistemaEmisionElect(String codigoSistemaEmisionElect) {
		setCodigoSistemaEmisionElect(codigoSistemaEmisionElect);
		return this;
	}
	// -- [codigoComprobantePago] ------------------------

	public String getCodigoComprobantePago() {
		return codigoComprobantePago;
	}

	public void setCodigoComprobantePago(String codigoComprobantePago) {
		this.codigoComprobantePago = codigoComprobantePago;
	}

	public PreRegistroProveedor codigoComprobantePago(String codigoComprobantePago) {
		setCodigoComprobantePago(codigoComprobantePago);
		return this;
	}
	// -- [codigoPadron] ------------------------

	public String getCodigoPadron() {
		return codigoPadron;
	}

	public void setCodigoPadron(String codigoPadron) {
		this.codigoPadron = codigoPadron;
	}

	public PreRegistroProveedor codigoPadron(String codigoPadron) {
		setCodigoPadron(codigoPadron);
		return this;
	}

	@Override
	public String toString() {
		return "PreRegistroProveedor{" +
				"idRegistro=" + idRegistro +
				", ruc='" + ruc + '\'' +
				", razonSocial='" + razonSocial + '\'' +
				", comentario='" + comentario + '\'' +
				", contacto='" + contacto + '\'' +
				", telefono='" + telefono + '\'' +
				", email='" + email + '\'' +
				", estado='" + estado + '\'' +
				", idHcp='" + idHcp + '\'' +
				", activo='" + activo + '\'' +
				", habido='" + habido + '\'' +
				", region='" + region + '\'' +
				", provincia='" + provincia + '\'' +
				", distrito='" + distrito + '\'' +
				", ubigeo='" + ubigeo + '\'' +
				", direccion='" + direccion + '\'' +
				", sunat='" + sunat + '\'' +
				", datospersonales='" + datospersonales + '\'' +
				", tipoProveedor=" + tipoProveedor +
				", fechaInicioActiSunat=" + fechaInicioActiSunat +
				", codigoSistemaEmisionElect='" + codigoSistemaEmisionElect + '\'' +
				", codigoComprobantePago='" + codigoComprobantePago + '\'' +
				", codigoPadron='" + codigoPadron + '\'' +
				'}';
	}


}