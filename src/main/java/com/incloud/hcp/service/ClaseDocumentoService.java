package com.incloud.hcp.service;

import com.incloud.hcp.domain.ClaseDocumento;

import java.util.List;

/**
 * Created by USER on 24/08/2017.
 */
public interface ClaseDocumentoService {

    List<ClaseDocumento> getListClaseDocumentoSolped();

    List<ClaseDocumento> getListClaseDocumentoOrdenCompra(Integer idClaseDocumento);

    List<ClaseDocumento> getListClaseDocumentoOC(int claseDocSolped);

    List<ClaseDocumento> findByNivel(Integer nivel);

    List<ClaseDocumento> getListClaseDocumentoLicitacion(Integer idLicitacion) throws Exception;

}
