package com.incloud.hcp.jco.balanza.GuiaRemision.service;

import com.incloud.hcp.jco.balanza.GuiaRemision.dto.GuiaRemisionResponseDTO;

public interface JCOGuiaRemisionService {
    GuiaRemisionResponseDTO grabarGuiaRemision(
            GuiaRemisionResponseDTO guiaRemisionResponseDTO
    ) throws Exception;
}
