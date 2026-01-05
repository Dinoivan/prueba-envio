package com.incloud.hcp.populate.impl;

import com.incloud.hcp.domain.TipoProveedor;
import com.incloud.hcp.dto.TipoProveedorDto;
import com.incloud.hcp.populate.Populater;
import org.springframework.stereotype.Component;

@Component
public class TipoProveedorPopulate implements Populater<TipoProveedor, TipoProveedorDto>{
    @Override
    public TipoProveedorDto toDto(TipoProveedor tipoProveedor) {
        TipoProveedorDto dto = new TipoProveedorDto();
        dto.setDescripcion(tipoProveedor.getDescripcion());
        dto.setIdTipoProveedor(tipoProveedor.getIdTipoProveedor());

        return dto;
    }

    @Override
    public TipoProveedor toEntity(TipoProveedorDto tipoProveedorDto) {
        TipoProveedor entity = new TipoProveedor();
        entity.setIdTipoProveedor(tipoProveedorDto.getIdTipoProveedor());
        entity.setDescripcion(tipoProveedorDto.getDescripcion());

        return entity;
    }
}
