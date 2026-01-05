package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.TipoDocumentoAceptacion;
import com.incloud.hcp.repository.TipoDocumentoAceptacionRepository;
import com.incloud.hcp.service.TipoDocumentoAceptacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoDocumentoAceptacionServiceImpl implements TipoDocumentoAceptacionService {

    TipoDocumentoAceptacionRepository tipoDocumentoAceptacionRepository;

    @Autowired
    public TipoDocumentoAceptacionServiceImpl(TipoDocumentoAceptacionRepository tipoDocumentoAceptacionRepository) {
        this.tipoDocumentoAceptacionRepository = tipoDocumentoAceptacionRepository;
    }

    @Override
    public List<TipoDocumentoAceptacion> getAllTipoDocumentoAceptacion() {
        return tipoDocumentoAceptacionRepository.findAll();
    }
}
