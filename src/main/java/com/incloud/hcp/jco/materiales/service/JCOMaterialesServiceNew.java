package com.incloud.hcp.jco.materiales.service;

import com.incloud.hcp.jco.materiales.dto.MaterialesRFCResponseDto;


public interface JCOMaterialesServiceNew {

    MaterialesRFCResponseDto getListMaterialesRFC(String fechaInicio, String fechaFin) throws Exception;

}
