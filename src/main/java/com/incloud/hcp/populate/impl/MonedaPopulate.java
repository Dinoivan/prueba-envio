package com.incloud.hcp.populate.impl;

import com.incloud.hcp.domain.Moneda;
import com.incloud.hcp.dto.MonedaDto;
import com.incloud.hcp.populate.Populater;
import org.springframework.stereotype.Component;

@Component
public class MonedaPopulate implements Populater<Moneda, MonedaDto> {
    @Override
    public MonedaDto toDto(Moneda moneda) {
        MonedaDto dto = new MonedaDto();
        dto.setIdMoneda(moneda.getIdMoneda());
        dto.setDescripcion(moneda.getTextoBreve());
        return dto;
    }

    @Override
    public Moneda toEntity(MonedaDto monedaDto) {
        Moneda entity = new Moneda();
        entity.setIdMoneda(monedaDto.getIdMoneda());
        entity.setTextoBreve(monedaDto.getDescripcion());
        entity.setSigla(monedaDto.getDescripcion());
        return entity;
    }
}
