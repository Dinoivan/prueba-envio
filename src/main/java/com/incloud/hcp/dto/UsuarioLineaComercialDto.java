package com.incloud.hcp.dto;

import com.incloud.hcp.domain.UsuarioLineaComercial;

import java.util.List;

public class UsuarioLineaComercialDto {

    private Integer idUsuario;
    private List<UsuarioLineaComercial> usuarioLineaComercialList;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<UsuarioLineaComercial> getUsuarioLineaComercialList() {
        return usuarioLineaComercialList;
    }

    public void setUsuarioLineaComercialList(List<UsuarioLineaComercial> usuarioLineaComercialList) {
        this.usuarioLineaComercialList = usuarioLineaComercialList;
    }


}
