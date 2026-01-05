package com.incloud.hcp.dto;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.*;
import com.incloud.hcp.sap.SapLog;

import java.util.List;

public class CComparativoAdjudicarDto {

    private Integer idLicitacion;
    private Integer idUsuario;
    private boolean exito = true;
    private Licitacion licitacion;
    private UserSession userSession;
    private Usuario usuario;

    private List<CcomparativoProveedor> ccomparativoProveedorList;
    private List<CcomparativoAdjudicado> ccomparativoAdjudicadoList;
    private List<Proveedor> listaProveedor;
    private List<SapLog> modPeticionOfertaSapLogList;

    public Integer getIdLicitacion() {
        return idLicitacion;
    }

    public void setIdLicitacion(Integer idLicitacion) {
        this.idLicitacion = idLicitacion;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public Licitacion getLicitacion() {
        return licitacion;
    }

    public void setLicitacion(Licitacion licitacion) {
        this.licitacion = licitacion;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<CcomparativoProveedor> getCcomparativoProveedorList() {
        return ccomparativoProveedorList;
    }

    public void setCcomparativoProveedorList(List<CcomparativoProveedor> ccomparativoProveedorList) {
        this.ccomparativoProveedorList = ccomparativoProveedorList;
    }

    public List<CcomparativoAdjudicado> getCcomparativoAdjudicadoList() {
        return ccomparativoAdjudicadoList;
    }

    public void setCcomparativoAdjudicadoList(List<CcomparativoAdjudicado> ccomparativoAdjudicadoList) {
        this.ccomparativoAdjudicadoList = ccomparativoAdjudicadoList;
    }

    public List<Proveedor> getListaProveedor() {
        return listaProveedor;
    }

    public void setListaProveedor(List<Proveedor> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }

    public List<SapLog> getModPeticionOfertaSapLogList() {
        return modPeticionOfertaSapLogList;
    }

    public void setModPeticionOfertaSapLogList(List<SapLog> modPeticionOfertaSapLogList) {
        this.modPeticionOfertaSapLogList = modPeticionOfertaSapLogList;
    }

    @Override
    public String toString() {
        return "CComparativoAdjudicarDto{" +
                "idLicitacion=" + idLicitacion +
                ", idUsuario=" + idUsuario +
                ", exito=" + exito +
                ", licitacion=" + licitacion +
                ", userSession=" + userSession +
                ", usuario=" + usuario +
                ", ccomparativoProveedorList=" + ccomparativoProveedorList +
                ", ccomparativoAdjudicadoList=" + ccomparativoAdjudicadoList +
                ", listaProveedor=" + listaProveedor +
                ", modPeticionOfertaSapLogList=" + modPeticionOfertaSapLogList +
                '}';
    }
}
