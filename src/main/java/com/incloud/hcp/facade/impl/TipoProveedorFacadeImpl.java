package com.incloud.hcp.facade.impl;

import com.incloud.hcp.domain.TipoProveedor;
import com.incloud.hcp.dto.TipoProveedorDto;
import com.incloud.hcp.facade.TipoProveedorFacade;
import com.incloud.hcp.populate.Populater;
import com.incloud.hcp.service.TipoProveedorService;
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
public class TipoProveedorFacadeImpl implements TipoProveedorFacade {

    private TipoProveedorService tipoProveedorService;
    private Populater<TipoProveedor, TipoProveedorDto> tipoProveedorPopulater;

    @Autowired
    public void setTipoProveedorService(TipoProveedorService tipoProveedorService) {
        this.tipoProveedorService = tipoProveedorService;
    }

    @Autowired
    @Qualifier(value = "tipoProveedorPopulate")
    public void setTipoProveedorPopulater(Populater tipoProveedorPopulater) {
        this.tipoProveedorPopulater = tipoProveedorPopulater;
    }

    @Override
    public List<TipoProveedorDto> getListAll() {
        List<TipoProveedorDto> listDto = new ArrayList<>();

        Optional.ofNullable(tipoProveedorService.getListAll())
                .ifPresent(list -> list.stream().map(tipoProveedorPopulater::toDto).forEach(listDto::add));

        return listDto;
    }
}
