package com.incloud.hcp.facade.impl;

import com.incloud.hcp.domain.CondicionPago;
import com.incloud.hcp.dto.CondicionPagoDto;
import com.incloud.hcp.facade.CondicionPagoFacade;
import com.incloud.hcp.populate.Populater;
import com.incloud.hcp.service.CondicionPagoService;
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
public class CondicionPagoFacadeImpl implements CondicionPagoFacade {

    private CondicionPagoService condicionPagoService;
    private Populater<CondicionPago, CondicionPagoDto> populater;

    @Autowired
    @Qualifier(value = "condicionPagoPopulate")
    public void setPopulater(Populater<CondicionPago, CondicionPagoDto> condicionPagoPopulater) {
        this.populater = condicionPagoPopulater;
    }

    @Autowired
    public void setCondicionPagoService(CondicionPagoService condicionPagoService) {
        this.condicionPagoService = condicionPagoService;
    }

    @Override
    public List<CondicionPagoDto> getListAll() {
        List<CondicionPagoDto> listDto = new ArrayList<>();
        Optional.ofNullable(condicionPagoService).map(CondicionPagoService::getListAll)
                .ifPresent(list -> list.stream().map(populater::toDto).forEach(listDto::add));
        return listDto;
    }
}
