package com.incloud.hcp.jco.materiales.service;

import com.incloud.hcp.jco.materiales.dto.MaterialesRFCResponseDto;


public interface JCOMaterialesService {

    MaterialesRFCResponseDto actualizarMaterialesRFC(String fechaInicio, String fechaFin) throws Exception;

}
