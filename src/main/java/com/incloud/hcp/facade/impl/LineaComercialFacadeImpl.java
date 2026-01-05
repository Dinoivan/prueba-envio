package com.incloud.hcp.facade.impl;

import com.incloud.hcp.bean.Linea;
import com.incloud.hcp.bean.LineaFamilia;
import com.incloud.hcp.bean.LineaSubFamilia;
import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.facade.LineaComercialFacade;
import com.incloud.hcp.populate.Populater;
import com.incloud.hcp.service.LineaComercialService;
import com.incloud.hcp.util.constant.LineaComercialConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LineaComercialFacadeImpl implements LineaComercialFacade {

    private Populater<LineaComercial, Linea> lineaPopulate;
    private Populater<LineaComercial, LineaFamilia> familiaPopulate;
    private Populater<LineaComercial, LineaSubFamilia> subFamiliaPopulate;

    private LineaComercialService lineaComercialService;

    @Autowired
    @Qualifier(value = "lineaComercialPopulate")
    public void setLineaPopulate(Populater<LineaComercial, Linea> lineaPopulate) {
        this.lineaPopulate = lineaPopulate;
    }

    @Autowired
    @Qualifier(value = "lineaComercialFamiliaPopulate")
    public void setFamiliaPopulate(Populater<LineaComercial, LineaFamilia> familiaPopulate) {
        this.familiaPopulate = familiaPopulate;
    }

    @Autowired
    @Qualifier(value = "lineaComercialSubFamiliaPopulate")
    public void setSubFamiliaPopulate(Populater<LineaComercial, LineaSubFamilia> subFamiliaPopulate) {
        this.subFamiliaPopulate = subFamiliaPopulate;
    }

    @Autowired
    public void setLineaComercialService(LineaComercialService lineaComercialService) {
        this.lineaComercialService = lineaComercialService;
    }

    @Override
    public List<Linea> getListLineaWithoutIndGeneral() {
        List<Linea> listDto = new ArrayList<>();
        Optional.ofNullable(this.lineaComercialService.getListByNivelAndWithoutIndGeneral(LineaComercialConstant.LINEA_COMERCIAL))
                .ifPresent(list -> list.stream().map(lineaPopulate::toDto).forEach(listDto::add));
        return listDto;
    }

    @Override
    public List<Linea> getListLinea() {
        List<Linea> listDto = new ArrayList<>();
        Optional.ofNullable(this.lineaComercialService.getListByNivel(LineaComercialConstant.LINEA_COMERCIAL))
                .ifPresent(list -> list.stream().map(lineaPopulate::toDto).forEach(listDto::add));
        return listDto;
    }

    @Override
    public List<LineaFamilia> getLineaFamilia(Integer idLinea) {
        List<LineaFamilia> listDto = new ArrayList<>();
        Optional.ofNullable(this.lineaComercialService.getListByIdParent(idLinea))
                .ifPresent(list -> list.stream().map(familiaPopulate::toDto).forEach(listDto::add));
        return listDto;
    }



    @Override
    public List<LineaSubFamilia> getLineaSubFamilia(Integer idFamilia) {
        List<LineaSubFamilia> listDto = new ArrayList<>();
        Optional.ofNullable(this.lineaComercialService.getListByIdParent(idFamilia))
                .ifPresent(list -> list.stream().map(subFamiliaPopulate::toDto).forEach(listDto::add));
        return listDto;
    }
}
