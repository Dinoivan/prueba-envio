package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.almacen.OrdenDespachoDetalle;
import com.incloud.hcp.dto.OrdenDespachoDetalleDto;
import com.incloud.hcp.myibatis.mapper.OrdenDespachoDetalleMapper;
import com.incloud.hcp.myibatis.mapper.OrdenDespachoMapper;
import com.incloud.hcp.repository.OrdenDespachoDetalleRepository;
import com.incloud.hcp.service.OrdenDespachoDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenDespachoDetalleServiceImpl implements OrdenDespachoDetalleService {

    private OrdenDespachoDetalleRepository ordenDespachoDetalleRepository;
    private OrdenDespachoDetalleMapper ordenDespachoDetalleMapper;

    @Autowired
    public OrdenDespachoDetalleServiceImpl(OrdenDespachoDetalleRepository ordenDespachoDetalleRepository,
                                           OrdenDespachoDetalleMapper ordenDespachoDetalleMapper) {
        this.ordenDespachoDetalleRepository = ordenDespachoDetalleRepository;
        this.ordenDespachoDetalleMapper = ordenDespachoDetalleMapper;
    }

    @Override
    public List<OrdenDespachoDetalle> getOrdenDespachoDetalleListByIdOcLiberada(Integer idOrdenDespacho, String ruc) {
        return ordenDespachoDetalleRepository.getAllByIdOrdenDespachoLiberada(idOrdenDespacho, ruc);
    }
    @Override
    public List<OrdenDespachoDetalle> getOCDetalleListByIdOcLiberada(Integer idOrdenDespacho ) {
        return ordenDespachoDetalleRepository.getByIdOrdenDespachoLiberada(idOrdenDespacho);
    }

    @Override
    public List<OrdenDespachoDetalleDto> getOrdenDespachoDetalleListByOcLiberada(String numeroOrdenCompra, String ruc) {
        return ordenDespachoDetalleMapper.getAllByIdOrdenDespachoLiberada(numeroOrdenCompra, ruc);
    }

    @Override
    public List<OrdenDespachoDetalle> getOrdenDespachoDetalleListByIdOc(Integer idOrdenDespacho) {
        return ordenDespachoDetalleRepository.getAllByIdOrdenDespacho(idOrdenDespacho);
    }
}
