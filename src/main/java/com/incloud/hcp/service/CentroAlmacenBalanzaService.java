package com.incloud.hcp.service;

import com.incloud.hcp.domain.balanza.CentroAlmacenBalanza;

public interface CentroAlmacenBalanzaService {

    CentroAlmacenBalanza guardarActualizarCentro(CentroAlmacenBalanza centroAlmacenBalanza);
    CentroAlmacenBalanza cambiarEstado(Integer centroAlmacenId) throws Exception;
}
