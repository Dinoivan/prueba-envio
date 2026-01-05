package com.incloud.hcp.dto;

import com.incloud.hcp.domain.Prefactura;
import com.incloud.hcp.jco.prefactura.dto.PrefacturaAnuladaDto;

import java.util.List;

public class PrefacturaAnuladaRespuestaDto {
    private List<PrefacturaAnuladaDto> prefacturaAnuladaSapDtoList;
    private List<Prefactura> prefacturaModificadaList;


    public List<PrefacturaAnuladaDto> getPrefacturaAnuladaSapDtoList() {
        return prefacturaAnuladaSapDtoList;
    }

    public void setPrefacturaAnuladaSapDtoList(List<PrefacturaAnuladaDto> prefacturaAnuladaSapDtoList) {
        this.prefacturaAnuladaSapDtoList = prefacturaAnuladaSapDtoList;
    }

    public List<Prefactura> getPrefacturaModificadaList() {
        return prefacturaModificadaList;
    }

    public void setPrefacturaModificadaList(List<Prefactura> prefacturaModificadaList) {
        this.prefacturaModificadaList = prefacturaModificadaList;
    }
}