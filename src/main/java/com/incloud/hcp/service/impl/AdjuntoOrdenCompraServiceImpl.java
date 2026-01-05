package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.AdjuntoOrdenCompra;
import com.incloud.hcp.repository.AdjuntoOrdenCompraRepository;
import com.incloud.hcp.service.AdjuntoOrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrador on 25/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class AdjuntoOrdenCompraServiceImpl implements AdjuntoOrdenCompraService {

    @Autowired
    private AdjuntoOrdenCompraRepository adjuntoOrdenCompraRepository;

    @Override
    public AdjuntoOrdenCompra save(AdjuntoOrdenCompra adjuntoOrdenCompra) {
        return adjuntoOrdenCompraRepository.save(adjuntoOrdenCompra);
    }

    /*@Override
    public List<AdjuntoOrdenCompra> getProveedorCatalogoByIdProveedor(String ordencompra) {
        return adjuntoOrdenCompraRepository.getProveedorCatalogoByIdProveedor(ordencompra);
    }*/

    /*@Override
    public List<AdjuntoOrdenCompraDto> getListCatalogoDtoByIdProveedor(Integer idProveedor) {
        List<AdjuntoOrdenCompraDto> list = new ArrayList<>();
        AdjuntoOrdenCompraDTOMapper dtoMapper = new AdjuntoOrdenCompraDTOMapper();
        Optional.ofNullable(adjuntoOrdenCompraRepository)
                .map(r -> r.getProveedorCatalogoByIdProveedor(idProveedor))
                .ifPresent(l -> l.stream().map(dtoMapper::toDto).forEach(list::add));
        return list;
    }*/

    @Override
    public void deleteCatalogoByIdProveedorCatalogoById(Integer idProveedor, String archivoId) {
        /*adjuntoOrdenCompraRepository.deleteCatalogoByIdProveedorCatalogoById(idProveedor, archivoId);*/
    }
}
