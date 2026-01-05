package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.CentroAlmacenBalanza;
import com.incloud.hcp.repository.LecturaPesoRepository;
import com.incloud.hcp.service.LecturaPesoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LecturaPesoServiceImpl implements LecturaPesoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LecturaPesoRepository lecturaPesoRepository;

    @Override
    public List<CentroAlmacenBalanza> getAllCentroPesoBalanza() {
        return lecturaPesoRepository.findAll();
    }
}
