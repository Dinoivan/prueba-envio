package com.incloud.hcp.dto;

import com.incloud.hcp.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrador on 30/08/2017.
 */
public class ProveedorDto {
    private Integer idProveedor;
    private String idHcp;
    private String acreedorCodigoSap;
    private String contacto;
    private String direccionFiscal;
    private String email;
    private String emailRetencion;
    private BigDecimal evaluacionDesempeno;
    private BigDecimal evaluacionHomologacion;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private Boolean indBlackList;
    private Boolean indBloqueadoSap;
    private Boolean indHomologado;
    private Boolean indMigradoSap;
    private Boolean indProveedorComunidad;
    private String razonSocial;
    private String ruc;
    private String telefono;
    private String tipoPersona;
    private Integer idCondicionPago;
    private Integer idMoneda;
    private Integer idTipoComprobante;
    private Integer idTipoProveedor;
    private Integer codigoPais;
    private Integer codigoRegion;
    private Integer codigoProvincia;
    private Integer codigoDistrito;
    private boolean activo;
    private boolean habido;
    private String nombreProveedorOse;
    private boolean indAgenteRetencion;
    private List<CanalContactoDto> canales;

    public boolean getIndAgenteRetencion() {
        return indAgenteRetencion;
    }

    public void setIndAgenteRetencion(boolean indAgenteRetencion) {
        this.indAgenteRetencion = indAgenteRetencion;
    }

    private List<CuentaBancariaDto> cuentasBanco;



    private List<LineaComercialDto> lineasComercial;
    private List<ProductoDto> productos;
    private List<EvaluacionDesempenioDto> evalucionesDesempenio;
    private List<SolicitudBlackListDto> noConformes;
    private List<ProveedorHomologacionDto> respuestasHomologacion;
    private List<ProveedorCatalogoDto> catalogos;
    private List<ProveedorAdjuntoSunatDto> adjuntosSunat;
    private List<SectorTrabajoDto> sectorTrabajos;


    private String territorioRegionAmazonia;
    private String codigoPostal;
    private String celular;
    private String indHabidoSunat;
    private String indActivoSunat;
    private String fechaInicioActiSunat;
    private String indCreacionUnicaVez;
    private String indProveedorExcepcion;
    private String codigoSistemaEmisionElect;
    private String codigoComprobantePago;
    private String codigoPadron;
    private String codigoGrupoCompra;
    private String codigoGrupoTesoreria;
    private String operacionesAfectas;
    private String indTipoVentaBien;
    private String indTipoVentaServicio;
    private String indTipoFacturacionElect;
    private String indTipoFacturacionManual;
    private String nombreRepresentanteLegal;
    private String cargoRepresentanteLegal;
    private String emailRepresentanteLegal;
    private String nroDocumRepresentanteLegal;
    private String nombrePersonaCreditoCobranza;
    private String cargoPersonaCreditoCobranza;
    private String emailPersonaCreditoCobranza;
    private String nroDocumPersonaCreditoCobranza;
    private String nombrePersonaTesoreria;
    private String cargoPersonaTesoreria;
    private String emailPersonaTesoreria;
    private String nroDocumPersonaTesoreria;
    private String nombrePersonaCompra;
    private String cargoPersonaCompra;
    private String emailPersonaCompra;
    private String celularPersonaCompra;
    private String nroDocumPersonaCompra;
    private String indAceptacion;

    private String codigoActividadEconomica;
    private String codigoProfesionOficio;
    private String codigoTipoProveedorActividad;

    private boolean creacionUnicaVez;
    private boolean proveedorExcepcion;
    private boolean tipoVentaBien;
    private boolean tipoVentaServicio;
    private boolean tipoFacturacionElect;
    private boolean tipoFacturacionManual;

    private boolean aceptacion;

    // Many to one
    private EstadoProveedor idEstadoProveedor;


    private List<ProveedorCliente> principales;
    private List<ProveedorInstalacion> instalaciones;
    private List<ProveedorPermiso> permisos;
    private List<ProveedorFuncionario> adicionales;
    private List<ProveedorPreguntaInformacion> preguntaInformacion;


