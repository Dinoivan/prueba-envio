package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Moneda;
import com.incloud.hcp.repository.MonedaRepository;
import com.incloud.hcp.service.MonedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonedaServiceImpl implements MonedaService {
    private MonedaRepository monedaRepository;

    @Autowired
    public void setMonedaRepository(MonedaRepository monedaRepository) {
        this.monedaRepository = monedaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Moneda> getListAll() {
        return monedaRepository.findAll();
    }
}