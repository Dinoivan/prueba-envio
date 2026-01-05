package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.TipoSolicitudBlacklist;
import com.incloud.hcp.repository.TipoSolicitudBlackListRepository;
import com.incloud.hcp.service.TipoSolicitudBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by MARCELO on 12/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class TipoSolicitudBlackListServiceImpl implements TipoSolicitudBlackListService {

    @Autowired
    private TipoSolicitudBlackListRepository tipoSolicitudBlackListRepository;

    @Override
    public List<TipoSolicitudBlacklist> getListTipoSolicitudBlackList() {
        return tipoSolicitudBlackListRepository.findAll();
    }
}