    public ProveedorDto() {
        this.canales = new ArrayList<>();
        this.cuentasBanco = new ArrayList<>();
        this.lineasComercial = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.evalucionesDesempenio = new ArrayList<>();
        this.noConformes = new ArrayList<>();
        this.respuestasHomologacion = new ArrayList<>();
        this.catalogos = new ArrayList<>();
        this.sectorTrabajos = new ArrayList<>();
        this.adjuntosSunat= new ArrayList<>();
        this.principales = new ArrayList<>();
        this.instalaciones = new ArrayList<>();
        this.permisos = new ArrayList<>();
        this.adicionales = new ArrayList<>();
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getIdHcp() {
        return idHcp;
    }

    public void setIdHcp(String idHcp) {
        this.idHcp = idHcp;
    }

    public String getAcreedorCodigoSap() {
        return acreedorCodigoSap;
    }

    public void setAcreedorCodigoSap(String acreedorCodigoSap) {
        this.acreedorCodigoSap = acreedorCodigoSap;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public String getEmail() {
        return email;
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
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Boolean getIndBlackList() {
        return indBlackList;
    }

    public void setIndBlackList(Boolean indBlackList) {
        this.indBlackList = indBlackList;
    }

    public Boolean getIndBloqueadoSap() {
        return indBloqueadoSap;
    }

    public void setIndBloqueadoSap(Boolean indBloqueadoSap) {
        this.indBloqueadoSap = indBloqueadoSap;
    }

    public Boolean getIndHomologado() {
        return indHomologado;
    }

    public void setIndHomologado(Boolean indHomologado) {
        this.indHomologado = indHomologado;
    }

    public Boolean getIndProveedorComunidad() {
        return indProveedorComunidad;
    }

    public void setIndProveedorComunidad(Boolean indProveedorComunidad) {
        this.indProveedorComunidad = indProveedorComunidad;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Integer getIdCondicionPago() {
        return idCondicionPago;
    }

    public void setIdCondicionPago(Integer idCondicionPago) {
        this.idCondicionPago = idCondicionPago;
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Integer getIdTipoComprobante() {
        return idTipoComprobante;
    }

    public void setIdTipoComprobante(Integer idTipoComprobante) {
        this.idTipoComprobante = idTipoComprobante;
    }

    public Integer getIdTipoProveedor() {
        return idTipoProveedor;
    }

    public void setIdTipoProveedor(Integer idTipoProveedor) {
        this.idTipoProveedor = idTipoProveedor;
    }

    public Integer getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Integer codigoPais) {
        this.codigoPais = codigoPais;
    }

    public Integer getCodigoRegion() {
        return codigoRegion;
    }

    public void setCodigoRegion(Integer codigoRegion) {
        this.codigoRegion = codigoRegion;
    }

    public Integer getCodigoProvincia() {
        return codigoProvincia;
    }

    public void setCodigoProvincia(Integer codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public Integer getCodigoDistrito() {
        return codigoDistrito;
    }

    public void setCodigoDistrito(Integer codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public String getNombreProveedorOse() {
        return nombreProveedorOse;
    }

    public void setNombreProveedorOse(String nombreProveedorOse) {
        this.nombreProveedorOse = nombreProveedorOse;
    }

    public List<CuentaBancariaDto> getCuentasBanco() {
        return cuentasBanco;
    }

    public void setCuentasBanco(List<CuentaBancariaDto> cuentasBanco) {
        this.cuentasBanco = cuentasBanco;
    }

    public List<LineaComercialDto> getLineasComercial() {
        return lineasComercial;
    }

    public void setLineasComercial(List<LineaComercialDto> lineasComercial) {
        this.lineasComercial = lineasComercial;
    }

    public List<ProductoDto> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDto> productos) {
        this.productos = productos;
    }

    public List<EvaluacionDesempenioDto> getEvalucionesDesempenio() {
        return evalucionesDesempenio;
    }

    public void setEvalucionesDesempenio(List<EvaluacionDesempenioDto> evalucionesDesempenio) {
        this.evalucionesDesempenio = evalucionesDesempenio;
    }

    public List<SolicitudBlackListDto> getNoConformes() {
        return noConformes;
    }

    public void setNoConformes(List<SolicitudBlackListDto> noConformes) {
        this.noConformes = noConformes;
    }

    public List<ProveedorHomologacionDto> getRespuestasHomologacion() {
        return respuestasHomologacion;
    }

    public void setRespuestasHomologacion(List<ProveedorHomologacionDto> respuestasHomologacion) {
        this.respuestasHomologacion = respuestasHomologacion;
    }

    public List<ProveedorCatalogoDto> getCatalogos() {
        return catalogos;
    }

    public void setCatalogos(List<ProveedorCatalogoDto> catalogos) {
        this.catalogos = catalogos;
    }

    public List<ProveedorAdjuntoSunatDto> getAdjuntosSunat() {
        return adjuntosSunat;
    }

    public void setAdjuntosSunat(List<ProveedorAdjuntoSunatDto> adjuntosSunat) {
        this.adjuntosSunat = adjuntosSunat;
    }

    public void addCuentaBanco(CuentaBancariaDto cuentaBancariaDto) {
        this.cuentasBanco.add(cuentaBancariaDto);
    }

    public void addLineaComercial(LineaComercialDto lineaComercialDto) {
        this.lineasComercial.add(lineaComercialDto);
    }

    public void addProducto(ProductoDto productoDto) {
        this.productos.add(productoDto);
    }

    public void addEvaluacionDesempenio(EvaluacionDesempenioDto evaluacionDto) {
        this.evalucionesDesempenio.add(evaluacionDto);
    }

    public void addNoConforme(SolicitudBlackListDto solicitudDto) {
        this.noConformes.add(solicitudDto);
    }

    public void addRespuestaHomologacion(ProveedorHomologacionDto respuesta) {
        this.respuestasHomologacion.add(respuesta);
    }

    public void addCatalogo(ProveedorCatalogoDto catalogoDto){
        this.catalogos.add(catalogoDto);
    }

    public void addAdjuntoSunat(ProveedorAdjuntoSunatDto proveedorAdjuntoSunat){
        this.adjuntosSunat.add(proveedorAdjuntoSunat);
    }

    public void addCanalContacto(CanalContactoDto canalContactoDto){
        this.canales.add(canalContactoDto);
    }

    public boolean getActivo() {
        return this.activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<CanalContactoDto> getCanales() {
        return canales;
    }

    public void setCanales(List<CanalContactoDto> canales) {
        this.canales = canales;
    }

    public String getEmailRetencion() {
        return emailRetencion;
    }

    public void setEmailRetencion(String emailRetencion) {
        this.emailRetencion = emailRetencion;
    }

    public List<SectorTrabajoDto> getSectorTrabajos() {
        return sectorTrabajos;
    }

    public void setSectorTrabajos(List<SectorTrabajoDto> sectorTrabajos) {
        this.sectorTrabajos = sectorTrabajos;
    }


    public boolean isActivo() {
        return activo;
    }

    public String getTerritorioRegionAmazonia() {
        return territorioRegionAmazonia;
    }

    public void setTerritorioRegionAmazonia(String territorioRegionAmazonia) {
        this.territorioRegionAmazonia = territorioRegionAmazonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getIndHabidoSunat() {
        return indHabidoSunat;
    }

    public void setIndHabidoSunat(String indHabidoSunat) {
        this.indHabidoSunat = indHabidoSunat;
    }

    public String getIndActivoSunat() {
        return indActivoSunat;
    }

    public void setIndActivoSunat(String indActivoSunat) {
        this.indActivoSunat = indActivoSunat;
    }

    public String getFechaInicioActiSunat() {
        return fechaInicioActiSunat;
    }

    public void setFechaInicioActiSunat(String fechaInicioActiSunat) {
        this.fechaInicioActiSunat = fechaInicioActiSunat;
    }

    public String getIndCreacionUnicaVez() {
        return indCreacionUnicaVez;
    }

    public void setIndCreacionUnicaVez(String indCreacionUnicaVez) {
        this.indCreacionUnicaVez = indCreacionUnicaVez;
    }

    public String getIndProveedorExcepcion() {
        return indProveedorExcepcion;
    }

    public void setIndProveedorExcepcion(String indProveedorExcepcion) {
        this.indProveedorExcepcion = indProveedorExcepcion;
    }

    public String getCodigoSistemaEmisionElect() {
        return codigoSistemaEmisionElect;
    }

    public void setCodigoSistemaEmisionElect(String codigoSistemaEmisionElect) {
        this.codigoSistemaEmisionElect = codigoSistemaEmisionElect;
    }

    public String getCodigoComprobantePago() {
        return codigoComprobantePago;
    }

    public void setCodigoComprobantePago(String codigoComprobantePago) {
        this.codigoComprobantePago = codigoComprobantePago;
    }

    public String getCodigoPadron() {
        return codigoPadron;
    }

    public void setCodigoPadron(String codigoPadron) {
        this.codigoPadron = codigoPadron;
    }

    public String getCodigoGrupoCompra() {
        return codigoGrupoCompra;
    }

    public void setCodigoGrupoCompra(String codigoGrupoCompra) {
        this.codigoGrupoCompra = codigoGrupoCompra;
    }

    public String getCodigoGrupoTesoreria() {
        return codigoGrupoTesoreria;
    }

    public void setCodigoGrupoTesoreria(String codigoGrupoTesoreria) {
        this.codigoGrupoTesoreria = codigoGrupoTesoreria;
    }

    public String getOperacionesAfectas() {
        return operacionesAfectas;
    }

    public void setOperacionesAfectas(String operacionesAfectas) {
        this.operacionesAfectas = operacionesAfectas;
    }

    public String getIndTipoVentaBien() {
        return indTipoVentaBien;
    }

    public void setIndTipoVentaBien(String indTipoVentaBien) {
        this.indTipoVentaBien = indTipoVentaBien;
    }

    public String getIndTipoVentaServicio() {
        return indTipoVentaServicio;
    }

    public void setIndTipoVentaServicio(String indTipoVentaServicio) {
        this.indTipoVentaServicio = indTipoVentaServicio;
    }

    public String getIndTipoFacturacionElect() {
        return indTipoFacturacionElect;
    }

    public void setIndTipoFacturacionElect(String indTipoFacturacionElect) {
        this.indTipoFacturacionElect = indTipoFacturacionElect;
    }

    public String getIndTipoFacturacionManual() {
        return indTipoFacturacionManual;
    }

    public void setIndTipoFacturacionManual(String indTipoFacturacionManual) {
        this.indTipoFacturacionManual = indTipoFacturacionManual;
    }

    public String getNombreRepresentanteLegal() {
        return nombreRepresentanteLegal;
    }

    public void setNombreRepresentanteLegal(String nombreRepresentanteLegal) {
        this.nombreRepresentanteLegal = nombreRepresentanteLegal;
    }

    public String getCargoRepresentanteLegal() {
        return cargoRepresentanteLegal;
    }

    public void setCargoRepresentanteLegal(String cargoRepresentanteLegal) {
        this.cargoRepresentanteLegal = cargoRepresentanteLegal;
    }

    public String getEmailRepresentanteLegal() {
        return emailRepresentanteLegal;
    }

    public void setEmailRepresentanteLegal(String emailRepresentanteLegal) {
        this.emailRepresentanteLegal = emailRepresentanteLegal;
    }

    public String getNroDocumRepresentanteLegal() {
        return nroDocumRepresentanteLegal;
    }

    public void setNroDocumRepresentanteLegal(String nroDocumRepresentanteLegal) {
        this.nroDocumRepresentanteLegal = nroDocumRepresentanteLegal;
    }

    public String getNombrePersonaCreditoCobranza() {
        return nombrePersonaCreditoCobranza;
    }

    public void setNombrePersonaCreditoCobranza(String nombrePersonaCreditoCobranza) {
        this.nombrePersonaCreditoCobranza = nombrePersonaCreditoCobranza;
    }

    public String getCargoPersonaCreditoCobranza() {
        return cargoPersonaCreditoCobranza;
    }

    public void setCargoPersonaCreditoCobranza(String cargoPersonaCreditoCobranza) {
        this.cargoPersonaCreditoCobranza = cargoPersonaCreditoCobranza;
    }

    public String getEmailPersonaCreditoCobranza() {
        return emailPersonaCreditoCobranza;
    }

    public void setEmailPersonaCreditoCobranza(String emailPersonaCreditoCobranza) {
        this.emailPersonaCreditoCobranza = emailPersonaCreditoCobranza;
    }

    public String getNroDocumPersonaCreditoCobranza() {
        return nroDocumPersonaCreditoCobranza;
    }

    public void setNroDocumPersonaCreditoCobranza(String nroDocumPersonaCreditoCobranza) {
        this.nroDocumPersonaCreditoCobranza = nroDocumPersonaCreditoCobranza;
    }

    public String getNombrePersonaTesoreria() {
        return nombrePersonaTesoreria;
    }

    public void setNombrePersonaTesoreria(String nombrePersonaTesoreria) {
        this.nombrePersonaTesoreria = nombrePersonaTesoreria;
    }

    public String getCargoPersonaTesoreria() {
        return cargoPersonaTesoreria;
    }

    public void setCargoPersonaTesoreria(String cargoPersonaTesoreria) {
        this.cargoPersonaTesoreria = cargoPersonaTesoreria;
    }

    public String getEmailPersonaTesoreria() {
        return emailPersonaTesoreria;
    }

    public void setEmailPersonaTesoreria(String emailPersonaTesoreria) {
        this.emailPersonaTesoreria = emailPersonaTesoreria;
    }

    public String getNroDocumPersonaTesoreria() {
        return nroDocumPersonaTesoreria;
    }

    public void setNroDocumPersonaTesoreria(String nroDocumPersonaTesoreria) {
        this.nroDocumPersonaTesoreria = nroDocumPersonaTesoreria;
    }

    public String getNombrePersonaCompra() {
        return nombrePersonaCompra;
    }

    public void setNombrePersonaCompra(String nombrePersonaCompra) {
        this.nombrePersonaCompra = nombrePersonaCompra;
    }

    public String getCargoPersonaCompra() {
        return cargoPersonaCompra;
    }

    public void setCargoPersonaCompra(String cargoPersonaCompra) {
        this.cargoPersonaCompra = cargoPersonaCompra;
    }

    public String getEmailPersonaCompra() {
        return emailPersonaCompra;
    }

    public void setEmailPersonaCompra(String emailPersonaCompra) {
        this.emailPersonaCompra = emailPersonaCompra;
    }

    public String getNroDocumPersonaCompra() {
        return nroDocumPersonaCompra;
    }

    public void setNroDocumPersonaCompra(String nroDocumPersonaCompra) {
        this.nroDocumPersonaCompra = nroDocumPersonaCompra;
    }

    public String getIndAceptacion() {
        return indAceptacion;
    }

    public void setIndAceptacion(String indAceptacion) {
        this.indAceptacion = indAceptacion;
    }

    public String getCodigoActividadEconomica() {
        return codigoActividadEconomica;
    }

    public void setCodigoActividadEconomica(String codigoActividadEconomica) {
        this.codigoActividadEconomica = codigoActividadEconomica;
    }

    public String getCodigoProfesionOficio() {
        return codigoProfesionOficio;
    }

    public void setCodigoProfesionOficio(String codigoProfesionOficio) {
        this.codigoProfesionOficio = codigoProfesionOficio;
    }

    public String getCodigoTipoProveedorActividad() {
        return codigoTipoProveedorActividad;
    }

    public void setCodigoTipoProveedorActividad(String codigoTipoProveedorActividad) {
        this.codigoTipoProveedorActividad = codigoTipoProveedorActividad;
    }

    public EstadoProveedor getIdEstadoProveedor() {
        return idEstadoProveedor;
    }

    public void setIdEstadoProveedor(EstadoProveedor idEstadoProveedor) {
        this.idEstadoProveedor = idEstadoProveedor;
    }


    public boolean isHabido() {
        return habido;
    }

    public void setHabido(boolean habido) {
        this.habido = habido;
    }

    public boolean isCreacionUnicaVez() {
        return creacionUnicaVez;
    }

    public void setCreacionUnicaVez(boolean creacionUnicaVez) {
        this.creacionUnicaVez = creacionUnicaVez;
    }

    public boolean isProveedorExcepcion() {
        return proveedorExcepcion;
    }

    public void setProveedorExcepcion(boolean proveedorExcepcion) {
        this.proveedorExcepcion = proveedorExcepcion;
    }

    public boolean isTipoVentaBien() {
        return tipoVentaBien;
    }

    public void setTipoVentaBien(boolean tipoVentaBien) {
        this.tipoVentaBien = tipoVentaBien;
    }

    public boolean isTipoVentaServicio() {
        return tipoVentaServicio;
    }

    public void setTipoVentaServicio(boolean tipoVentaServicio) {
        this.tipoVentaServicio = tipoVentaServicio;
    }

    public boolean isTipoFacturacionElect() {
        return tipoFacturacionElect;
    }

    public void setTipoFacturacionElect(boolean tipoFacturacionElect) {
        this.tipoFacturacionElect = tipoFacturacionElect;
    }

    public boolean isTipoFacturacionManual() {
        return tipoFacturacionManual;
    }

    public void setTipoFacturacionManual(boolean tipoFacturacionManual) {
        this.tipoFacturacionManual = tipoFacturacionManual;
    }

    public String getCelularPersonaCompra() {
        return celularPersonaCompra;
    }

    public void setCelularPersonaCompra(String celularPersonaCompra) {
        this.celularPersonaCompra = celularPersonaCompra;
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }

    public List<ProveedorCliente> getPrincipales() {
        return principales;
    }

    public void setPrincipales(List<ProveedorCliente> principales) {
        this.principales = principales;
    }

    public List<ProveedorInstalacion> getInstalaciones() {
        return instalaciones;
    }

    public void setInstalaciones(List<ProveedorInstalacion> instalaciones) {
        this.instalaciones = instalaciones;
    }

    public List<ProveedorPermiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<ProveedorPermiso> permisos) {
        this.permisos = permisos;
    }

    public List<ProveedorFuncionario> getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(List<ProveedorFuncionario> adicionales) {
        this.adicionales = adicionales;
    }

    public List<ProveedorPreguntaInformacion> getPreguntaInformacion() {
        return preguntaInformacion;
    }

    public void setPreguntaInformacion(List<ProveedorPreguntaInformacion> preguntaInformacion) {
        this.preguntaInformacion = preguntaInformacion;
    }

    public Boolean getIndMigradoSap() {
        return indMigradoSap;
    }

    public void setIndMigradoSap(Boolean indMigradoSap) {
        this.indMigradoSap = indMigradoSap;
    }

    @Override
    public String toString() {
        return "ProveedorDto{" +
                "idProveedor=" + idProveedor +
                ", idHcp='" + idHcp + '\'' +
                ", acreedorCodigoSap='" + acreedorCodigoSap + '\'' +
                ", contacto='" + contacto + '\'' +
                ", direccionFiscal='" + direccionFiscal + '\'' +
                ", email='" + email + '\'' +
                ", emailRetencion='" + emailRetencion + '\'' +
                ", evaluacionDesempeno=" + evaluacionDesempeno +
                ", evaluacionHomologacion=" + evaluacionHomologacion +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                ", indBlackList=" + indBlackList +
                ", indBloqueadoSap=" + indBloqueadoSap +
                ", indHomologado=" + indHomologado +
                ", indMigradoSap=" + indMigradoSap +
                ", indProveedorComunidad=" + indProveedorComunidad +
                ", razonSocial='" + razonSocial + '\'' +
                ", ruc='" + ruc + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipoPersona='" + tipoPersona + '\'' +
                ", idCondicionPago=" + idCondicionPago +
                ", idMoneda=" + idMoneda +
                ", idTipoComprobante=" + idTipoComprobante +
                ", idTipoProveedor=" + idTipoProveedor +
                ", codigoPais=" + codigoPais +
                ", codigoRegion=" + codigoRegion +
                ", codigoProvincia=" + codigoProvincia +
                ", codigoDistrito=" + codigoDistrito +
                ", activo=" + activo +
                ", habido=" + habido +
                ", po=" + nombreProveedorOse +
                ", indAgenteRetencion=" + indAgenteRetencion +
                ", canales=" + canales +
                ", cuentasBanco=" + cuentasBanco +
                ", lineasComercial=" + lineasComercial +
                ", productos=" + productos +
                ", evalucionesDesempenio=" + evalucionesDesempenio +
                ", noConformes=" + noConformes +
                ", respuestasHomologacion=" + respuestasHomologacion +
                ", catalogos=" + catalogos +
                ", adjuntosSunat=" + adjuntosSunat +
                ", sectorTrabajos=" + sectorTrabajos +
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
                ", celularPersonaCompra='" + celularPersonaCompra + '\'' +
                ", nroDocumPersonaCompra='" + nroDocumPersonaCompra + '\'' +
                ", indAceptacion='" + indAceptacion + '\'' +
                ", codigoActividadEconomica='" + codigoActividadEconomica + '\'' +
                ", codigoProfesionOficio='" + codigoProfesionOficio + '\'' +
                ", codigoTipoProveedorActividad='" + codigoTipoProveedorActividad + '\'' +
                ", creacionUnicaVez=" + creacionUnicaVez +
                ", proveedorExcepcion=" + proveedorExcepcion +
                ", tipoVentaBien=" + tipoVentaBien +
                ", tipoVentaServicio=" + tipoVentaServicio +
                ", tipoFacturacionElect=" + tipoFacturacionElect +
                ", tipoFacturacionManual=" + tipoFacturacionManual +
                ", aceptacion=" + aceptacion +
                ", idEstadoProveedor=" + idEstadoProveedor +
                ", principales=" + principales +
                ", instalaciones=" + instalaciones +
                ", permisos=" + permisos +
                ", adicionales=" + adicionales +
                ", preguntaInformacion=" + preguntaInformacion +
                '}';
    }
}
