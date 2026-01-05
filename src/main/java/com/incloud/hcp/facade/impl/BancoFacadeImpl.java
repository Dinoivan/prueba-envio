package com.incloud.hcp.facade.impl;

import com.incloud.hcp.domain.Banco;
import com.incloud.hcp.dto.BancoDto;
import com.incloud.hcp.facade.BancoFacade;
import com.incloud.hcp.populate.Populater;
import com.incloud.hcp.service.BancoService;
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
public class BancoFacadeImpl implements BancoFacade {

    private BancoService bancoService;
    private Populater<Banco, BancoDto> populater;

    @Autowired
    public void setBancoService(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    @Autowired
    @Qualifier(value = "bancoPopulate")
    public void setPopulater(Populater<Banco, BancoDto> populater) {
        this.populater = populater;
    }

    @Override
    public List<BancoDto> getListAll() {
        List<BancoDto> listDto = new ArrayList<>();

        Optional.ofNullable(bancoService).map(BancoService::getListAll)
                .ifPresent(list -> list.stream().map(populater::toDto).forEach(listDto::add));

        return listDto;
    }
}
