package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.DocumentoAceptacionDetalle;
import com.incloud.hcp.repository.DocumentoAceptacionDetalleRepository;
import com.incloud.hcp.service.DocumentoAceptacionDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoAceptacionDetalleServiceImpl implements DocumentoAceptacionDetalleService {

    private DocumentoAceptacionDetalleRepository documentoAceptacionDetalleRepository;

    @Autowired
    public DocumentoAceptacionDetalleServiceImpl(DocumentoAceptacionDetalleRepository documentoAceptacionDetalleRepository) {
        this.documentoAceptacionDetalleRepository = documentoAceptacionDetalleRepository;
    }

    @Override
    public List<DocumentoAceptacionDetalle> getDocumentoAceptacionDetalleNoAnuladasListById(Integer idDocumentoAceptacion) {
        return documentoAceptacionDetalleRepository.getDocumentoAceptacionDetalleNoAnuladasListByIdDocumentoAceptacion(idDocumentoAceptacion);
    }

    @Override
    public List<DocumentoAceptacionDetalle> getDocumentoAceptacionDetalleConAnuladasListById(Integer idDocumentoAceptacion) {
        return documentoAceptacionDetalleRepository.findDocumentoAceptacionDetalleByIdDocumentoAceptacion(idDocumentoAceptacion);
    }
}
