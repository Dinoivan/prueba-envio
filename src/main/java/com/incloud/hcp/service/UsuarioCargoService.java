package com.incloud.hcp.service;

import com.incloud.hcp.domain.UsuarioCargo;
import com.incloud.hcp.dto.UsuarioCargoDto;

import java.util.List;

public interface UsuarioCargoService {

    List<UsuarioCargo> findbyIdUsuario(Integer idUsuario);

    List<UsuarioCargo> grabarLista(UsuarioCargoDto usuarioCargoDto) throws Exception;
}
