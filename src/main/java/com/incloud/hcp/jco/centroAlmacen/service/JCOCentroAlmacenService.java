package com.incloud.hcp.jco.centroAlmacen.service;

import com.incloud.hcp.jco.centroAlmacen.dto.CentroAlmacenRFCResponseDto;


public interface JCOCentroAlmacenService {

    CentroAlmacenRFCResponseDto actualizaCentroAlmacen(String centro) throws Exception;
}
