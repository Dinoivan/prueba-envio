package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.HomologacionRespuesta;
import com.incloud.hcp.repository.HomologacionRepository;
import com.incloud.hcp.repository.HomologacionRespuestaRepository;
import com.incloud.hcp.service.HomologacionRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by MARCELO on 13/10/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class HomologacionRespuestaServiceImpl implements HomologacionRespuestaService {

    private HomologacionRepository homologacionRepository;
    private HomologacionRespuestaRepository homologacionRespuestaRepository;

    @Autowired
    public void setHomologacionRepository(HomologacionRepository homologacionRepository) {
        this.homologacionRepository = homologacionRepository;
    }

    @Autowired
    public void setHomologacionRespuestaRepository(HomologacionRespuestaRepository homologacionRespuestaRepository) {
        this.homologacionRespuestaRepository = homologacionRespuestaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomologacionRespuesta> getHomologacionRespuestaByHomologacion(Integer idHomologacion) {
        return homologacionRespuestaRepository
                .findByHomologacionOrderByIdHomologacionRespuesta(homologacionRepository.getOne(idHomologacion));
    }

    @Override
    public HomologacionRespuesta guardar(HomologacionRespuesta homologacionRespuesta) {
        return homologacionRespuestaRepository.save(homologacionRespuesta);
    }
}
