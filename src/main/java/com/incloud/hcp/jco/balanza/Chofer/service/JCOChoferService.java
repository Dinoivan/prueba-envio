package com.incloud.hcp.jco.balanza.Chofer.service;


import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferResponseDTO;

public interface JCOChoferService {

//    void extraerChoferListRFC(String sign, String option, String low, String high) throws Exception;
    void extraerChoferListRFC(boolean extraerChofer) throws Exception;

    ChoferResponseDTO actualizarChofer(
            ChoferResponseDTO choferResponseDTO
    ) throws Exception;

    ChoferResponseDTO grabarChofer(
            ChoferResponseDTO choferResponseDTO
    ) throws Exception;
}
