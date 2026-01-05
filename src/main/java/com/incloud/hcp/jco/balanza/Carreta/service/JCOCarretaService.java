package com.incloud.hcp.jco.balanza.Carreta.service;

import com.incloud.hcp.jco.balanza.Carreta.dto.CarretaResponseDTO;

public interface JCOCarretaService {
    //    void extraerCarretaListRFC(String sign, String option, String low, String high) throws Exception;
    void extraerCarretaListRFC(boolean extraccionCarreta) throws Exception;

    CarretaResponseDTO actualizarCarreta(
            CarretaResponseDTO carretaResponseDTO
    ) throws Exception;

    CarretaResponseDTO grabarCarreta(
            CarretaResponseDTO carretaResponseDTO
    ) throws Exception;
}
