package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.OrdenCompraDetalle;
import com.incloud.hcp.repository.OrdenCompraDetalleRepository;
import com.incloud.hcp.service.OrdenCompraDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenCompraDetalleServiceImpl implements OrdenCompraDetalleService {

    private OrdenCompraDetalleRepository ordenCompraDetalleRepository;

    @Autowired
    public OrdenCompraDetalleServiceImpl(OrdenCompraDetalleRepository ordenCompraDetalleRepository) {
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
    }

    @Override
    public List<OrdenCompraDetalle> getOrdenCompraDetalleListByIdOcLiberada(Integer idOrdenCompra,String ruc) {
        return ordenCompraDetalleRepository.getAllByIdOrdenCompraLiberada(idOrdenCompra, ruc);
    }
    @Override
    public List<OrdenCompraDetalle> getOCDetalleListByIdOcLiberada(Integer idOrdenCompra ) {
        return ordenCompraDetalleRepository.getByIdOrdenCompraLiberada(idOrdenCompra);
    }

    @Override
    public List<OrdenCompraDetalle> getOrdenCompraDetalleListByIdOc(Integer idOrdenCompra) {
        return ordenCompraDetalleRepository.getAllByIdOrdenCompra(idOrdenCompra);
    }
}
