package com.incloud.hcp.jco.balanza.CentroAlmacen.service;

import com.incloud.hcp.domain.balanza.CentroAlmacenBlz;

import java.util.List;

public interface JCOCentroAlmacenBlzService {
    void extraerCentroAlmacenBlz() throws Exception;

    List<CentroAlmacenBlz> getCentroAlmacenByCentro(String centro);
    List<CentroAlmacenBlz> getAllCentroAlmacen();
}
