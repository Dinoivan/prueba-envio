package com.incloud.hcp.service;

import com.incloud.hcp.domain.balanza.DireccionAlternativa;

import java.util.List;


public interface DireccionAlternativaService {

    List<DireccionAlternativa> getAllDireccionAlternativas();
    List<DireccionAlternativa> getfindByRuc(String ruc);

    DireccionAlternativa save(DireccionAlternativa direccionAlternativa);



}
