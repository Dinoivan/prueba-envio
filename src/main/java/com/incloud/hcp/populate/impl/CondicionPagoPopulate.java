package com.incloud.hcp.populate.impl;

import com.incloud.hcp.domain.CondicionPago;
import com.incloud.hcp.dto.CondicionPagoDto;
import com.incloud.hcp.populate.Populater;
import org.springframework.stereotype.Component;

@Component
public class CondicionPagoPopulate implements Populater<CondicionPago, CondicionPagoDto>{

    @Override
    public CondicionPagoDto toDto(CondicionPago condicionPago) {
        CondicionPagoDto dto = new CondicionPagoDto();
        dto.setIdCondicionPago(condicionPago.getIdCondicionPago());
        dto.setDescripcion(condicionPago.getDescripcion());
        return dto;
    }

    @Override
    public CondicionPago toEntity(CondicionPagoDto condicionPagoDto) {
        CondicionPago entity = new CondicionPago();
        entity.setIdCondicionPago(condicionPagoDto.getIdCondicionPago());
        entity.setDescripcion(condicionPagoDto.getDescripcion());

        return entity;
    }
}
