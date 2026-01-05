package com.incloud.hcp.dto;

import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.domain.ProveedorPreguntaInformacion;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Created by Administrador on 04/09/2017.
 */
@Validated
public class PreRegistroProveedorDto {
    private Integer idRegistro;

    @NotNull(message = "Es obligatorio")
    private String ruc;

    @NotNull(message = "Es obligatorio")
    @Size(max = 300, message = "Campo debe ser como máximo 300 caracteres")
    private String razonSocial;

    @NotNull(message = "Es obligatorio")
    @Size(max = 600, message = "Campo debe ser como máximo 600 caracteres")
    private String contacto;

    @NotNull(message = "Es obligatorio")
    @Size(max = 30, message = "Campo debe ser como máximo 30 caracteres")
    private String telefono;

    @NotNull(message = "Es obligatorio")
    @Size(max = 100, message = "Campo debe ser como máximo 100 caracteres")
    private String email;

    private String estado;
    @NotNull(message = "Es obligatorio")

    private Integer idTipoProveedor;

    @NotNull(message = "Es obligatorio")
    @Size(max = 60, message = "Campo debe ser como máximo 60 caracteres")
    private String idHcp;

    private boolean activo;
    private boolean habido;
    private String region;
    private String provincia;
    private String distrito;
    private String ubigeo;
    private String direccion;
    private boolean sunat;
    private String datospersonales;

    private String actividadEconomica;
    private String fechaInicioActiSunat;
    private String codigoSistemaEmisionElect;
    private String codigoComprobantePago;
    private String codigoPadron;
    private String comentario;


//    @NotNull(message = "Es obligatorio")
    private List<LineaComercial> lineaComercialList;

    private List<LineaComercialDto> lineasComercial;

    private List<ProveedorPreguntaInformacion> preguntaInformacion;

    public PreRegistroProveedorDto() {
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

    public Integer getIdTipoProveedor() {
        return idTipoProveedor;
    }

    public void setIdTipoProveedor(Integer idTipoProveedor) {
        this.idTipoProveedor = idTipoProveedor;
    }

    public String getIdHcp() {
        return idHcp;
    }

    public void setIdHcp(String idHcp) {
        this.idHcp = idHcp;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean getHabido() {
        return habido;
    }

    public void setHabido(boolean habido) {
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

    public boolean getSunat() {
        return sunat;
    }

    public void setSunat(boolean sunat) {
        this.sunat = sunat;
    }

    public String getDatospersonales() {
        return datospersonales;
    }

    public void setDatospersonales(String datospersonales) {
        this.datospersonales = datospersonales;
    }


    public boolean isActivo() {
        return activo;
    }

    public boolean isHabido() {
        return habido;
    }

    public boolean isSunat() {
        return sunat;
    }

    public List<LineaComercial> getLineaComercialList() {
        return lineaComercialList;
    }

    public void setLineaComercialList(List<LineaComercial> lineaComercialList) {
        this.lineaComercialList = lineaComercialList;
    }

    public List<LineaComercialDto> getLineasComercial() {
        return lineasComercial;
    }

    public void setLineasComercial(List<LineaComercialDto> lineasComercial) {
        this.lineasComercial = lineasComercial;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public String getFechaInicioActiSunat() {
        return fechaInicioActiSunat;
    }

    public void setFechaInicioActiSunat(String fechaInicioActiSunat) {
        this.fechaInicioActiSunat = fechaInicioActiSunat;
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

    public List<ProveedorPreguntaInformacion> getPreguntaInformacion() {
        return preguntaInformacion;
    }

    public void setPreguntaInformacion(List<ProveedorPreguntaInformacion> preguntaInformacion) {
        this.preguntaInformacion = preguntaInformacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "PreRegistroProveedorDto{" +
                "idRegistro=" + idRegistro +
                ", ruc='" + ruc + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", comentario='" + comentario + '\'' +
                ", contacto='" + contacto + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", estado='" + estado + '\'' +
                ", idTipoProveedor=" + idTipoProveedor +
                ", idHcp='" + idHcp + '\'' +
                ", activo=" + activo +
                ", habido=" + habido +
                ", region='" + region + '\'' +
                ", provincia='" + provincia + '\'' +
                ", distrito='" + distrito + '\'' +
                ", ubigeo='" + ubigeo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", sunat=" + sunat +
                ", datospersonales='" + datospersonales + '\'' +
                ", actividadEconomica='" + actividadEconomica + '\'' +
                ", fechaInicioActiSunat='" + fechaInicioActiSunat + '\'' +
                ", codigoSistemaEmisionElect='" + codigoSistemaEmisionElect + '\'' +
                ", codigoComprobantePago='" + codigoComprobantePago + '\'' +
                ", codigoPadron='" + codigoPadron + '\'' +
                ", lineaComercialList=" + lineaComercialList +
                ", lineasComercial=" + lineasComercial +
                ", preguntaInformacion=" + preguntaInformacion +
                '}';
    }
}
