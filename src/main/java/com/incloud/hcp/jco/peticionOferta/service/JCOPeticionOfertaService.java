package com.incloud.hcp.jco.peticionOferta.service;

import com.incloud.hcp.jco.peticionOferta.dto.PeticionOfertaRFCRequestDto;
import com.incloud.hcp.jco.peticionOferta.dto.PeticionOfertaRFCResponseDto;
import com.incloud.hcp.sap.SapLog;

import java.util.List;

public interface JCOPeticionOfertaService {


    PeticionOfertaRFCResponseDto getPeticionOfertaResponseByCodigo(String numeroSolicitud, boolean obtainFullPositionList) throws Exception;

    List<SapLog> modificarPeticionOferta(PeticionOfertaRFCRequestDto peticionOfertaRFCRequestDto) throws Exception;
}
