package com.incloud.hcp.service;

import com.incloud.hcp.domain.Sociedad;

public interface SociedadService {

    Sociedad getOneByRucCliente (String rucCliente);

    Sociedad getOneByCodigoSociedad (String codigoSociedad);
}
