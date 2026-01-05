package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.RubroBien;
import com.incloud.hcp.myibatis.mapper.RubroBienMapper;
import com.incloud.hcp.service.RubroBienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RubroBienServiceImpl implements RubroBienService {

    @Autowired
    private RubroBienMapper rubroBienMapper;


    @Override
    public List<RubroBien> listarArticulosNuevos() {
        return rubroBienMapper.nuevosGruposArticulos();
    }

    @Override
    public List<RubroBien> listarArticulosActualizados() {
        return rubroBienMapper.actualizadosGruposArticulos();
    }
}
