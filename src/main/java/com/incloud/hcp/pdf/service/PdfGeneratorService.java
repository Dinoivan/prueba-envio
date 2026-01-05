package com.incloud.hcp.pdf.service;

import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;
import com.incloud.hcp.pdf.bean.ParameterComprobanteRetencionPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterEntradaMercaderiaPdfDTO;
import com.incloud.hcp.pdf.bean.PrefacturaPdfDto;


public interface PdfGeneratorService {

    byte[] generateEntradaMercaderia(ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderiaPdfDTO);

    byte[] generateConformidadServicio(ParameterConformidadServicioPdfDTO parameterConformidadServicioPdfDTO);

    byte[] generateOrdenCompraPdfBytes(OrdenCompraPdfDto ordenCompraPdfDto);

    byte[] generateContratoMarcoPdfBytes(OrdenCompraPdfDto ordenCompraPdfDto);

    byte[] generatePrefacturaPdfBytes(PrefacturaPdfDto prefacturaPdfDto);

    byte[] generateComprobanteRetencion(ParameterComprobanteRetencionPdfDTO parameterComprobanteRetencionPdfDTO);
}
