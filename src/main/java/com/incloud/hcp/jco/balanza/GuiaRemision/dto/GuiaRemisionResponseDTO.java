package com.incloud.hcp.jco.balanza.GuiaRemision.dto;

import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.sap.SapLog;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class GuiaRemisionResponseDTO {

    private String serieGuia;
    private Integer nroGuia;
    private String tipoGuia;
    private String tipoMovimiento;
    private String tipoDestino;
    private String motTraslado;
    private String textoGuia;
    private String ticketPesaje;
    private String razonSocial;
    private String ruc;
    private String oriCentro;
    private String oriAlmacen;
    private String oriProveedor;
    private String oriDirStreet;
    private String oriDirHouseNum1;
    private String oriDirHouseNum2;
    private String oriDirStrSuppl2;
    private String oriDirRegion;
    private String oriDirBezei;
    private String oriDirCity1;
    private String oriDirCity2;
    private String desCentro;
    private String desAlmacen;
    private String desCliente;
    private String desProveedor;
    private String desDirStreet;
    private String desDirHouseNum1;
    private String desDirHouseNum2;
    private String desDirStrSuppl2;
    private String desDirRegion;
    private String desDirBezei;
    private String desDirCity1;
    private String desDirCity2;
    private String placaTransporte;
    private String placaCarreta;

    private String zBalanza;
    private String zProceso;
    private String zReferencia;
    private String zProducto;
    private String tipoOrigen;
    private String transport;
    private String lfdnr;
    private String land1;

    private String presintoAduanero;
    private String numContenedor;
    private String codigoDam;
    private Double pesoSeleccion;
    private String tipoLocacion;
    private String puertoLlegada;
    private String tipoPuerto;
    private String aeropuerto;
    private String constanciaDetraccion;
    private String sustentoDiferencia;
    private String sociedad;

    private String idmotTraslado;
    private String idmotOtro;
    private String modoTransporte;
    private Double pesoBruto;
    private Double pesoNeto;
    private Double pesoTara;
    private String indicadorServicio;
    private String tipoTransaccion;
    private String numeroBulto;
    private String unidadMedida;

    private Date fechaTraslado;
    private String usuarioCreador;
    private Date fechaCreacion;
    private Time horaCreacion;

    private List<GuiaRemisionPosResponseDTO> guiaRemisionPosList;
    private Chofer chofer;
    private List<SapLog> sapLogList;



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

    public String getTipoGuia() {
        return tipoGuia;
    }

    public void setTipoGuia(String tipoGuia) {
        this.tipoGuia = tipoGuia;
    }

    public String getMotTraslado() {
        return motTraslado;
    }

    public void setMotTraslado(String motTraslado) {
        this.motTraslado = motTraslado;
    }

    public String getTextoGuia() {
        return textoGuia;
    }

    public void setTextoGuia(String textoGuia) {
        this.textoGuia = textoGuia;
    }

    public String getTipoDestino() {
        return tipoDestino;
    }

    public void setTipoDestino(String tipoDestino) {
        this.tipoDestino = tipoDestino;
    }

    public String getTicketPesaje() {
        return ticketPesaje;
    }

    public void setTicketPesaje(String ticketPesaje) {
        this.ticketPesaje = ticketPesaje;
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

    public String getOriCentro() {
        return oriCentro;
    }

    public void setOriCentro(String oriCentro) {
        this.oriCentro = oriCentro;
    }

    public String getOriAlmacen() {
        return oriAlmacen;
    }

    public void setOriAlmacen(String oriAlmacen) {
        this.oriAlmacen = oriAlmacen;
    }

    public String getOriProveedor() {
        return oriProveedor;
    }

    public void setOriProveedor(String oriProveedor) {
        this.oriProveedor = oriProveedor;
    }

    public String getOriDirStreet() {
        return oriDirStreet;
    }

    public void setOriDirStreet(String oriDirStreet) {
        this.oriDirStreet = oriDirStreet;
    }

    public String getOriDirHouseNum1() {
        return oriDirHouseNum1;
    }

    public void setOriDirHouseNum1(String oriDirHouseNum1) {
        this.oriDirHouseNum1 = oriDirHouseNum1;
    }

    public String getOriDirHouseNum2() {
        return oriDirHouseNum2;
    }

    public void setOriDirHouseNum2(String oriDirHouseNum2) {
        this.oriDirHouseNum2 = oriDirHouseNum2;
    }

    public String getOriDirStrSuppl2() {
        return oriDirStrSuppl2;
    }

    public void setOriDirStrSuppl2(String oriDirStrSuppl2) {
        this.oriDirStrSuppl2 = oriDirStrSuppl2;
    }

    public String getOriDirRegion() {
        return oriDirRegion;
    }

    public void setOriDirRegion(String oriDirRegion) {
        this.oriDirRegion = oriDirRegion;
    }

    public String getOriDirBezei() {
        return oriDirBezei;
    }

    public void setOriDirBezei(String oriDirBezei) {
        this.oriDirBezei = oriDirBezei;
    }

    public String getOriDirCity1() {
        return oriDirCity1;
    }

    public void setOriDirCity1(String oriDirCity1) {
        this.oriDirCity1 = oriDirCity1;
    }

    public String getOriDirCity2() {
        return oriDirCity2;
    }

    public void setOriDirCity2(String oriDirCity2) {
        this.oriDirCity2 = oriDirCity2;
    }

    public String getDesCentro() {
        return desCentro;
    }

    public void setDesCentro(String desCentro) {
        this.desCentro = desCentro;
    }

    public String getDesAlmacen() {
        return desAlmacen;
    }

    public void setDesAlmacen(String desAlmacen) {
        this.desAlmacen = desAlmacen;
    }

    public String getDesCliente() {
        return desCliente;
    }

    public void setDesCliente(String desCliente) {
        this.desCliente = desCliente;
    }

    public String getDesProveedor() {
        return desProveedor;
    }

    public void setDesProveedor(String desProveedor) {
        this.desProveedor = desProveedor;
    }

    public String getDesDirStreet() {
        return desDirStreet;
    }

    public void setDesDirStreet(String desDirStreet) {
        this.desDirStreet = desDirStreet;
    }

    public String getDesDirHouseNum1() {
        return desDirHouseNum1;
    }

    public void setDesDirHouseNum1(String desDirHouseNum1) {
        this.desDirHouseNum1 = desDirHouseNum1;
    }

    public String getDesDirHouseNum2() {
        return desDirHouseNum2;
    }

    public void setDesDirHouseNum2(String desDirHouseNum2) {
        this.desDirHouseNum2 = desDirHouseNum2;
    }

    public String getDesDirStrSuppl2() {
        return desDirStrSuppl2;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public void setDesDirStrSuppl2(String desDirStrSuppl2) {
        this.desDirStrSuppl2 = desDirStrSuppl2;
    }

    public String getDesDirRegion() {
        return desDirRegion;
    }

    public void setDesDirRegion(String desDirRegion) {
        this.desDirRegion = desDirRegion;
    }

    public String getDesDirBezei() {
        return desDirBezei;
    }

    public void setDesDirBezei(String desDirBezei) {
        this.desDirBezei = desDirBezei;
    }

    public String getDesDirCity1() {
        return desDirCity1;
    }

    public void setDesDirCity1(String desDirCity1) {
        this.desDirCity1 = desDirCity1;
    }

    public String getDesDirCity2() {
        return desDirCity2;
    }

    public void setDesDirCity2(String desDirCity2) {
        this.desDirCity2 = desDirCity2;
    }

    public String getPlacaTransporte() {
        return placaTransporte;
    }

    public void setPlacaTransporte(String placaTransporte) {
        this.placaTransporte = placaTransporte;
    }

    public String getPlacaCarreta() {
        return placaCarreta;
    }

    public void setPlacaCarreta(String placaCarreta) {
        this.placaCarreta = placaCarreta;
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

    public String getzReferencia() {
        return zReferencia;
    }

    public void setzReferencia(String zReferencia) {
        this.zReferencia = zReferencia;
    }

    public String getzProducto() {
        return zProducto;
    }

    public void setzProducto(String zProducto) {
        this.zProducto = zProducto;
    }

    public List<GuiaRemisionPosResponseDTO> getGuiaRemisionPosList() {
        return guiaRemisionPosList;
    }

    public void setGuiaRemisionPosList(List<GuiaRemisionPosResponseDTO> guiaRemisionPosList) {
        this.guiaRemisionPosList = guiaRemisionPosList;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }
    public List<SapLog> getSapLogList() {
        return sapLogList;
    }

    public void setSapLogList(List<SapLog> sapLogList) {
        this.sapLogList = sapLogList;
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

    public Time getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(Time horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public String getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(String tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getIdmotTraslado() {
        return idmotTraslado;
    }

    public void setIdmotTraslado(String idmotTraslado) {
        this.idmotTraslado = idmotTraslado;
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

    public String getLfdnr() {
        return lfdnr;
    }

    public void setLfdnr(String lfdnr) {
        this.lfdnr = lfdnr;
    }

    public String getLand1() {
        return land1;
    }

    public void setLand1(String land1) {
        this.land1 = land1;
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

    public String getSociedad() {
        return sociedad;
    }

    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    @Override
    public String toString() {
        return "GuiaRemisionResponseDTO{" +
                "serieGuia='" + serieGuia + '\'' +
                ", nroGuia=" + nroGuia +
                ", tipoGuia='" + tipoGuia + '\'' +
                ", tipoMovimiento='" + tipoMovimiento + '\'' +
                ", tipoDestino='" + tipoDestino + '\'' +
                ", motTraslado='" + motTraslado + '\'' +
                ", textoGuia='" + textoGuia + '\'' +
                ", ticketPesaje='" + ticketPesaje + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", ruc='" + ruc + '\'' +
                ", oriCentro='" + oriCentro + '\'' +
                ", oriAlmacen='" + oriAlmacen + '\'' +
                ", oriProveedor='" + oriProveedor + '\'' +
                ", oriDirStreet='" + oriDirStreet + '\'' +
                ", oriDirHouseNum1='" + oriDirHouseNum1 + '\'' +
                ", oriDirHouseNum2='" + oriDirHouseNum2 + '\'' +
                ", oriDirStrSuppl2='" + oriDirStrSuppl2 + '\'' +
                ", oriDirRegion='" + oriDirRegion + '\'' +
                ", oriDirBezei='" + oriDirBezei + '\'' +
                ", oriDirCity1='" + oriDirCity1 + '\'' +
                ", oriDirCity2='" + oriDirCity2 + '\'' +
                ", desCentro='" + desCentro + '\'' +
                ", desAlmacen='" + desAlmacen + '\'' +
                ", desCliente='" + desCliente + '\'' +
                ", desProveedor='" + desProveedor + '\'' +
                ", desDirStreet='" + desDirStreet + '\'' +
                ", desDirHouseNum1='" + desDirHouseNum1 + '\'' +
                ", desDirHouseNum2='" + desDirHouseNum2 + '\'' +
                ", desDirStrSuppl2='" + desDirStrSuppl2 + '\'' +
                ", desDirRegion='" + desDirRegion + '\'' +
                ", desDirBezei='" + desDirBezei + '\'' +
                ", desDirCity1='" + desDirCity1 + '\'' +
                ", desDirCity2='" + desDirCity2 + '\'' +
                ", placaTransporte='" + placaTransporte + '\'' +
                ", placaCarreta='" + placaCarreta + '\'' +
                ", zBalanza='" + zBalanza + '\'' +
                ", zProceso='" + zProceso + '\'' +
                ", zReferencia='" + zReferencia + '\'' +
                ", zProducto='" + zProducto + '\'' +
                ", tipoOrigen='" + tipoOrigen + '\'' +
                ", transport='" + transport + '\'' +
                ", fechaTraslado=" + fechaTraslado +
                ", usuarioCreador='" + usuarioCreador + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", horaCreacion=" + horaCreacion +
                ", guiaRemisionPosList=" + guiaRemisionPosList +
                ", chofer=" + chofer +
                ", sapLogList=" + sapLogList +
                ", idmotOtro" + idmotOtro +
                ", idmotTraslado=" + idmotTraslado +
                ", modoTransporte=" + modoTransporte +
                ", pesoBruto=" + pesoBruto +
                ", pesoNeto=" + pesoNeto+
                ", pesoTara=" + pesoTara +
                '}';
    }
}
