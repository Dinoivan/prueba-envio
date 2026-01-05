package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.almacen.GuiaDespachoDetalle;
import com.incloud.hcp.myibatis.mapper.GuiaDespachoDetalleMapper;
import com.incloud.hcp.service.GuiaDespachoDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuiaDespachoDetalleServiceImpl implements GuiaDespachoDetalleService {
    private GuiaDespachoDetalleMapper guiaDespachoDetalleMapper;

    @Autowired
    public GuiaDespachoDetalleServiceImpl(GuiaDespachoDetalleMapper guiaDespachoDetalleMapper) {
        this.guiaDespachoDetalleMapper = guiaDespachoDetalleMapper;
    }

    @Override
    public List<GuiaDespachoDetalle> getAllGuiaDepachoDetalleById(Integer id) {
        return guiaDespachoDetalleMapper.getAllGuiaDepachoDetalleByIdMap(id);
    }
}
