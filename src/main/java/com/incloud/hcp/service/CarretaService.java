package com.incloud.hcp.service;

import com.incloud.hcp.domain.balanza.Carreta;
import com.incloud.hcp.jco.balanza.Carreta.dto.CarretaResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface CarretaService {


    List<Carreta> getAllCarreta();
    Optional<Carreta> getCarretaById(Integer idCarreta);

   Carreta getCarretaByDescripcion(String descripcion);
    Carreta getCarretaByCodigo(String codigo);
    ResponseEntity<Map> save(Carreta carreta);

    Carreta update(Integer carretaId, CarretaResponseDTO carretaResponseDTO);
    Carreta cambiarEstado(Integer carretaId) throws Exception;

}
