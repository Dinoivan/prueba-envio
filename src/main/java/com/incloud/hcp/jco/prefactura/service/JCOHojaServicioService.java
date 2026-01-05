package com.incloud.hcp.jco.prefactura.service;

import com.incloud.hcp.jco.prefactura.dto.HojaServicioSubPosicionDto;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaRFCRequestDto;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaRFCResponseDto;

import java.util.List;


public interface JCOHojaServicioService {

    List<HojaServicioSubPosicionDto> extraerHojaServicioSubPosicionListRFC(String numeroHojaServicio) throws Exception;
}
