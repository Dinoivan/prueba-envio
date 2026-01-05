package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.LineaFamilia;
import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.myibatis.mapper.LineaComercialMapper;
import com.incloud.hcp.repository.LineaComercialRepository;
import com.incloud.hcp.service.LineaComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 28/08/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class LineaComercialServiceImpl implements LineaComercialService {

    private LineaComercialRepository lineaComercialRepository;
    private LineaComercialMapper lineaComercialMapper;

    @Autowired
    public void setLineaComercialRepository(LineaComercialRepository lineaComercialRepository) {
        this.lineaComercialRepository = lineaComercialRepository;
    }

    @Autowired
    public void setLineaComercialMapper(LineaComercialMapper lineaComercialMapper) {
        this.lineaComercialMapper = lineaComercialMapper;
    }

    @Override
    public LineaComercial getByIdLineaComercial(Integer idLineaComercial) {
        return this.lineaComercialRepository.getLineaComercialById(idLineaComercial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineaComercial> getListByIdParent(Integer idParent) {
        return this.lineaComercialRepository.getListByIdParent(idParent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineaComercial> getListByNivel(Integer nivel) {
        return this.lineaComercialRepository.getListByNivel(nivel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineaComercial> getListByNivelAndWithoutIndGeneral(Integer nivel) {
        return this.lineaComercialRepository.getListByNivelAndWithoutIndGeneral(nivel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineaComercial> getListAll() {
        return lineaComercialRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineaFamilia> getListFamiliaByIDs(ArrayList<String> ids) {
        return lineaComercialMapper.getListFamiliaByIDs(ids);
    }
}
