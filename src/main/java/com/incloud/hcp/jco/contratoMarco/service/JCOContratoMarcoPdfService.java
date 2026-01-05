package com.incloud.hcp.jco.contratoMarco.service;


import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoPdfSapDto;

public interface JCOContratoMarcoPdfService {

    ContratoMarcoPdfSapDto extraerContratoMarcoPdfDtoRFC(String numeroContratoMarco) throws Exception;


}
