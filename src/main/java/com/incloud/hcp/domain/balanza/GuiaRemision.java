package com.incloud.hcp.domain.balanza;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
@Entity
@Access(AccessType.FIELD)
@Table(name="BLZ_GUIA_REMISION")
public class GuiaRemision implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(generator = "GUIA_REMISION_ID_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "GUIA_REMISION_ID_SEQ", sequenceName = "GUIA_REMISION_ID_SEQ", allocationSize = 1)
    @Column(name="ID_GUIA", unique=true, nullable=false)
    private Integer id;
    @Column(name="SERIE_GUIA", nullable=false)
    private String serieGuia;
    @Column(name="NRO_GUIA", nullable=false)
    private Integer nroGuia;
    @Column(name="ID_TIPO_GUIA", nullable=true)
    private String idtipoGuia;
    @Column(name="ID_MOT_TRANSLADO", nullable=true)
    private String idmotTraslado;
    @Column(name="ID_MOT_OTRO", nullable=true)
    private String idmotOtro;
    @Column(name="TEXTO_GUIA", nullable=true)
    private String textoGuia;
    @Column(name="ID_TICKET_PESAJE", nullable=false)
    private String idTicketPesaje;
    @Column(name="RAZON_SOCIAL", nullable=false)
    private String RazonSocial;
    @Column(name="REFERENCIA", nullable=true)
    private String referencia;
    @Column(name="RUC", nullable=false)
    private String ruc;
    @Column(name="ID_CENTRO_ORIGEN", nullable=true)
    private String idCentroOrigen;
    @Column(name="ID_CENTRO_DESTINO", nullable=true)
    private String idCentroDestino;
    @Column(name="ID_ALMACEN_ORIGEN", nullable=true)
    private String idAlmacenOrigen;
    @Column(name="ID_ALMACEN_DESTINO", nullable=true)
    private String idAlmacenDestino;
    @Column(name="PROVEEDOR_ORIGEN", nullable=true)
    private String proveedorOrigen;
    @Column(name="PROVEEDOR_DESTINO", nullable=true)
    private String proveedorDestino;
    @Column(name="CLIENTE_DESTINO", nullable=true)
    private String clienteDestino;
    @Column(name="DIRECCION_DESTINO", nullable=true)
    private String direccionDestino;
    @Column(name="DIRECCION_ORIGEN", nullable=true)
    private String direccionOrigen;

    @Column(name="DISTRITO", nullable=true)
    private String distrito;
    @Column(name="REGION", nullable=true)
    private String region;

    @Column(name="DENOMINACION", nullable=true)
    private String denominacion;

    @Column(name="POBLACION", nullable=true)
    private String poblacion;

    @Column(name="TIPO_DESTINO", nullable=true)
    private String tipoDestino;

    @Column(name="ACREEDOR", nullable=true)
    private String acreedor;



    @Column(name = "ZBALANZA")
    private String zBalanza;

    @Column(name = "ZINDEST")
    private String zIndest;
    @Column(name = "ZINDORI")
    private String zIndori;
    @Column(name = "TRANSPORT")
    private String transport;
    @Column(name = "ZPROCESO")
    private String zProceso;

    @Column(name = "ZPRODUCTO")
    private String zProducto;

    @Column(name = "SOCIEDAD")
    private String sociedad;

    @Column(name = "CENTRO")
    private String centro;

    @Column(name = "FECHA_TRASLADO")
    private Date fechaTraslado;

    @Column(name = "USUARIO_CREADOR")
    private String usuarioCreador;

    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Column(name = "HORA_CREACION")
    private LocalTime horaCreacion;

    @Column(name = "MODO_TRASNPORTE")
    private String modoTransporte;

    @Column(name = "PESO_BRUTO")
    private Double pesoBruto;

    @Column(name = "PESO_NETO")
    private Double pesoNeto;

    @Column(name = "PESO_TARA")
    private Double pesoTara;

    @Column(name = "TIPO_MOVIEMIENTO")
    private String tipoMovimiento;

    @Column(name = "INDICADOR_SERVICIO")
    private String indicadorServicio;

    @Column(name = "TIPO_TRANSACCION")
    private String tipoTransaccion;

    @Column(name = "NUMERO_BULTO")
    private String numeroBulto;
    @Column(name = "UNIDAD_MEDIDA")
    private String unidadMedida;
    @Column(name = "BEZEI")
    private String bezei;
    @Column(name = "City1")
    private String city1;
    @Column(name = "CITY2")
    private String city2;
    @Column(name = "LAND1")
    private String land1;

    @Column(name = "LFDNR")
    private String lfdnr;

    @Column(name = "PRESINTO_ADUANERO")
    private String presintoAduanero;
    @Column(name = "NUM_CONTENEDOR")
    private String numContenedor;
    @Column(name = "CODIGO_DAM")
    private String codigoDam;
    @Column(name = "PESO_SELECCION")
    private Double pesoSeleccion;
    @Column(name = "TIPO_LOCACION")
    private String tipoLocacion;
    @Column(name = "PUERTO_LLEGADA")
    private String puertoLlegada;
    @Column(name = "TIPO_PUERTO")
    private String tipoPuerto;
    @Column(name = "AEROPUERTO")
    private String aeropuerto;
    @Column(name = "SUSTENTO_DIFERENCIA")
    private String sustentoDiferencia;
    @Column(name = "CONSTANCIA_DETRACCION")
    private String constanciaDetraccion;
    @Column(name = "MESANJE_SAP")
    private String mensaje;
    @Column(name = "ESTADO_SAP")
    private String estado;
    @Column(name = "ZANULA")
    private String zanula;

    @JsonIgnoreProperties(value={"choferticketPesajeList", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Chofer.class)
    @JoinColumn(name="ID_CHOFER", referencedColumnName = "ID_CHOFER")
    private Chofer chofer;

    @JsonIgnoreProperties(value={"transporteticketPesajeList", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Transporte.class)
    @JoinColumn(name="ID_TRANSPORTE", referencedColumnName = "ID_TRANSPORTE")
    private Transporte transporte;

    @JsonIgnoreProperties(value={"carretaticketPesajeList", "hibernateLazyInitializer", "handler"}, allowSetters=true)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Transporte.class)
    @JoinColumn(name="ID_CARRETA", referencedColumnName = "ID_TRANSPORTE")
    private Transporte carreta;

    public GuiaRemision(Integer id, String serieGuia, Integer nroGuia, String idtipoGuia, String idmotTraslado, String textoGuia, String idTicketPesaje, String razonSocial, String referencia, String ruc, String idCentroOrigen, String idCentroDestino, String idAlmacenOrigen, String idAlmacenDestino, String proveedorOrigen, String proveedorDestino, String clienteDestino, String direccionDestino, String direccionOrigen, String region, String denominacion, String poblacion, String tipoDestino, String acreedor, String distrito, String zBalanza, String zIndest, String zIndori, String transport, String zProceso, String zProducto, String sociedad, String centro, Date fechaTraslado, String usuarioCreador, Date fechaCreacion, LocalTime horaCreacion, Chofer chofer, Transporte transporte, Transporte carreta) {
        this.id = id;
        this.serieGuia = serieGuia;
        this.nroGuia = nroGuia;
        this.idtipoGuia = idtipoGuia;
        this.idmotTraslado = idmotTraslado;
        this.textoGuia = textoGuia;
        this.idTicketPesaje = idTicketPesaje;
        RazonSocial = razonSocial;
        this.referencia = referencia;
        this.ruc = ruc;
        this.idCentroOrigen = idCentroOrigen;
        this.idCentroDestino = idCentroDestino;
        this.idAlmacenOrigen = idAlmacenOrigen;
        this.idAlmacenDestino = idAlmacenDestino;
        this.proveedorOrigen = proveedorOrigen;
        this.proveedorDestino = proveedorDestino;
        this.clienteDestino = clienteDestino;
        this.direccionDestino = direccionDestino;
        this.direccionOrigen = direccionOrigen;
        this.region = region;
        this.denominacion = denominacion;
        this.poblacion = poblacion;
        this.tipoDestino = tipoDestino;
        this.acreedor = acreedor;
        this.distrito = distrito;
        this.zBalanza = zBalanza;
        this.zIndest = zIndest;
        this.zIndori = zIndori;
        this.transport = transport;
        this.zProceso = zProceso;
        this.zProducto = zProducto;
        this.sociedad = sociedad;
        this.centro = centro;
        this.fechaTraslado = fechaTraslado;
        this.usuarioCreador = usuarioCreador;
        this.fechaCreacion = fechaCreacion;
        this.horaCreacion = horaCreacion;
        this.chofer = chofer;
        this.transporte = transporte;
        this.carreta = carreta;
    }

    public GuiaRemision(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerieGuia() {
        return serieGuia;
    }

    public void setSerieGuia(String serieGuia) {
        this.serieGuia = serieGuia;
    }

    public Integer getNroGuia() {
        return nroGuia;
    }

    public void setNroGuia(Integer nroGuia) {
        this.nroGuia = nroGuia;
    }

    public String getIdtipoGuia() {
        return idtipoGuia;
    }

    public void setIdtipoGuia(String idtipoGuia) {
        this.idtipoGuia = idtipoGuia;
    }

    public String getIdmotTraslado() {
        return idmotTraslado;
    }

    public void setIdmotTraslado(String idmotTraslado) {
        this.idmotTraslado = idmotTraslado;
    }

    public String getTextoGuia() {
        return textoGuia;
    }

    public void setTextoGuia(String textoGuia) {
        this.textoGuia = textoGuia;
    }

    public String getIdTicketPesaje() {
        return idTicketPesaje;
    }

    public void setIdTicketPesaje(String idTicketPesaje) {
        this.idTicketPesaje = idTicketPesaje;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getIdCentroOrigen() {
        return idCentroOrigen;
    }

    public void setIdCentroOrigen(String idCentroOrigen) {
        this.idCentroOrigen = idCentroOrigen;
    }


    public String getIdCentroDestino() {
        return idCentroDestino;
    }

    public void setIdCentroDestino(String idCentroDestino) {
        this.idCentroDestino = idCentroDestino;
    }

    public String getIdAlmacenOrigen() {
        return idAlmacenOrigen;
    }

    public void setIdAlmacenOrigen(String idAlmacenOrigen) {
        this.idAlmacenOrigen = idAlmacenOrigen;
    }

    public String getIdAlmacenDestino() {
        return idAlmacenDestino;
    }

    public void setIdAlmacenDestino(String idAlmacenDestino) {
        this.idAlmacenDestino = idAlmacenDestino;
    }

    public String getProveedorOrigen() {
        return proveedorOrigen;
    }

    public void setProveedorOrigen(String proveedorOrigen) {
        this.proveedorOrigen = proveedorOrigen;
    }

    public String getProveedorDestino() {
        return proveedorDestino;
    }

    public void setProveedorDestino(String proveedorDestino) {
        this.proveedorDestino = proveedorDestino;
    }

    public String getClienteDestino() {
        return clienteDestino;
    }

    public void setClienteDestino(String clienteDestino) {
        this.clienteDestino = clienteDestino;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getzBalanza() {
        return zBalanza;
    }

    public void setzBalanza(String zBalanza) {
        this.zBalanza = zBalanza;
    }

    public String getzProceso() {
        return zProceso;
    }

    public void setzProceso(String zProceso) {
        this.zProceso = zProceso;
    }

    public String getzProducto() {
        return zProducto;
    }

    public void setzProducto(String zProducto) {
        this.zProducto = zProducto;
    }

    public Date getFechaTraslado() {
        return fechaTraslado;
    }

    public void setFechaTraslado(Date fechaTraslado) {
        this.fechaTraslado = fechaTraslado;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalTime getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(LocalTime horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public Transporte getCarreta() {
        return carreta;
    }

    public void setCarreta(Transporte carreta) {
        this.carreta = carreta;
    }
/* public Carreta getCarreta() {
        return carreta;
    }

    public void setCarreta(Carreta carreta) {
        this.carreta = carreta;
    }*/

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getTipoDestino() {
        return tipoDestino;
    }

    public void setTipoDestino(String tipoDestino) {
        this.tipoDestino = tipoDestino;
    }

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getzIndest() {
        return zIndest;
    }

    public String getzIndori() {
        return zIndori;
    }

    public void setzIndori(String zIndori) {
        this.zIndori = zIndori;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setzIndest(String zIndest) {
        this.zIndest = zIndest;
    }

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getIdmotOtro() {
        return idmotOtro;
    }

    public void setIdmotOtro(String idmotOtro) {
        this.idmotOtro = idmotOtro;
    }

    public String getModoTransporte() {
        return modoTransporte;
    }

    public void setModoTransporte(String modoTransporte) {
        this.modoTransporte = modoTransporte;
    }


    public Double getPesoBruto() {
        return pesoBruto;
    }

    public void setPesoBruto(Double pesoBruto) {
        this.pesoBruto = pesoBruto;
    }

    public Double getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(Double pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public Double getPesoTara() {
        return pesoTara;
    }

    public void setPesoTara(Double pesoTara) {
        this.pesoTara = pesoTara;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getIndicadorServicio() {
        return indicadorServicio;
    }

    public void setIndicadorServicio(String indicadorServicio) {
        this.indicadorServicio = indicadorServicio;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getNumeroBulto() {
        return numeroBulto;
    }

    public void setNumeroBulto(String numeroBulto) {
        this.numeroBulto = numeroBulto;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getBezei() {
        return bezei;
    }

    public void setBezei(String bezei) {
        this.bezei = bezei;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public String getLand1() {
        return land1;
    }

    public void setLand1(String land1) {
        this.land1 = land1;
    }

    public String getLfdnr() {
        return lfdnr;
    }

    public void setLfdnr(String lfdnr) {
        this.lfdnr = lfdnr;
    }

    public String getPresintoAduanero() {
        return presintoAduanero;
    }

    public void setPresintoAduanero(String presintoAduanero) {
        this.presintoAduanero = presintoAduanero;
    }

    public String getNumContenedor() {
        return numContenedor;
    }

    public void setNumContenedor(String numContenedor) {
        this.numContenedor = numContenedor;
    }

    public String getCodigoDam() {
        return codigoDam;
    }

    public void setCodigoDam(String codigoDam) {
        this.codigoDam = codigoDam;
    }

    public Double getPesoSeleccion() {
        return pesoSeleccion;
    }

    public void setPesoSeleccion(Double pesoSeleccion) {
        this.pesoSeleccion = pesoSeleccion;
    }

    public String getTipoLocacion() {
        return tipoLocacion;
    }

    public void setTipoLocacion(String tipoLocacion) {
        this.tipoLocacion = tipoLocacion;
    }

    public String getPuertoLlegada() {
        return puertoLlegada;
    }

    public void setPuertoLlegada(String puertoLlegada) {
        this.puertoLlegada = puertoLlegada;
    }

    public String getTipoPuerto() {
        return tipoPuerto;
    }

    public void setTipoPuerto(String tipoPuerto) {
        this.tipoPuerto = tipoPuerto;
    }

    public String getAeropuerto() {
        return aeropuerto;
    }

    public void setAeropuerto(String aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    public String getConstanciaDetraccion() {
        return constanciaDetraccion;
    }

    public void setConstanciaDetraccion(String constanciaDetraccion) {
        this.constanciaDetraccion = constanciaDetraccion;
    }

    public String getSustentoDiferencia() {
        return sustentoDiferencia;
    }

    public void setSustentoDiferencia(String sustentoDiferencia) {
        this.sustentoDiferencia = sustentoDiferencia;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getZanula() {
        return zanula;
    }

    public void setZanula(String zanula) {
        this.zanula = zanula;
    }

    @Override
    public String toString() {
        return "GuiaRemision{" +
                "id=" + id +
                ", serieGuia='" + serieGuia + '\'' +
                ", nroGuia=" + nroGuia +
                ", idtipoGuia='" + idtipoGuia + '\'' +
                ", idmotTraslado='" + idmotTraslado + '\'' +
                ", textoGuia='" + textoGuia + '\'' +
                ", idTicketPesaje='" + idTicketPesaje + '\'' +
                ", RazonSocial='" + RazonSocial + '\'' +
                ", referencia='" + referencia + '\'' +
                ", ruc='" + ruc + '\'' +
                ", idCentroOrigen='" + idCentroOrigen + '\'' +
                ", idCentroDestino='" + idCentroDestino + '\'' +
                ", idAlmacenOrigen='" + idAlmacenOrigen + '\'' +
                ", idAlmacenDestino='" + idAlmacenDestino + '\'' +
                ", proveedorOrigen='" + proveedorOrigen + '\'' +
                ", proveedorDestino='" + proveedorDestino + '\'' +
                ", clienteDestino='" + clienteDestino + '\'' +
                ", direccionDestino='" + direccionDestino + '\'' +
                ", direccionOrigen='" + direccionOrigen + '\'' +
                ", region='" + region + '\'' +
                ", denominacion='" + denominacion + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", tipoDestino='" + tipoDestino + '\'' +
                ", acreedor='" + acreedor + '\'' +
                ", distrito='" + distrito + '\'' +
                ", zBalanza='" + zBalanza + '\'' +
                ", zIndest='" + zIndest + '\'' +
                ", zIndori='" + zIndori + '\'' +
                ", transport='" + transport + '\'' +
                ", zProceso='" + zProceso + '\'' +
                ", zProducto='" + zProducto + '\'' +
                ", fechaTraslado=" + fechaTraslado +
                ", usuarioCreador='" + usuarioCreador + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", horaCreacion=" + horaCreacion +
                ", chofer=" + chofer +
                ", transporte=" + transporte +
                ", carreta=" + carreta +
                ", sociedad=" + sociedad +
                ", centro=" + centro +
                ", idmotOtro=" + idmotOtro +
                ", modoTransporte=" + modoTransporte +
                ", pesoBruto=" + pesoBruto +
                ", pesoNeto=" + pesoNeto+
                ", pesoTara=" + pesoTara +
                '}';
    }
}
