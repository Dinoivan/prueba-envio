package com.incloud.hcp.facade;


import com.incloud.hcp.dto.HomologacionDto;

import java.util.List;

public interface HomologacionFacade {
    HomologacionDto guardar(HomologacionDto homologacionDto);

    List<HomologacionDto> getListAll();
    HomologacionDto getHomologacionDto(Integer idHomologacion);
}
