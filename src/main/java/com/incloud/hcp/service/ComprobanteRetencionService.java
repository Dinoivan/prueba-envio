package com.incloud.hcp.service;

import com.incloud.hcp.jco.comprobanteRetencion.dto.ComprobanteRetencionDto;
import com.incloud.hcp.pdf.bean.ParameterComprobanteRetencionPdfDTO;

import java.util.Date;
import java.util.List;

public interface ComprobanteRetencionService {

    List<ComprobanteRetencionDto> getComprobanteRetencionListPorFechasAndSociedadAndRuc(Date fechaInicio, Date fechaFin, String sociedad, String ruc) throws Exception;

    String getComprobanteRetencionGenerateContent(ParameterComprobanteRetencionPdfDTO parameterComprobanteRetencionPdfDTO);

    String getComprobanteRetencionGeneratePdfContent(ComprobanteRetencionDto comprobanteRetencionDto);

    String getComprobanteRetencionBase64(ComprobanteRetencionDto comprobanteRetencionDto, String tipoArchivo);

}
