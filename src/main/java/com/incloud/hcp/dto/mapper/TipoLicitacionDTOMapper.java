package com.incloud.hcp.dto.mapper;

import com.incloud.hcp.domain.TipoLicitacion;
import com.incloud.hcp.dto.TipoLicitacionDto;

/**
 * Created by MARCELO on 21/11/2017.
 */
public class TipoLicitacionDTOMapper implements EntityDTOMapper<TipoLicitacion, TipoLicitacionDto> {
    @Override
    public TipoLicitacion toEntity(TipoLicitacionDto dto) {
        TipoLicitacion entity = new TipoLicitacion();
        entity.setNivel(dto.getNivel());
        entity.setDescripcion(dto.getDescripcion());
        entity.setIdPadre(dto.getIdPadre());
        return entity;
    }

    @Override
    public TipoLicitacionDto toDto(TipoLicitacion entity) {
        TipoLicitacionDto dto = new TipoLicitacionDto();
        dto.setNivel(entity.getNivel());
        dto.setDescripcion(entity.getDescripcion());
        dto.setIdTipoLicitacion(entity.getIdTipoLicitacion());
        dto.setIdPadre(entity.getIdPadre());
        return dto;
    }
}
