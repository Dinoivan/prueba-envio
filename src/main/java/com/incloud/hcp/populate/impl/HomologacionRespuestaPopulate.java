package com.incloud.hcp.populate.impl;

import com.incloud.hcp.domain.HomologacionRespuesta;
import com.incloud.hcp.dto.HomologacionRespuestaDto;
import com.incloud.hcp.populate.Populater;
import org.springframework.stereotype.Component;

@Component
public class HomologacionRespuestaPopulate implements Populater<HomologacionRespuesta, HomologacionRespuestaDto> {

    @Override
    public HomologacionRespuestaDto toDto(HomologacionRespuesta entity) {
        HomologacionRespuestaDto dto = new HomologacionRespuestaDto();
        dto.setIdHomologacionRespuesta(entity.getIdHomologacionRespuesta());
        dto.setPuntaje(entity.getPuntaje());
        dto.setRespuesta(entity.getRespuesta());
        return dto;
    }

    @Override
    public HomologacionRespuesta toEntity(HomologacionRespuestaDto dto) {
        HomologacionRespuesta entity = new HomologacionRespuesta();
        entity.setIdHomologacionRespuesta(dto.getIdHomologacionRespuesta());
        entity.setPuntaje(dto.getPuntaje());
        entity.setRespuesta(dto.getRespuesta());

        return entity;
    }
}
