package com.incloud.hcp.populate.impl;

import com.incloud.hcp.bean.LineaFamilia;
import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.populate.Populater;
import org.springframework.stereotype.Component;

@Component
public class LineaComercialFamiliaPopulate implements Populater<LineaComercial, LineaFamilia> {
    @Override
    public LineaFamilia toDto(LineaComercial entity) {
        LineaFamilia dto = new LineaFamilia();
        dto.setId(entity.getIdLineaComercial());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }

    @Override
    public LineaComercial toEntity(LineaFamilia dto) {
        LineaComercial entity = new LineaComercial();
        entity.setIdLineaComercial(dto.getId());
        entity.setDescripcion(dto.getDescripcion());
        return entity;
    }
}
