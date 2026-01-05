package com.incloud.hcp.jco.servicios.service;


import com.incloud.hcp.jco.servicios.dto.ServiciosRFCResponseDto;


public interface JCOServiciosServiceNew {

    ServiciosRFCResponseDto getListServicios(String fechaInicio,String fechaFin) throws Exception;

}
