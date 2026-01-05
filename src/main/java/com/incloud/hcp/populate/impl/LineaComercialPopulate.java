package com.incloud.hcp.populate.impl;

import com.incloud.hcp.bean.Linea;
import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.populate.Populater;
import org.springframework.stereotype.Component;

@Component
public class LineaComercialPopulate implements Populater<LineaComercial, Linea> {
    @Override
    public Linea toDto(LineaComercial entity) {
        Linea dto = new Linea();
        dto.setId(entity.getIdLineaComercial());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }

    @Override
    public LineaComercial toEntity(Linea dto) {
        LineaComercial entity = new LineaComercial();
        entity.setIdLineaComercial(dto.getId());
        entity.setDescripcion(dto.getDescripcion());
        return entity;
    }
}
