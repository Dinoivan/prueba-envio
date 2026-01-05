package com.incloud.hcp.jco.documentoAceptacion.service;


import com.incloud.hcp.jco.documentoAceptacion.dto.SapTableItemDto;

import java.util.List;

public interface JCODocumentoAceptacionService {

    void extraerDocumentoAceptacionListRFC(String parametro1, String parametro2, boolean extraccionUnicoDocumento, boolean aprobarOrdenCompra, boolean enviarCorreoAprobacion) throws Exception;

    List<SapTableItemDto> extraerDataDocumentoAceptacionRFC(String parametro1, String parametro2, boolean unicoDocumentoAceptacion) throws Exception;

    boolean toggleDocumentoAceptacionExtractionProcessingState();

    boolean currentDocumentoAceptacionExtractionProcessingState();
}
