package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.EstadoPrefactura;
import com.incloud.hcp.repository.EstadoPrefacturaRepository;
import com.incloud.hcp.service.EstadoPrefacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoPrefacturaServiceImpl implements EstadoPrefacturaService {

    private EstadoPrefacturaRepository estadoPrefacturaRepository;

    @Autowired
    public EstadoPrefacturaServiceImpl(EstadoPrefacturaRepository estadoPrefacturaRepository) {
        this.estadoPrefacturaRepository = estadoPrefacturaRepository;
    }

    @Override
    public List<EstadoPrefactura> getAllEstadoPrefacturas() {
        return estadoPrefacturaRepository.findAll();
    }
}
