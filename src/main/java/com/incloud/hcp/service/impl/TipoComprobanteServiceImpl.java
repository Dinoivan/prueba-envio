package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.TipoComprobante;
import com.incloud.hcp.repository.TipoComprobanteRepository;
import com.incloud.hcp.service.TipoComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoComprobanteServiceImpl implements TipoComprobanteService {

    private TipoComprobanteRepository tipoComprobanteRepository;

    @Autowired
    public void setTipoComprobanteRepository(TipoComprobanteRepository tipoComprobanteRepository) {
        this.tipoComprobanteRepository = tipoComprobanteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoComprobante> getListAll() {
        return this.tipoComprobanteRepository.findAll();
    }
}
