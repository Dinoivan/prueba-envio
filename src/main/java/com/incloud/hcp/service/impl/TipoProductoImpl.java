package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.TipoProducto;
import com.incloud.hcp.repository.TipoProductoRepository;
import com.incloud.hcp.service.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoProductoImpl implements TipoProductoService {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Override
    public List<TipoProducto> getAllTipoProducto() {
        return this.tipoProductoRepository.listaTipoProductos();
    }

    @Override
    public TipoProducto cambiarEstado(Integer tipoProductoId) throws Exception {
        Optional<TipoProducto> tipoProductoOptional = tipoProductoRepository.findById(tipoProductoId);
        if(!tipoProductoOptional.isPresent()){
            throw new Exception("No se encontró el tipo producto con ID: " + tipoProductoId);
        }
        TipoProducto tipoProducto = tipoProductoOptional.get();
        tipoProducto.setEstado(!tipoProducto.getEstado());
        return tipoProductoRepository.save(tipoProducto);
    }
    @Override
    public TipoProducto eliminarTipo(Integer tipoProductoId) throws Exception {
        Optional<TipoProducto> tipoProductoOptional = tipoProductoRepository.findById(tipoProductoId);
        if(!tipoProductoOptional.isPresent()){
            throw new Exception("No se encontró el tipo producto con ID: " + tipoProductoId);
        }
        TipoProducto tipoProducto = tipoProductoOptional.get();
        tipoProducto.setDeleted(true);
        return tipoProductoRepository.save(tipoProducto);
    }
}
