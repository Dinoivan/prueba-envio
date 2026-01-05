package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.validation.FixedLength;
import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the proveedor_cuenta_bancaria database table.
 * 
 */
@Entity
@Table(name="proveedor_cuenta_bancaria")
public class ProveedorCuentaBancaria extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cuenta", unique=true, nullable=false)
	@GeneratedValue(generator = "proveedor_cuenta_bancaria_id_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "proveedor_cuenta_bancaria_id_seq", sequenceName = "proveedor_cuenta_bancaria_id_seq", allocationSize = 1)
	private Integer idCuenta;

	@Column(name="clave_control_banco", nullable=false, length=2)
	private String claveControlBanco;

	@Column(length=100)
	private String contacto;

	@Column(name="numero_cuenta", nullable=false, length=18)
	private String numeroCuenta;

	@Column(name="numero_cuenta_cci", length=25)
	private String numeroCuentaCci;

	private String indCuentaDetraccion;

	//uni-directional many-to-one association to Banco
	@ManyToOne
	@JoinColumn(name="id_banco", nullable=false)
	private Banco banco;

	//uni-directional many-to-one association to Moneda
	@ManyToOne
	@JoinColumn(name="id_moneda", nullable=false)
	private Moneda moneda;

	//uni-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false)
	private Proveedor proveedor;

	@Column(name = "archivo_id", length = 60)
	private String archivoId;

	@Column(name = "archivo_nombre", length = 100)
	private String archivoNombre;

	@Column(name = "archivo_tipo", length = 100)
	private String archivoTipo;

    @Column(name = "ruta_adjunto", length = 1000)
	private String rutaAdjunto;


	public ProveedorCuentaBancaria() {
	}

	public Integer getIdCuenta() {
		return this.idCuenta;
	}

	public void setIdCuenta(Integer idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getClaveControlBanco() {
		return this.claveControlBanco;
	}

	public void setClaveControlBanco(String claveControlBanco) {
		this.claveControlBanco = claveControlBanco;
	}

	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getNumeroCuentaCci() {
		return this.numeroCuentaCci;
	}

	public void setNumeroCuentaCci(String numeroCuentaCci) {
		this.numeroCuentaCci = numeroCuentaCci;
	}

	public Banco getBanco() {
		return this.banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

    public String getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(String archivoId) {
        this.archivoId = archivoId;
    }

    public String getArchivoNombre() {
        return archivoNombre;
    }

    public void setArchivoNombre(String archivoNombre) {
        this.archivoNombre = archivoNombre;
    }

    public String getArchivoTipo() {
        return archivoTipo;
    }

    public void setArchivoTipo(String archivoTipo) {
        this.archivoTipo = archivoTipo;
    }

    public String getRutaAdjunto() {
        return rutaAdjunto;
    }

    public void setRutaAdjunto(String rutaAdjunto) {
        this.rutaAdjunto = rutaAdjunto;
    }

    // -- [indCuentaDetraccion] ------------------------

	@FixedLength(length = 1)
	@Column(name = "ind_cuenta_detraccion", length = 1)
	public String getIndCuentaDetraccion() {
		return indCuentaDetraccion;
	}

	public void setIndCuentaDetraccion(String indCuentaDetraccion) {
		this.indCuentaDetraccion = indCuentaDetraccion;
	}

	public ProveedorCuentaBancaria indCuentaDetraccion(String indCuentaDetraccion) {
		setIndCuentaDetraccion(indCuentaDetraccion);
		return this;
	}

	@Override
	public String toString() {
		return "ProveedorCuentaBancaria{" +
				"idCuenta=" + idCuenta +
				", claveControlBanco='" + claveControlBanco + '\'' +
				", contacto='" + contacto + '\'' +
				", numeroCuenta='" + numeroCuenta + '\'' +
				", numeroCuentaCci='" + numeroCuentaCci + '\'' +
				", indCuentaDetraccion='" + indCuentaDetraccion + '\'' +
				", banco=" + banco +
				", moneda=" + moneda +
				", proveedor=" + proveedor +
				'}';
	}
}