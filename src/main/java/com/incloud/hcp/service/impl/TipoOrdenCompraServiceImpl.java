package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.TipoOrdenCompra;
import com.incloud.hcp.repository.TipoOrdenCompraRepository;
import com.incloud.hcp.service.TipoOrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoOrdenCompraServiceImpl implements TipoOrdenCompraService {

    private TipoOrdenCompraRepository tipoOrdenCompraRepository;

    @Autowired
    public TipoOrdenCompraServiceImpl(TipoOrdenCompraRepository tipoOrdenCompraRepository) {
        this.tipoOrdenCompraRepository = tipoOrdenCompraRepository;
    }

    @Override
    public List<TipoOrdenCompra> getAllTipoOrdenCompra() {
        return tipoOrdenCompraRepository.findAll();
    }
}
