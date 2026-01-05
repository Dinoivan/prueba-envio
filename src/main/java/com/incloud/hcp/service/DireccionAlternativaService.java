package com.incloud.hcp.service;

import com.incloud.hcp.domain.balanza.Carreta;
import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.domain.balanza.DireccionAlternativa;
import com.incloud.hcp.domain.balanza.TicketPesaje;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface DireccionAlternativaService {

    List<DireccionAlternativa> getAllDireccionAlternativas();
    List<DireccionAlternativa> getfindByRuc(String ruc);

    DireccionAlternativa save(DireccionAlternativa direccionAlternativa);



}
