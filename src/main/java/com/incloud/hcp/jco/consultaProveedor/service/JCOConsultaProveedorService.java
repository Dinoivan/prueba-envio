package com.incloud.hcp.jco.consultaProveedor.service;


import com.incloud.hcp.jco.consultaProveedor.dto.ConsultaProveedorRFCResponseDto;




public interface JCOConsultaProveedorService {

    ConsultaProveedorRFCResponseDto listaProveedorByRFC(String nroAcreedor, String fechaInicio, String fechaFin, String Email, String tipoPersona) throws Exception;


}


