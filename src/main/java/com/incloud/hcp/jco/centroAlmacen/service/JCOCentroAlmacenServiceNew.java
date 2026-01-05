package com.incloud.hcp.jco.centroAlmacen.service;

import com.incloud.hcp.jco.centroAlmacen.dto.CentroAlmacenRFCResponseDto;

import java.util.List;

public interface JCOCentroAlmacenServiceNew {

    CentroAlmacenRFCResponseDto getListaCentroAlmacen(String centro) throws Exception;

    CentroAlmacenRFCResponseDto getListaCentroAlmacenRFC(List<String> centros) throws Exception;
    CentroAlmacenRFCResponseDto getListaCentroAlmacen_v1(String centro) throws Exception;

}
