package com.incloud.hcp.jco.comprobanteRetencion.service;

import com.incloud.hcp.jco.comprobanteRetencion.dto.ComprobanteRetencionDto;

import java.util.List;


public interface JCOComprobanteRetencionService {

    List<ComprobanteRetencionDto> extraerComprobanteRetencionListRFC(String fechaInicio, String fechaFin, String numeroComprobantePago, String ruc) throws Exception;

}
