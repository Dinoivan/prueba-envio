package com.incloud.hcp.jco.servicios.service;

import com.incloud.hcp.jco.servicios.dto.ServiciosRFCResponseDto;

public interface JCOServiciosService {

    ServiciosRFCResponseDto actualizarMaterialesRFC(String fechaInicio, String fechaFin) throws Exception;
}
