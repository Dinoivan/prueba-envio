package com.incloud.hcp.jco.grupoArticulo.service;

import com.incloud.hcp.jco.grupoArticulo.dto.GrupoArticuloRFCResponseDto;

public interface JCOGrupoArticuloServiceNew {

    GrupoArticuloRFCResponseDto getGrupoArticulo(String codigo) throws Exception;
}
