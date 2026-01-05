package com.incloud.hcp.jco.EstadoDocumentoAceptacion.services;

import com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto.EstadoDocumentoAceptacionResponse;
import com.incloud.hcp.jco.EstadoDocumentoAceptacion.dto.RangeSap;

import java.util.List;

public interface JCOEstadoDocumentoAceptacionService {
    List<EstadoDocumentoAceptacionResponse> extraerEstadoDocumentoAceptacionRFC(List<RangeSap> rangeSap) throws Exception;
}
