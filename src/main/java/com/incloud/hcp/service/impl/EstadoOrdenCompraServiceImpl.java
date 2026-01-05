package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.EstadoOrdenCompra;
import com.incloud.hcp.repository.EstadoOrdenCompraRepository;
import com.incloud.hcp.service.EstadoOrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoOrdenCompraServiceImpl implements EstadoOrdenCompraService {

    EstadoOrdenCompraRepository estadoOrdenCompraRepository;

    @Autowired
    public EstadoOrdenCompraServiceImpl(EstadoOrdenCompraRepository estadoOrdenCompraRepository) {
        this.estadoOrdenCompraRepository = estadoOrdenCompraRepository;
    }

    @Override
    public List<EstadoOrdenCompra> getAllEstadoOrdenCompra() {
        return estadoOrdenCompraRepository.findAll();
    }
}
