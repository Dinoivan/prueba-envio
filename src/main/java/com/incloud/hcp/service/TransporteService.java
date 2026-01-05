package com.incloud.hcp.service;

import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.domain.balanza.Transporte;
import com.incloud.hcp.jco.balanza.Transporte.dto.TransporteResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TransporteService {

    List<Transporte> getAllTransporte();
    List<Transporte> getAllTransporteActivo();
    List<Transporte> getAllRemolque();
    Optional<Transporte> getTransporteById(Integer idTransporte);

    /*Optional<Transporte> getTransporteByPlaca(String placa);

    Optional<Transporte>getTransporteByModelo(String modelo);*/
    public ResponseEntity<Map>save(Transporte transporte);
    Transporte update(Integer transporteId, TransporteResponseDTO transporteResponseDTO);

    Transporte cambiarEstado(Integer transporteId) throws Exception;

}
