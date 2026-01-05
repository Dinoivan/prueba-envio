package com.incloud.hcp.service;

import com.incloud.hcp.domain.Licitacion;

/**
 * Created by USER on 10/11/2017.
 */
public interface LicitacionAdjuntoService {

    public void deleteLicitacionAdjuntoByLicitacionArchivoId(Licitacion licitacion, String archivoId);

}
