package com.incloud.hcp.facade.impl;

import com.incloud.hcp.domain.TipoComprobante;
import com.incloud.hcp.dto.TipoComprobanteDto;
import com.incloud.hcp.facade.TipoComprobanteFacade;
import com.incloud.hcp.populate.Populater;
import com.incloud.hcp.service.TipoComprobanteService;
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
public class TipoComprobanteFacadeImpl implements TipoComprobanteFacade {

    private TipoComprobanteService tipoComprobanteService;
    private Populater<TipoComprobante, TipoComprobanteDto> populater;

    @Autowired
    @Qualifier(value = "tipoComprobantePopulate")
    public void setPopulater(Populater<TipoComprobante, TipoComprobanteDto> populater) {
        this.populater = populater;
    }

    @Autowired
    public void setTipoComprobanteService(TipoComprobanteService tipoComprobanteService) {
        this.tipoComprobanteService = tipoComprobanteService;
    }

    @Override
    public List<TipoComprobanteDto> getListAll() {
        List<TipoComprobanteDto> listDto = new ArrayList<>();
        Optional.ofNullable(tipoComprobanteService).map(TipoComprobanteService::getListAll)
                .ifPresent(list -> list.stream().map(populater::toDto).forEach(listDto::add));
        return listDto;
    }
}
