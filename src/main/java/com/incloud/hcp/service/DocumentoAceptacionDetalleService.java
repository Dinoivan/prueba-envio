package com.incloud.hcp.service;

import com.incloud.hcp.domain.DocumentoAceptacionDetalle;

import java.util.List;

public interface DocumentoAceptacionDetalleService {

    List<DocumentoAceptacionDetalle> getDocumentoAceptacionDetalleNoAnuladasListById(Integer idDocumentoAceptacion);

    List<DocumentoAceptacionDetalle> getDocumentoAceptacionDetalleConAnuladasListById(Integer idDocumentoAceptacion);
}
