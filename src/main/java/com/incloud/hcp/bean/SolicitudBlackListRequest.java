package com.incloud.hcp.bean;

import com.incloud.hcp.domain.AprobadorSolicitud;
import com.incloud.hcp.domain.CriteriosBlacklist;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.SolicitudAdjuntoBlacklist;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by USER on 02/11/2017.
 */
public class SolicitudBlackListRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer idSolicitud;
    private String sede;
    private String motivo;
    private CriteriosBlacklist criteriosBlacklist;
    private Proveedor proveedor;
    private String estadoSolicitud;
    private String adminIdRevision;
    private String adminNameRevision;
    private String adminFechaRevision;
    private String indRechazoAdmin;
    private String motivoRechazoAdmin;
    private Date fechaCreacion;
    private Integer usuarioCreacion;
    private String usuarioCreacionName;
    private String usuarioCreacionEmail;
    private Date fechaModificacion;
    private Integer usuarioModificacion;
    private List<AprobadorSolicitud> listaAprobador;
    private List<SolicitudAdjuntoBlacklist> listAdjuntoBlackList;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
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

    public CriteriosBlacklist getCriteriosBlacklist() {
        return criteriosBlacklist;
    }

    public void setCriteriosBlacklist(CriteriosBlacklist criteriosBlacklist) {
        this.criteriosBlacklist = criteriosBlacklist;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getAdminIdRevision() {
        return adminIdRevision;
    }

    public void setAdminIdRevision(String adminIdRevision) {
        this.adminIdRevision = adminIdRevision;
    }

    public String getAdminNameRevision() {
        return adminNameRevision;
    }

    public void setAdminNameRevision(String adminNameRevision) {
        this.adminNameRevision = adminNameRevision;
    }

    public String getAdminFechaRevision() {
        return adminFechaRevision;
    }

    public void setAdminFechaRevision(String adminFechaRevision) {
        this.adminFechaRevision = adminFechaRevision;
    }

    public String getIndRechazoAdmin() {
        return indRechazoAdmin;
    }

    public void setIndRechazoAdmin(String indRechazoAdmin) {
        this.indRechazoAdmin = indRechazoAdmin;
    }

    public String getMotivoRechazoAdmin() {
        return motivoRechazoAdmin;
    }

    public void setMotivoRechazoAdmin(String motivoRechazoAdmin) {
        this.motivoRechazoAdmin = motivoRechazoAdmin;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(Integer usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioCreacionName() {
        return usuarioCreacionName;
    }

    public void setUsuarioCreacionName(String usuarioCreacionName) {
        this.usuarioCreacionName = usuarioCreacionName;
    }

    public String getUsuarioCreacionEmail() {
        return usuarioCreacionEmail;
    }

    public void setUsuarioCreacionEmail(String usuarioCreacionEmail) {
        this.usuarioCreacionEmail = usuarioCreacionEmail;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(Integer usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public List<AprobadorSolicitud> getListaAprobador() {
        return listaAprobador;
    }

    public void setListaAprobador(List<AprobadorSolicitud> listaAprobador) {
        this.listaAprobador = listaAprobador;
    }

    public List<SolicitudAdjuntoBlacklist> getListAdjuntoBlackList() {
        return listAdjuntoBlackList;
    }

    public void setListAdjuntoBlackList(List<SolicitudAdjuntoBlacklist> listAdjuntoBlackList) {
        this.listAdjuntoBlackList = listAdjuntoBlackList;
    }

    @Override
    public String toString() {
        return "SolicitudBlackListRequest{" +
                "idSolicitud=" + idSolicitud +
                ", sede='" + sede + '\'' +
                ", motivo='" + motivo + '\'' +
                ", criteriosBlacklist=" + criteriosBlacklist +
                ", proveedor=" + proveedor +
                ", estadoSolicitud='" + estadoSolicitud + '\'' +
                ", adminIdRevision='" + adminIdRevision + '\'' +
                ", adminNameRevision='" + adminNameRevision + '\'' +
                ", adminFechaRevision='" + adminFechaRevision + '\'' +
                ", indRechazoAdmin='" + indRechazoAdmin + '\'' +
                ", motivoRechazoAdmin='" + motivoRechazoAdmin + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", usuarioCreacion=" + usuarioCreacion +
                ", usuarioCreacionName='" + usuarioCreacionName + '\'' +
                ", usuarioCreacionEmail='" + usuarioCreacionEmail + '\'' +
                ", fechaModificacion=" + fechaModificacion +
                ", usuarioModificacion=" + usuarioModificacion +
                ", listaAprobador=" + listaAprobador +
                ", listAdjuntoBlackList=" + listAdjuntoBlackList +
                '}';
    }
}
