package com.incloud.hcp.service;

import com.incloud.hcp.domain.HomologacionRespuesta;

import java.util.List;

/**
 * Created by MARCELO on 13/10/2017.
 */
public interface HomologacionRespuestaService {

    HomologacionRespuesta guardar(HomologacionRespuesta homologacionRespuesta);

    List<HomologacionRespuesta> getHomologacionRespuestaByHomologacion(Integer idHomologacion);
}
