package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.validation.FixedLength;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the proveedor database table.
 *
 */
@Entity
@Table(name="proveedor")
public class Proveedor extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id_proveedor", unique=true, nullable=false)
    @GeneratedValue(generator = "proveedor_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "proveedor_id_seq", sequenceName = "proveedor_id_seq", allocationSize = 1)
    private Integer idProveedor;

    @Column(name="acreedor_codigo_sap", length=10)
    private String acreedorCodigoSap;

    @Column(length=100)
    private String contacto;

    @Column(name="direccion_fiscal", nullable=false, length=500)
    private String direccionFiscal;

    @Column(length=80)
    private String email;

    @Column(name="evaluacion_desempeno", nullable=false, precision=5, scale=2)
    private BigDecimal evaluacionDesempeno;

    @Column(name="evaluacion_homologacion", nullable=false, precision=5, scale=2)
    private BigDecimal evaluacionHomologacion;

    @Column(name="fecha_creacion", nullable=false)
    private Date fechaCreacion;

    @Column(name="fecha_modificacion")
    private Date fechaModificacion;

    @Column(name="ind_black_list", length=1)
    private String indBlackList;

    @Column(name="ind_bloqueado_sap", length=1)
    private String indBloqueadoSap;

    @Column(name="ind_homologado", length=1)
    private String indHomologado;

    @Column(name="ind_proveedor_comunidad", length=1)
    private String indProveedorComunidad;

    @Column(name="ind_sujeto_retencion", length=1)
    private String indSujetoRetencion;

    @Column(length=30)
    private String password;

    @Column(name="razon_social", nullable=false, length=300)
    private String razonSocial;

    @Column(nullable=false, length=16)
    private String ruc;

    @Column(name="TELEFONO", nullable=true, length=30)
    private String telefono;

    @Column(name="tipo_persona", nullable=false, length=1)
    private String tipoPersona;

    @Column(name="usuario_creacion", nullable=false)
    private Integer usuarioCreacion;

    @Column(name="usuario_modificacion")
    private Integer usuarioModificacion;


    //uni-directional many-to-one association to CondicionPago
    @ManyToOne
    @JoinColumn(name="id_condicion_pago", nullable=false)
    private CondicionPago condicionPago;

    //uni-directional many-to-one association to Moneda
    @ManyToOne
    @JoinColumn(name="id_moneda", nullable=false)
    private Moneda moneda;

    //uni-directional many-to-one association to TipoComprobante
    @ManyToOne
    @JoinColumn(name="id_tipo_comprobante", nullable=false)
    private TipoComprobante tipoComprobante;

    //uni-directional many-to-one association to TipoProveedor
    @ManyToOne
    @JoinColumn(name="id_tipo_proveedor", nullable=false)
    private TipoProveedor tipoProveedor;

    //uni-directional many-to-one association to Ubigeo
    @ManyToOne
    @JoinColumn(name="id_distrito")
    private Ubigeo distrito;

    //uni-directional many-to-one association to Ubigeo
    @ManyToOne
    @JoinColumn(name="id_provincia")
    private Ubigeo provincia;

    //uni-directional many-to-one association to Ubigeo
    @ManyToOne
    @JoinColumn(name="id_region")
    private Ubigeo region;

    //uni-directional many-to-one association to Ubigeo
    @ManyToOne
    @JoinColumn(name="id_pais")
    private Ubigeo pais;

    @Column(name="carpeta_id", length=60)
    private String carpetaId;

    @Column(name="hcp_id", length=60)
    private String idHcp;

    @Column(name="activo", length=1)
    private String activo;

    @Column(name="email_retencion", length=80)
    private String emailRetencion;


    @Size(max = 100)
    @Column(name = "territorio_region_amazonia", length = 100)
    private String territorioRegionAmazonia;

    @Size(max = 30)
    @Column(name = "codigo_postal", length = 30)
    private String codigoPostal;

    @Size(max = 30)
    @Column(name = "celular", length = 30)
    private String celular;

    @FixedLength(length = 1)
    @Column(name = "ind_habido_sunat", length = 1)
    private String indHabidoSunat;

    @FixedLength(length = 1)
    @Column(name = "ind_activo_sunat", length = 1)
    private String indActivoSunat;

    @Size(max = 100)
    @Column(name = "fecha_inicio_acti_sunat", length = 100)
    private String fechaInicioActiSunat;

    @FixedLength(length = 1)
    @Column(name = "ind_creacion_unica_vez", length = 1)
    private String indCreacionUnicaVez;

    @FixedLength(length = 1)
    @Column(name = "ind_proveedor_excepcion", length = 1)
    private String indProveedorExcepcion;

    @Size(max = 1000)
    @Column(name = "codigo_sistema_emision_elect", length = 1000)
    private String codigoSistemaEmisionElect;

    @Size(max = 1000)
    @Column(name = "codigo_comprobante_pago", length = 1000)
    private String codigoComprobantePago;

    @Size(max = 1000)
    @Column(name = "codigo_padron", length = 1000)
    private String codigoPadron;

//    @NotEmpty
    @Size(max = 10)
    @Column(name = "codigo_grupo_compra", nullable = true, length = 10)
    private String codigoGrupoCompra;

//    @NotEmpty
    @Size(max = 10)
    @Column(name = "codigo_grupo_tesoreria", nullable = true, length = 10)
    private String codigoGrupoTesoreria;

    @Size(max = 100)
    @Column(name = "operaciones_afectas", length = 100)
    private String operacionesAfectas;

    @FixedLength(length = 1)
    @Column(name = "ind_tipo_venta_bien", length = 1)
    private String indTipoVentaBien;

    @FixedLength(length = 1)
    @Column(name = "ind_tipo_venta_servicio", length = 1)
    private String indTipoVentaServicio;

    @FixedLength(length = 1)
    @Column(name = "ind_tipo_facturacion_elect", length = 1)
    private String indTipoFacturacionElect;

    @FixedLength(length = 1)
    @Column(name = "ind_tipo_facturacion_manual", length = 1)
    private String indTipoFacturacionManual;

    @Size(max = 100)
    @Column(name = "nombre_representante_legal", length = 100)
    private String nombreRepresentanteLegal;

    @Size(max = 100)
    @Column(name = "cargo_representante_legal", length = 100)
    private String cargoRepresentanteLegal;

    @Size(max = 100)
    @Column(name = "email_representante_legal", length = 100)
    private String emailRepresentanteLegal;

    @Size(max = 40)
    @Column(name = "nro_docum_representante_legal", length = 40)
    private String nroDocumRepresentanteLegal;

    @Size(max = 100)
    @Column(name = "nombre_persona_credito_cobranza", length = 100)
    private String nombrePersonaCreditoCobranza;

    @Size(max = 100)
    @Column(name = "cargo_persona_credito_cobranza", length = 100)
    private String cargoPersonaCreditoCobranza;

    @Size(max = 100)
    @Column(name = "email_persona_credito_cobranza", length = 100)
    private String emailPersonaCreditoCobranza;

    @Size(max = 40)
    @Column(name = "nro_docum_persona_credito_cobranza", length = 40)
    private String nroDocumPersonaCreditoCobranza;

    @Size(max = 100)
    @Column(name = "nombre_persona_tesoreria", length = 100)
    private String nombrePersonaTesoreria;

    @Size(max = 100)
    @Column(name = "cargo_persona_tesoreria", length = 100)
    private String cargoPersonaTesoreria;

    @Size(max = 100)
    @Column(name = "email_persona_tesoreria", length = 100)
    private String emailPersonaTesoreria;

    @Size(max = 40)
    @Column(name = "nro_docum_persona_tesoreria", length = 40)
    private String nroDocumPersonaTesoreria;

    @Size(max = 100)
    @Column(name = "nombre_persona_compra", length = 100)
    private String nombrePersonaCompra;

    @Size(max = 100)
    @Column(name = "cargo_persona_compra", length = 100)
    private String cargoPersonaCompra;

    @Size(max = 100)
    @Column(name = "email_persona_compra", length = 100)
    private String emailPersonaCompra;

    @Size(max = 40)
    @Column(name = "nro_docum_persona_compra", length = 40)
    private String nroDocumPersonaCompra;

    @Size(max = 30)
    @Column(name = "celular_persona_compra", length = 30)
    private String celularPersonaCompra;

    @FixedLength(length = 1)
    @Column(name = "ind_aceptacion", length = 1)
    private String indAceptacion;

    @Size(max = 1000)
    @Column(name = "codigo_actividad_economica", length = 1000)
    private String codigoActividadEconomica;

    @Size(max = 10)
    @Column(name = "codigo_profesion_oficio", length = 10)
    private String codigoProfesionOficio;

    @Size(max = 10)
    @Column(name = "codigo_tipo_proveedor_actividad", length = 10)
    private String codigoTipoProveedorActividad;

    // Many to one
    @ManyToOne
    @JoinColumn(name="id_estado_proveedor", nullable=false)
    private EstadoProveedor idEstadoProveedor;

    @Column(name="ind_migrado_sap", length=1)
    private String indMigradoSap;

    @Column(name="tel_mov", length=30)
    private String telefonoMovil;

    @Column(name="NOMBRE_PROVEEDOR_OSE")
    private String nombreProveedorOse;






    public String getIndAgenteRetencion() {
        return indAgenteRetencion;
    }

    public void setIndAgenteRetencion(String indAgenteRetencion) {
        this.indAgenteRetencion = indAgenteRetencion;
    }

    /**JRAMOS UPDATE*/
    @Column(name="IND_AGENTE_RETENCION",length = 1)
    private String indAgenteRetencion;

    @Transient
    private boolean mostrarCuestionarioHomologacionResponder = false;

    public Proveedor() {
    }



    public Integer getIdProveedor() {
        return this.idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getAcreedorCodigoSap() {
        return this.acreedorCodigoSap;
    }

    public void setAcreedorCodigoSap(String acreedorCodigoSap) {
        this.acreedorCodigoSap = acreedorCodigoSap;
    }

    public String getContacto() {
        return this.contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccionFiscal() {
        return this.direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getEvaluacionDesempeno() {
        return evaluacionDesempeno;
    }

    public void setEvaluacionDesempeno(BigDecimal evaluacionDesempeno) {
        this.evaluacionDesempeno = evaluacionDesempeno;
    }

    public BigDecimal getEvaluacionHomologacion() {
        return evaluacionHomologacion;
    }

    public void setEvaluacionHomologacion(BigDecimal evaluacionHomologacion) {
        this.evaluacionHomologacion = evaluacionHomologacion;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getIndBlackList() {
        return this.indBlackList;
    }

    public void setIndBlackList(String indBlackList) {
        this.indBlackList = indBlackList;
    }

    public String getIndBloqueadoSap() {
        return this.indBloqueadoSap;
    }

    public void setIndBloqueadoSap(String indBloqueadoSap) {
        this.indBloqueadoSap = indBloqueadoSap;
    }

    public String getIndHomologado() {
        return this.indHomologado;
    }

    public void setIndHomologado(String indHomologado) {
        this.indHomologado = indHomologado;
    }

    public String getIndProveedorComunidad() {
        return this.indProveedorComunidad;
    }

    public void setIndProveedorComunidad(String indProveedorComunidad) {
        this.indProveedorComunidad = indProveedorComunidad;
    }

    public String getIndSujetoRetencion() {
        return this.indSujetoRetencion;
    }

    public void setIndSujetoRetencion(String indSujetoRetencion) {
        this.indSujetoRetencion = indSujetoRetencion;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return this.ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoPersona() {
        return this.tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
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

    public CondicionPago getCondicionPago() {
        return this.condicionPago;
    }

    public void setCondicionPago(CondicionPago condicionPago) {
        this.condicionPago = condicionPago;
    }

    public Moneda getMoneda() {
        return this.moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public TipoComprobante getTipoComprobante() {
        return this.tipoComprobante;
    }

    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public TipoProveedor getTipoProveedor() {
        return this.tipoProveedor;
    }

    public void setTipoProveedor(TipoProveedor tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    public Ubigeo getDistrito() {
        return distrito;
    }

    public void setDistrito(Ubigeo distrito) {
        this.distrito = distrito;
    }

    public Ubigeo getProvincia() {
        return provincia;
    }

    public void setProvincia(Ubigeo provincia) {
        this.provincia = provincia;
    }

    public Ubigeo getRegion() {
        return region;
    }

    public void setRegion(Ubigeo region) {
        this.region = region;
    }

    public Ubigeo getPais() {
        return pais;
    }

    public void setPais(Ubigeo pais) {
        this.pais = pais;
    }

    public String getCarpetaId() {
        return carpetaId;
    }

    public void setCarpetaId(String carpetaId) {
        this.carpetaId = carpetaId;
    }

    public String getIdHcp() {
        return idHcp;
    }

    public void setIdHcp(String idHcp) {
        this.idHcp = idHcp;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getEmailRetencion() {
        return emailRetencion;
    }

    public void setEmailRetencion(String emailRetencion) {
        this.emailRetencion = emailRetencion;
    }

    public boolean isMostrarCuestionarioHomologacionResponder() {
        return mostrarCuestionarioHomologacionResponder;
    }

    public void setMostrarCuestionarioHomologacionResponder(boolean mostrarCuestionarioHomologacionResponder) {
        this.mostrarCuestionarioHomologacionResponder = mostrarCuestionarioHomologacionResponder;
    }


    // -- [territorioRegionAmazonia] ------------------------


    public String getTerritorioRegionAmazonia() {
        return territorioRegionAmazonia;
    }

    public void setTerritorioRegionAmazonia(String territorioRegionAmazonia) {
        this.territorioRegionAmazonia = territorioRegionAmazonia;
    }

    public Proveedor territorioRegionAmazonia(String territorioRegionAmazonia) {
        setTerritorioRegionAmazonia(territorioRegionAmazonia);
        return this;
    }

    // -- [codigoPostal] ------------------------

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Proveedor codigoPostal(String codigoPostal) {
        setCodigoPostal(codigoPostal);
        return this;
    }
    // -- [celular] ------------------------

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Proveedor celular(String celular) {
        setCelular(celular);
        return this;
    }
    // -- [indHabidoSunat] ------------------------

    public String getIndHabidoSunat() {
        return indHabidoSunat;
    }

    public void setIndHabidoSunat(String indHabidoSunat) {
        this.indHabidoSunat = indHabidoSunat;
    }

    public Proveedor indHabidoSunat(String indHabidoSunat) {
        setIndHabidoSunat(indHabidoSunat);
        return this;
    }
    // -- [indActivoSunat] ------------------------

    public String getIndActivoSunat() {
        return indActivoSunat;
    }

    public void setIndActivoSunat(String indActivoSunat) {
        this.indActivoSunat = indActivoSunat;
    }

    public Proveedor indActivoSunat(String indActivoSunat) {
        setIndActivoSunat(indActivoSunat);
        return this;
    }
    // -- [fechaInicioActiSunat] ------------------------

    public String getFechaInicioActiSunat() {
        return fechaInicioActiSunat;
    }

    public void setFechaInicioActiSunat(String fechaInicioActiSunat) {
        this.fechaInicioActiSunat = fechaInicioActiSunat;
    }

    public Proveedor fechaInicioActiSunat(String fechaInicioActiSunat) {
        setFechaInicioActiSunat(fechaInicioActiSunat);
        return this;
    }
    // -- [indCreacionUnicaVez] ------------------------

    public String getIndCreacionUnicaVez() {
        return indCreacionUnicaVez;
    }

    public void setIndCreacionUnicaVez(String indCreacionUnicaVez) {
        this.indCreacionUnicaVez = indCreacionUnicaVez;
    }

    public Proveedor indCreacionUnicaVez(String indCreacionUnicaVez) {
        setIndCreacionUnicaVez(indCreacionUnicaVez);
        return this;
    }
    // -- [indProveedorExcepcion] ------------------------

    public String getIndProveedorExcepcion() {
        return indProveedorExcepcion;
    }

    public void setIndProveedorExcepcion(String indProveedorExcepcion) {
        this.indProveedorExcepcion = indProveedorExcepcion;
    }

    public Proveedor indProveedorExcepcion(String indProveedorExcepcion) {
        setIndProveedorExcepcion(indProveedorExcepcion);
        return this;
    }
    // -- [codigoSistemaEmisionElect] ------------------------

    public String getCodigoSistemaEmisionElect() {
        return codigoSistemaEmisionElect;
    }

    public void setCodigoSistemaEmisionElect(String codigoSistemaEmisionElect) {
        this.codigoSistemaEmisionElect = codigoSistemaEmisionElect;
    }

    public Proveedor codigoSistemaEmisionElect(String codigoSistemaEmisionElect) {
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

    public Proveedor codigoComprobantePago(String codigoComprobantePago) {
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

    public Proveedor codigoPadron(String codigoPadron) {
        setCodigoPadron(codigoPadron);
        return this;
    }
    // -- [codigoGrupoCompra] ------------------------

    public String getCodigoGrupoCompra() {
        return codigoGrupoCompra;
    }

    public void setCodigoGrupoCompra(String codigoGrupoCompra) {
        this.codigoGrupoCompra = codigoGrupoCompra;
    }

    public Proveedor codigoGrupoCompra(String codigoGrupoCompra) {
        setCodigoGrupoCompra(codigoGrupoCompra);
        return this;
    }
    // -- [codigoGrupoTesoreria] ------------------------

    public String getCodigoGrupoTesoreria() {
        return codigoGrupoTesoreria;
    }

    public void setCodigoGrupoTesoreria(String codigoGrupoTesoreria) {
        this.codigoGrupoTesoreria = codigoGrupoTesoreria;
    }

    public Proveedor codigoGrupoTesoreria(String codigoGrupoTesoreria) {
        setCodigoGrupoTesoreria(codigoGrupoTesoreria);
        return this;
    }
    // -- [operacionesAfectas] ------------------------

    public String getOperacionesAfectas() {
        return operacionesAfectas;
    }

    public void setOperacionesAfectas(String operacionesAfectas) {
        this.operacionesAfectas = operacionesAfectas;
    }

    public Proveedor operacionesAfectas(String operacionesAfectas) {
        setOperacionesAfectas(operacionesAfectas);
        return this;
    }
    // -- [indTipoVentaBien] ------------------------

    public String getIndTipoVentaBien() {
        return indTipoVentaBien;
    }

    public void setIndTipoVentaBien(String indTipoVentaBien) {
        this.indTipoVentaBien = indTipoVentaBien;
    }

    public Proveedor indTipoVentaBien(String indTipoVentaBien) {
        setIndTipoVentaBien(indTipoVentaBien);
        return this;
    }
    // -- [indTipoVentaServicio] ------------------------

    public String getIndTipoVentaServicio() {
        return indTipoVentaServicio;
    }

    public void setIndTipoVentaServicio(String indTipoVentaServicio) {
        this.indTipoVentaServicio = indTipoVentaServicio;
    }

    public Proveedor indTipoVentaServicio(String indTipoVentaServicio) {
        setIndTipoVentaServicio(indTipoVentaServicio);
        return this;
    }
    // -- [indTipoFacturacionElect] ------------------------

    public String getIndTipoFacturacionElect() {
        return indTipoFacturacionElect;
    }

    public void setIndTipoFacturacionElect(String indTipoFacturacionElect) {
        this.indTipoFacturacionElect = indTipoFacturacionElect;
    }

    public Proveedor indTipoFacturacionElect(String indTipoFacturacionElect) {
        setIndTipoFacturacionElect(indTipoFacturacionElect);
        return this;
    }
    // -- [indTipoFacturacionManual] ------------------------

    public String getIndTipoFacturacionManual() {
        return indTipoFacturacionManual;
    }

    public void setIndTipoFacturacionManual(String indTipoFacturacionManual) {
        this.indTipoFacturacionManual = indTipoFacturacionManual;
    }

    public Proveedor indTipoFacturacionManual(String indTipoFacturacionManual) {
        setIndTipoFacturacionManual(indTipoFacturacionManual);
        return this;
    }
    // -- [nombreRepresentanteLegal] ------------------------

    public String getNombreRepresentanteLegal() {
        return nombreRepresentanteLegal;
    }

    public void setNombreRepresentanteLegal(String nombreRepresentanteLegal) {
        this.nombreRepresentanteLegal = nombreRepresentanteLegal;
    }

    public Proveedor nombreRepresentanteLegal(String nombreRepresentanteLegal) {
        setNombreRepresentanteLegal(nombreRepresentanteLegal);
        return this;
    }
    // -- [cargoRepresentanteLegal] ------------------------

    public String getCargoRepresentanteLegal() {
        return cargoRepresentanteLegal;
    }

    public void setCargoRepresentanteLegal(String cargoRepresentanteLegal) {
        this.cargoRepresentanteLegal = cargoRepresentanteLegal;
    }

    public Proveedor cargoRepresentanteLegal(String cargoRepresentanteLegal) {
        setCargoRepresentanteLegal(cargoRepresentanteLegal);
        return this;
    }
    // -- [emailRepresentanteLegal] ------------------------

    public String getEmailRepresentanteLegal() {
        return emailRepresentanteLegal;
    }

    public void setEmailRepresentanteLegal(String emailRepresentanteLegal) {
        this.emailRepresentanteLegal = emailRepresentanteLegal;
    }

    public Proveedor emailRepresentanteLegal(String emailRepresentanteLegal) {
        setEmailRepresentanteLegal(emailRepresentanteLegal);
        return this;
    }
    // -- [nroDocumRepresentanteLegal] ------------------------

    public String getNroDocumRepresentanteLegal() {
        return nroDocumRepresentanteLegal;
    }

    public void setNroDocumRepresentanteLegal(String nroDocumRepresentanteLegal) {
        this.nroDocumRepresentanteLegal = nroDocumRepresentanteLegal;
    }

    public Proveedor nroDocumRepresentanteLegal(String nroDocumRepresentanteLegal) {
        setNroDocumRepresentanteLegal(nroDocumRepresentanteLegal);
        return this;
    }
    // -- [nombrePersonaCreditoCobranza] ------------------------

    public String getNombrePersonaCreditoCobranza() {
        return nombrePersonaCreditoCobranza;
    }

    public void setNombrePersonaCreditoCobranza(String nombrePersonaCreditoCobranza) {
        this.nombrePersonaCreditoCobranza = nombrePersonaCreditoCobranza;
    }

    public Proveedor nombrePersonaCreditoCobranza(String nombrePersonaCreditoCobranza) {
        setNombrePersonaCreditoCobranza(nombrePersonaCreditoCobranza);
        return this;
    }
    // -- [cargoPersonaCreditoCobranza] ------------------------

    public String getCargoPersonaCreditoCobranza() {
        return cargoPersonaCreditoCobranza;
    }

    public void setCargoPersonaCreditoCobranza(String cargoPersonaCreditoCobranza) {
        this.cargoPersonaCreditoCobranza = cargoPersonaCreditoCobranza;
    }

    public Proveedor cargoPersonaCreditoCobranza(String cargoPersonaCreditoCobranza) {
        setCargoPersonaCreditoCobranza(cargoPersonaCreditoCobranza);
        return this;
    }
    // -- [emailPersonaCreditoCobranza] ------------------------

    public String getEmailPersonaCreditoCobranza() {
        return emailPersonaCreditoCobranza;
    }

    public void setEmailPersonaCreditoCobranza(String emailPersonaCreditoCobranza) {
        this.emailPersonaCreditoCobranza = emailPersonaCreditoCobranza;
    }

    public Proveedor emailPersonaCreditoCobranza(String emailPersonaCreditoCobranza) {
        setEmailPersonaCreditoCobranza(emailPersonaCreditoCobranza);
        return this;
    }
    // -- [nroDocumPersonaCreditoCobranza] ------------------------

    public String getNroDocumPersonaCreditoCobranza() {
        return nroDocumPersonaCreditoCobranza;
    }

    public void setNroDocumPersonaCreditoCobranza(String nroDocumPersonaCreditoCobranza) {
        this.nroDocumPersonaCreditoCobranza = nroDocumPersonaCreditoCobranza;
    }

    public Proveedor nroDocumPersonaCreditoCobranza(String nroDocumPersonaCreditoCobranza) {
        setNroDocumPersonaCreditoCobranza(nroDocumPersonaCreditoCobranza);
        return this;
    }
    // -- [nombrePersonaTesoreria] ------------------------

    public String getNombrePersonaTesoreria() {
        return nombrePersonaTesoreria;
    }

    public void setNombrePersonaTesoreria(String nombrePersonaTesoreria) {
        this.nombrePersonaTesoreria = nombrePersonaTesoreria;
    }

    public Proveedor nombrePersonaTesoreria(String nombrePersonaTesoreria) {
        setNombrePersonaTesoreria(nombrePersonaTesoreria);
        return this;
    }
    // -- [cargoPersonaTesoreria] ------------------------

    public String getCargoPersonaTesoreria() {
        return cargoPersonaTesoreria;
    }

    public void setCargoPersonaTesoreria(String cargoPersonaTesoreria) {
        this.cargoPersonaTesoreria = cargoPersonaTesoreria;
    }

    public Proveedor cargoPersonaTesoreria(String cargoPersonaTesoreria) {
        setCargoPersonaTesoreria(cargoPersonaTesoreria);
        return this;
    }
    // -- [emailPersonaTesoreria] ------------------------

    public String getEmailPersonaTesoreria() {
        return emailPersonaTesoreria;
    }

    public void setEmailPersonaTesoreria(String emailPersonaTesoreria) {
        this.emailPersonaTesoreria = emailPersonaTesoreria;
    }

    public Proveedor emailPersonaTesoreria(String emailPersonaTesoreria) {
        setEmailPersonaTesoreria(emailPersonaTesoreria);
        return this;
    }
    // -- [nroDocumPersonaTesoreria] ------------------------

    public String getNroDocumPersonaTesoreria() {
        return nroDocumPersonaTesoreria;
    }

    public void setNroDocumPersonaTesoreria(String nroDocumPersonaTesoreria) {
        this.nroDocumPersonaTesoreria = nroDocumPersonaTesoreria;
    }

    public Proveedor nroDocumPersonaTesoreria(String nroDocumPersonaTesoreria) {
        setNroDocumPersonaTesoreria(nroDocumPersonaTesoreria);
        return this;
    }
    // -- [nombrePersonaCompra] ------------------------

    public String getNombrePersonaCompra() {
        return nombrePersonaCompra;
    }

    public void setNombrePersonaCompra(String nombrePersonaCompra) {
        this.nombrePersonaCompra = nombrePersonaCompra;
    }

    public Proveedor nombrePersonaCompra(String nombrePersonaCompra) {
        setNombrePersonaCompra(nombrePersonaCompra);
        return this;
    }
    // -- [cargoPersonaCompra] ------------------------

    public String getCargoPersonaCompra() {
        return cargoPersonaCompra;
    }

    public void setCargoPersonaCompra(String cargoPersonaCompra) {
        this.cargoPersonaCompra = cargoPersonaCompra;
    }

    public Proveedor cargoPersonaCompra(String cargoPersonaCompra) {
        setCargoPersonaCompra(cargoPersonaCompra);
        return this;
    }
    // -- [emailPersonaCompra] ------------------------

    public String getEmailPersonaCompra() {
        return emailPersonaCompra;
    }

    public void setEmailPersonaCompra(String emailPersonaCompra) {
        this.emailPersonaCompra = emailPersonaCompra;
    }

    public Proveedor emailPersonaCompra(String emailPersonaCompra) {
        setEmailPersonaCompra(emailPersonaCompra);
        return this;
    }
    // -- [nroDocumPersonaCompra] ------------------------


    public String getNroDocumPersonaCompra() {
        return nroDocumPersonaCompra;
    }

    public void setNroDocumPersonaCompra(String nroDocumPersonaCompra) {
        this.nroDocumPersonaCompra = nroDocumPersonaCompra;
    }

    public Proveedor nroDocumPersonaCompra(String nroDocumPersonaCompra) {
        setNroDocumPersonaCompra(nroDocumPersonaCompra);
        return this;
    }

    // -- [celularPersonaCompra] ------------------------

    public String getCelularPersonaCompra() {
        return celularPersonaCompra;
    }

    public void setCelularPersonaCompra(String celularPersonaCompra) {
        this.celularPersonaCompra = celularPersonaCompra;
    }

    public Proveedor celularPersonaCompra(String celularPersonaCompra) {
        setCelularPersonaCompra(celularPersonaCompra);
        return this;
    }

    // -- [indAceptacion] ------------------------


    public String getIndAceptacion() {
        return indAceptacion;
    }

    public void setIndAceptacion(String indAceptacion) {
        this.indAceptacion = indAceptacion;
    }

    public Proveedor indAceptacion(String indAceptacion) {
        setIndAceptacion(indAceptacion);
        return this;
    }

    // -- [codigoActividadEconomica] ------------------------

    public String getCodigoActividadEconomica() {
        return codigoActividadEconomica;
    }

    public void setCodigoActividadEconomica(String codigoActividadEconomica) {
        this.codigoActividadEconomica = codigoActividadEconomica;
    }

    public Proveedor codigoActividadEconomica(String codigoActividadEconomica) {
        setCodigoActividadEconomica(codigoActividadEconomica);
        return this;
    }
    // -- [codigoProfesionOficio] ------------------------

    public String getCodigoProfesionOficio() {
        return codigoProfesionOficio;
    }

    public void setCodigoProfesionOficio(String codigoProfesionOficio) {
        this.codigoProfesionOficio = codigoProfesionOficio;
    }

    public Proveedor codigoProfesionOficio(String codigoProfesionOficio) {
        setCodigoProfesionOficio(codigoProfesionOficio);
        return this;
    }
    // -- [codigoTipoProveedorActividad] ------------------------
    //Inicio telefono Movil

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Proveedor telefonoMovil(String telefonoMovil) {
        setTelefonoMovil(telefonoMovil);
        return this;
    }

    //Fin Telefono Movil
    public String getCodigoTipoProveedorActividad() {
        return codigoTipoProveedorActividad;
    }

    public void setCodigoTipoProveedorActividad(String codigoTipoProveedorActividad) {
        this.codigoTipoProveedorActividad = codigoTipoProveedorActividad;
    }

    public Proveedor codigoTipoProveedorActividad(String codigoTipoProveedorActividad) {
        setCodigoTipoProveedorActividad(codigoTipoProveedorActividad);
        return this;
    }


    public EstadoProveedor getIdEstadoProveedor() {
        return idEstadoProveedor;
    }

    public void setIdEstadoProveedor(EstadoProveedor idEstadoProveedor) {
        this.idEstadoProveedor = idEstadoProveedor;
    }

    public String getIndMigradoSap() {
        return indMigradoSap;
    }

    public void setIndMigradoSap(String indMigradoSap) {
        this.indMigradoSap = indMigradoSap;
    }

    public String getNombreProveedorOse() {
        return nombreProveedorOse;
    }

    public void setNombreProveedorOse(String nombreProveedorOse) {
        this.nombreProveedorOse = nombreProveedorOse;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "idProveedor=" + idProveedor +
                ", acreedorCodigoSap='" + acreedorCodigoSap + '\'' +
                ", contacto='" + contacto + '\'' +
                ", direccionFiscal='" + direccionFiscal + '\'' +
                ", email='" + email + '\'' +
                ", evaluacionDesempeno=" + evaluacionDesempeno +
                ", evaluacionHomologacion=" + evaluacionHomologacion +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", indBlackList='" + indBlackList + '\'' +
                ", indBloqueadoSap='" + indBloqueadoSap + '\'' +
                ", indHomologado='" + indHomologado + '\'' +
                ", indProveedorComunidad='" + indProveedorComunidad + '\'' +
                ", indSujetoRetencion='" + indSujetoRetencion + '\'' +
                ", password='" + password + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", ruc='" + ruc + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipoPersona='" + tipoPersona + '\'' +
                ", usuarioCreacion=" + usuarioCreacion +
                ", usuarioModificacion=" + usuarioModificacion +
                ", condicionPago=" + condicionPago +
                ", moneda=" + moneda +
                ", tipoComprobante=" + tipoComprobante +
                ", tipoProveedor=" + tipoProveedor +
                ", distrito=" + distrito +
                ", provincia=" + provincia +
                ", region=" + region +
                ", pais=" + pais +
                ", carpetaId='" + carpetaId + '\'' +
                ", idHcp='" + idHcp + '\'' +
                ", activo='" + activo + '\'' +
                ", emailRetencion='" + emailRetencion + '\'' +
                ", territorioRegionAmazonia='" + territorioRegionAmazonia + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", celular='" + celular + '\'' +
                ", indHabidoSunat='" + indHabidoSunat + '\'' +
                ", indActivoSunat='" + indActivoSunat + '\'' +
                ", fechaInicioActiSunat='" + fechaInicioActiSunat + '\'' +
                ", indCreacionUnicaVez='" + indCreacionUnicaVez + '\'' +
                ", indProveedorExcepcion='" + indProveedorExcepcion + '\'' +
                ", codigoSistemaEmisionElect='" + codigoSistemaEmisionElect + '\'' +
                ", codigoComprobantePago='" + codigoComprobantePago + '\'' +
                ", codigoPadron='" + codigoPadron + '\'' +
                ", codigoGrupoCompra='" + codigoGrupoCompra + '\'' +
                ", codigoGrupoTesoreria='" + codigoGrupoTesoreria + '\'' +
                ", operacionesAfectas='" + operacionesAfectas + '\'' +
                ", indTipoVentaBien='" + indTipoVentaBien + '\'' +
                ", indTipoVentaServicio='" + indTipoVentaServicio + '\'' +
                ", indTipoFacturacionElect='" + indTipoFacturacionElect + '\'' +
                ", indTipoFacturacionManual='" + indTipoFacturacionManual + '\'' +
                ", nombreRepresentanteLegal='" + nombreRepresentanteLegal + '\'' +
                ", cargoRepresentanteLegal='" + cargoRepresentanteLegal + '\'' +
                ", emailRepresentanteLegal='" + emailRepresentanteLegal + '\'' +
                ", nroDocumRepresentanteLegal='" + nroDocumRepresentanteLegal + '\'' +
                ", nombrePersonaCreditoCobranza='" + nombrePersonaCreditoCobranza + '\'' +
                ", cargoPersonaCreditoCobranza='" + cargoPersonaCreditoCobranza + '\'' +
                ", emailPersonaCreditoCobranza='" + emailPersonaCreditoCobranza + '\'' +
                ", nroDocumPersonaCreditoCobranza='" + nroDocumPersonaCreditoCobranza + '\'' +
                ", nombrePersonaTesoreria='" + nombrePersonaTesoreria + '\'' +
                ", cargoPersonaTesoreria='" + cargoPersonaTesoreria + '\'' +
                ", emailPersonaTesoreria='" + emailPersonaTesoreria + '\'' +
                ", nroDocumPersonaTesoreria='" + nroDocumPersonaTesoreria + '\'' +
                ", nombrePersonaCompra='" + nombrePersonaCompra + '\'' +
                ", cargoPersonaCompra='" + cargoPersonaCompra + '\'' +
                ", emailPersonaCompra='" + emailPersonaCompra + '\'' +
                ", nroDocumPersonaCompra='" + nroDocumPersonaCompra + '\'' +
                ", celularPersonaCompra='" + celularPersonaCompra + '\'' +
                ", indAceptacion='" + indAceptacion + '\'' +
                ", codigoActividadEconomica='" + codigoActividadEconomica + '\'' +
                ", codigoProfesionOficio='" + codigoProfesionOficio + '\'' +
                ", codigoTipoProveedorActividad='" + codigoTipoProveedorActividad + '\'' +
                ", idEstadoProveedor=" + idEstadoProveedor +
                ", indMigradoSap='" + indMigradoSap + '\'' +
                ", telefonoMovil='" + telefonoMovil + '\'' +
                ", nombreProveedorOse='" + nombreProveedorOse + '\'' +
                ", indAgenteRetencion='" + indAgenteRetencion + '\'' +
                ", mostrarCuestionarioHomologacionResponder=" + mostrarCuestionarioHomologacionResponder +
                '}';
    }
}