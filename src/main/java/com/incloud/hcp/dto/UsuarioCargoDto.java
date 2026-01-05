package com.incloud.hcp.dto;

import com.incloud.hcp.domain.UsuarioCargo;

import java.util.List;

public class UsuarioCargoDto {

    private Integer idUsuario;
    private List<UsuarioCargo> usuarioCargoList;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<UsuarioCargo> getUsuarioCargoList() {
        return usuarioCargoList;
    }

    public void setUsuarioCargoList(List<UsuarioCargo> usuarioCargoList) {
        this.usuarioCargoList = usuarioCargoList;
    }
}
