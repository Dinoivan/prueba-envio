package com.incloud.hcp.dto.mapper;

import com.incloud.hcp.domain.AdjuntoOrdenCompra;
import com.incloud.hcp.dto.AdjuntoOrdenCompraDto;

/**
 * Created by Administrador on 25/09/2017.
 */
public class AdjuntoOrdenCompraDTOMapper implements EntityDTOMapper<AdjuntoOrdenCompra, AdjuntoOrdenCompraDto> {
    @Override
    public AdjuntoOrdenCompra toEntity(AdjuntoOrdenCompraDto dto) {
        AdjuntoOrdenCompra x = new AdjuntoOrdenCompra();
        x.setIdAdjunto(dto.getIdAdjunto());
        x.setArchivoTipo(dto.getArchivoTipo());
        x.setArchivoNombre(dto.getArchivoNombre());
        x.setOrdencompra(dto.getOrdencompra());
        x.setArchivo(dto.getArchivo());
        return x;
    }

    @Override
    public AdjuntoOrdenCompraDto toDto(AdjuntoOrdenCompra entity) {
        AdjuntoOrdenCompraDto dto = new AdjuntoOrdenCompraDto();
        dto.setIdAdjunto(entity.getIdAdjunto());
        dto.setArchivoTipo(entity.getArchivoTipo());
        dto.setArchivoNombre(entity.getArchivoNombre());
        dto.setOrdencompra(entity.getOrdencompra());
        dto.setArchivo(entity.getArchivo());
        return dto;
    }
}
