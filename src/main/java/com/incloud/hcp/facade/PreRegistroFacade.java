package com.incloud.hcp.facade;

import com.incloud.hcp.dto.PreRegistroProveedorDto;

import java.util.List;


public interface PreRegistroFacade {
    PreRegistroProveedorDto save(PreRegistroProveedorDto preRegistro) throws Exception;

    PreRegistroProveedorDto updateSearchSunat(PreRegistroProveedorDto dto);

    PreRegistroProveedorDto getPreRegistroByIdHcp(String idHcp);

    PreRegistroProveedorDto getPreRegistroById(Integer id);

    List<PreRegistroProveedorDto> getListSolicitudPendiente();

    List<PreRegistroProveedorDto> getListSolicitudPendiente(String idHcp);

    PreRegistroProveedorDto aprobarSolicitud(Integer idPreRegistro);

    PreRegistroProveedorDto reprobarSolicitud(Integer idPreRegistro);
}
