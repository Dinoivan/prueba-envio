package com.incloud.hcp.dto.mapper;

import com.incloud.hcp.domain.ProveedorCatalogo;
import com.incloud.hcp.dto.ProveedorCatalogoDto;

/**
 * Created by Administrador on 25/09/2017.
 */
public class ProveedorCatalogoDTOMapper implements EntityDTOMapper<ProveedorCatalogo, ProveedorCatalogoDto> {
    @Override
    public ProveedorCatalogo toEntity(ProveedorCatalogoDto dto) {
        ProveedorCatalogo catalogo = new ProveedorCatalogo();
        catalogo.setId(dto.getId());
        catalogo.setArchivoId(dto.getArchivoId());
        catalogo.setRutaCatalogo(dto.getRutaCatalogo());
        catalogo.setArchivoTipo(dto.getArchivoTipo());
        catalogo.setArchivoNombre(dto.getArchivoNombre());
        return catalogo;
    }

    @Override
    public ProveedorCatalogoDto toDto(ProveedorCatalogo entity) {
        ProveedorCatalogoDto dto = new ProveedorCatalogoDto();
        dto.setId(entity.getId());
        dto.setArchivoId(entity.getArchivoId());
        dto.setArchivoNombre(entity.getArchivoNombre());
        dto.setArchivoTipo(entity.getArchivoTipo());
        dto.setRutaCatalogo(entity.getRutaCatalogo());
        return dto;
    }
}
