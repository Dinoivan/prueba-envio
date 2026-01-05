package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.TipoPesaje;
import com.incloud.hcp.repository.TipoPesajeRepository;
import com.incloud.hcp.service.TipoPesajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TipoPesajeImpl implements TipoPesajeService {

    @Autowired
    private TipoPesajeRepository tipoPesajeRepository;

    @Override
    public List<TipoPesaje> getAllTipoPesaje() {
        return this.tipoPesajeRepository.listarPesajesActivos().stream().collect(Collectors.toList());
    }

    @Override
    public TipoPesaje cambiarEstado(Integer tipoPesajeId) throws Exception {
        Optional<TipoPesaje> tipoPesajeOptional = tipoPesajeRepository.findById(tipoPesajeId);
        if(!tipoPesajeOptional.isPresent()){
            throw new Exception("No se encontró el tipo pesaje con ID: " + tipoPesajeId);
        }
        TipoPesaje tipoPesaje = tipoPesajeOptional.get();
        tipoPesaje.setEstado(!tipoPesaje.getEstado());
        return tipoPesajeRepository.save(tipoPesaje);
    }

    @Override
    public TipoPesaje eliminarEstado(Integer tipoPesajeId) throws Exception {
        Optional<TipoPesaje> tipoPesajeOptional = tipoPesajeRepository.findById(tipoPesajeId);
        if(!tipoPesajeOptional.isPresent()){
            throw new Exception("No se encontró el tipo pesaje con ID: " + tipoPesajeId);
        }
        TipoPesaje tipoPesaje = tipoPesajeOptional.get();
        tipoPesaje.setDeleted(true);
        return tipoPesajeRepository.save(tipoPesaje);
    }
}
