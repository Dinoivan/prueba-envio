package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.repository.LicitacionAdjuntoRepository;
import com.incloud.hcp.service.LicitacionAdjuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by USER on 10/11/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class LicitacionAdjuntoServiceImpl implements LicitacionAdjuntoService {

    @Autowired
    private LicitacionAdjuntoRepository licitacionAdjuntoRepository;

    public void deleteLicitacionAdjuntoByLicitacionArchivoId(Licitacion licitacion, String archivoId) {
        this.licitacionAdjuntoRepository.deleteLicitacionAdjuntoByLicitacionArchivoId(licitacion, archivoId);
    }
}
