package com.incloud.hcp.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrador on 12/09/2017.
 */
public class SolicitudBlackListDto {

    private Integer idSolicitud;

    private String fechaCreacion;
    private String codigoSolicitud;

    @NotNull(message = "Es obligatorio")
    private String ruc;

    private String razonSocial;

    private String estado;

    @NotNull(message = "Es obligatorio")
    private int idCriterio;

    private String criterio;
    @NotNull(message = "Es obligatorio")

    private int idTipoSolicitud;

    @NotNull
    private String sede;

    @NotNull(message = "El motivo de la solicitud es obligatorio")
    private String motivo;

    private List<SolicitudBlackListAdjuntoDto> adjuntos;

    /**
     * Datos del usuario de creación
     */
    private String id;
    private String usuario;
    private String correo;

    public SolicitudBlackListDto() {
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCodigoSolicitud() {
        return codigoSolicitud;
    }

    public void setCodigoSolicitud(String codigoSolicitud) {
        this.codigoSolicitud = codigoSolicitud;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public int getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(int idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public List<SolicitudBlackListAdjuntoDto> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<SolicitudBlackListAdjuntoDto> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append("ruc : ");
        sb.append(ruc);
        sb.append(", razon social :");
        sb.append(razonSocial);
        sb.append(", idCriterio : ");
        sb.append(idCriterio);
        sb.append(", sede :");
        sb.append(sede);
        sb.append(", motivo : ");
        sb.append(motivo);
        sb.append(", id : ");
        sb.append(id);
        sb.append(", usuario : ");
        sb.append(usuario);
        sb.append(", correo : ");
        sb.append(correo);
        Optional.ofNullable(adjuntos).map(List::size).ifPresent(size -> {
            sb.append(", adjuntos : ");
            sb.append(size);
        });
        sb.append("]");
        return sb.toString();
    }
}
