package com.incloud.hcp.jco.balanza.Transportista.service;

import com.incloud.hcp.domain.balanza.Transportista;
import com.incloud.hcp.jco.balanza.Transportista.dto.TransportistaBlzFiltroBusquedaDTO;
import com.incloud.hcp.jco.balanza.Transportista.dto.TransportistaBlzFiltroDTO;

import java.util.List;

public interface TransportistaService {
    List<Transportista> getAllTransportistas();
    Transportista createTransportista(Transportista transportista);

    void extraerTransportistasListRFC(TransportistaBlzFiltroDTO dto) throws Exception;

    Transportista cambiarEstado(Long transportistaId) throws Exception;
    List<Transportista> getAllTransportistasBlzByFiltro(TransportistaBlzFiltroBusquedaDTO dto);
}
