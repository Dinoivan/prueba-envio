package com.incloud.hcp.service;

import com.incloud.hcp.domain.balanza.Carreta;
import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChoferService {

    public List<Chofer>getAllChofer();
  Chofer getChoferByDni(String dni);
    Chofer getChoferByLicencia(String licencia);
    public ResponseEntity<Map>save(Chofer chofer);
    Chofer update(Integer choferId, ChoferResponseDTO choferResponseDTO);

    Chofer cambiarEstado(Integer choferId) throws Exception;

}
