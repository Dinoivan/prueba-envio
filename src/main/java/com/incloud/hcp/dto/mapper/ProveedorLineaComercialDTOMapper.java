package com.incloud.hcp.dto.mapper;

import com.incloud.hcp.domain.ProveedorLineaComercial;
import com.incloud.hcp.dto.LineaComercialDto;
import com.incloud.hcp.repository.LineaComercialRepository;

import java.util.Optional;

/**
 * Created by Joel on 02/09/2017.
 */

public class ProveedorLineaComercialDTOMapper implements EntityDTOMapper<ProveedorLineaComercial, LineaComercialDto> {

    private LineaComercialRepository lineaComercialRepository;

    public ProveedorLineaComercialDTOMapper(LineaComercialRepository lineaComercialRepository) {
        this.lineaComercialRepository = lineaComercialRepository;
    }

    @Override
    public ProveedorLineaComercial toEntity(LineaComercialDto dto) {
        if(dto == null) {
            return null;
        }

        ProveedorLineaComercial linea = new ProveedorLineaComercial();
        Optional.ofNullable(this.lineaComercialRepository.getLineaComercialById(dto.getIdLinea())).ifPresent(linea::setLineaComercial);
        Optional.ofNullable(this.lineaComercialRepository.getLineaComercialById(dto.getIdFamilia())).ifPresent(linea::setFamilia);
        Optional.ofNullable(this.lineaComercialRepository.getLineaComercialById(dto.getIdSubFamilia())).ifPresent(linea::setSubFamilia);
        linea.setOtrosLineaComercial(dto.getOtraLinea());
        return linea;
    }

    @Override
    public LineaComercialDto toDto(ProveedorLineaComercial entity) {

        if(entity == null) {
            return null;
        }

        LineaComercialDto dto = new LineaComercialDto();

        Optional.ofNullable(entity.getLineaComercial()).ifPresent(l -> {
            dto.setIdLinea(l.getIdLineaComercial());
            dto.setLinea(l.getDescripcion());
        });

        Optional.ofNullable(entity.getFamilia()).ifPresent(f -> {
            dto.setIdFamilia(f.getIdLineaComercial());
            dto.setFamilia(f.getDescripcion());
        });

        Optional.ofNullable(entity.getSubFamilia()).ifPresent(s -> {
            dto.setIdSubFamilia(s.getIdLineaComercial());
            dto.setSubFamilia(s.getDescripcion());
        });
        dto.setOtraLinea(entity.getOtrosLineaComercial());
        return dto;
    }
}
