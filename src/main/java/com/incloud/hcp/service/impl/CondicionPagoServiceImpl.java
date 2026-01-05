package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.CondicionPago;
import com.incloud.hcp.repository.CondicionPagoReposity;
import com.incloud.hcp.service.CondicionPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CondicionPagoServiceImpl implements CondicionPagoService {

    private CondicionPagoReposity condicionPagoReposity;

    @Autowired
    public void setCondicionPagoReposity(CondicionPagoReposity condicionPagoReposity) {
        this.condicionPagoReposity = condicionPagoReposity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CondicionPago> getListAll() {
        return condicionPagoReposity.findAll();
    }
}
