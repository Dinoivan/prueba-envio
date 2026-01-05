package com.incloud.hcp.jco.balanza.MaestroCli.service;

import com.incloud.hcp.domain.balanza.Cliente;
import com.incloud.hcp.jco.balanza.MaestroCli.dto.MaestroCliImport;
import com.incloud.hcp.jco.balanza.MaestroCli.dto.MaestroCliResponse;

import java.util.List;

public interface JCOMaestroCliService {
    List<MaestroCliResponse> consultaMaestro(MaestroCliImport maestroCliImport) throws Exception;
    Cliente cambiarEstado(Long clienteId) throws Exception;

    List<Cliente> findAll();
}
