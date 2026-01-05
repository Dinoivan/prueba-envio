package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.SectorTrabajo;
import com.incloud.hcp.repository.SectorTrabajoRepository;
import com.incloud.hcp.service.SectorTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectorTrabajoServiceImpl implements SectorTrabajoService {


    private SectorTrabajoRepository sectorTrabajoRepository;

    @Autowired
    public void setSectorTrabajoRepository(SectorTrabajoRepository sectorTrabajoRepository) {
        this.sectorTrabajoRepository = sectorTrabajoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SectorTrabajo> getListAll() {
        return this.sectorTrabajoRepository.findAll();
    }
}
