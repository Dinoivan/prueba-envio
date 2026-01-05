package com.incloud.hcp.jco.prefactura.service;

import com.incloud.hcp.dto.PrefacturaActualizarDto;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaAnuladaDto;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaRFCRequestDto;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaRFCResponseDto;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaRegistradaSapDto;

import java.util.List;


public interface JCOPrefacturaService {

    PrefacturaRFCResponseDto registrarPrefacturaRFC(PrefacturaRFCRequestDto prefacturaRFCRequestDto) throws Exception;

    List<PrefacturaAnuladaDto> obtenerPrefacturaAnuladaListRFC(String fechaInicio, String fechaFin) throws Exception;

    List<PrefacturaRegistradaSapDto> obtenerPrefacturaRegistradaSapListRFC(List<PrefacturaActualizarDto> prefacturaActualizarDtoList) throws Exception;
}
