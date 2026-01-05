package com.incloud.hcp.populate.impl;

import com.incloud.hcp.domain.TipoComprobante;
import com.incloud.hcp.dto.TipoComprobanteDto;
import com.incloud.hcp.populate.Populater;
import org.springframework.stereotype.Component;

@Component
public class TipoComprobantePopulate implements Populater<TipoComprobante, TipoComprobanteDto>{
    @Override
    public TipoComprobanteDto toDto(TipoComprobante tipoComprobante) {
        TipoComprobanteDto dto = new TipoComprobanteDto();
        dto.setIdTipoComprobante(tipoComprobante.getIdTipoComprobante());
        dto.setDescripcion(tipoComprobante.getDescripcion());
        return dto;
    }

    @Override
    public TipoComprobante toEntity(TipoComprobanteDto tipoComprobanteDto) {
        TipoComprobante entity = new TipoComprobante();
        entity.setIdTipoComprobante(tipoComprobanteDto.getIdTipoComprobante());
        entity.setDescripcion(tipoComprobanteDto.getDescripcion());
        return entity;
    }
}
