package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Sociedad;
import com.incloud.hcp.repository.SociedadRepository;
import com.incloud.hcp.service.SociedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SociedadServiceImpl implements SociedadService {

    @Autowired
    private SociedadRepository sociedadRepository;

    @Override
    public Sociedad getOneByRucCliente(String rucCliente) {
        return sociedadRepository.getOneByRuc(rucCliente);
    }

    @Override
    public Sociedad getOneByCodigoSociedad(String codigoSociedad) {
        return sociedadRepository.getByCodigoSociedad(codigoSociedad);
    }
}
