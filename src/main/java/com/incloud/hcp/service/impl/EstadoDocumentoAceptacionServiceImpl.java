package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.EstadoDocumentoAceptacion;
import com.incloud.hcp.repository.EstadoDocumentoAceptacionRepository;
import com.incloud.hcp.service.EstadoDocumentoAceptacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoDocumentoAceptacionServiceImpl implements EstadoDocumentoAceptacionService {

    EstadoDocumentoAceptacionRepository estadoDocumentoAceptacionRepository;

    @Autowired
    public EstadoDocumentoAceptacionServiceImpl(EstadoDocumentoAceptacionRepository estadoDocumentoAceptacionRepository) {
        this.estadoDocumentoAceptacionRepository = estadoDocumentoAceptacionRepository;
    }

    @Override
    public List<EstadoDocumentoAceptacion> getAllEstadoDocumentoAceptacion() {
        return estadoDocumentoAceptacionRepository.findAll();
    }
}
