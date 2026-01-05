package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.ControlCambioCampo;
import com.incloud.hcp.domain.Licitacion;
import com.incloud.hcp.domain.LicitacionControlCambio;
import com.incloud.hcp.repository.ControlCambioCampoRepository;
import com.incloud.hcp.repository.LicitacionControlCambioRepository;
import com.incloud.hcp.repository.LicitacionRepository;
import com.incloud.hcp.service.LicitacionControlCambioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gadiel on 14/02/2018.
 */
@Service
public class LicitacionControlCambioServiceImpl implements LicitacionControlCambioService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    SimpleDateFormat ft_diamesanio = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private LicitacionControlCambioRepository licitacionControlCambioRepository;

    @Autowired
    private ControlCambioCampoRepository controlCambioCampoRepository;

    @Autowired
    private LicitacionRepository licitacionRepository;

    @Override
    public List<LicitacionControlCambio> getLicitacionControlCambio(Integer idLicitacion) {

        Licitacion licitacion = licitacionRepository.getOne(idLicitacion);

        List<LicitacionControlCambio> listaLicitacionControlCambio = this.licitacionControlCambioRepository.findByLicitacionOrderByFechaCreacionDesc(licitacion);
        logger.debug("Se obtuvo licitacionControlCambio");

        for (LicitacionControlCambio element : listaLicitacionControlCambio){

            element.setFechaCreacionString(this.ft_diamesanio.format(element.getFechaCreacion()));

            List<ControlCambioCampo> listaCampos = this.controlCambioCampoRepository.findByLicitacionControlCambio(element);
            logger.debug("Se obtuvo controlCambioCampo");
            //licitacionControlCambio.setControlCambioCamposList(listaCampos);
            logger.debug("Se settea listaCampos en objeto");

            List<ControlCambioCampo> listaSet = new ArrayList<ControlCambioCampo>();
            for (ControlCambioCampo campo : listaCampos){

                listaSet.add(new ControlCambioCampo(
                        campo.getNombreCampo(), campo.getValorInicial(), campo.getValorFinal(), null
                ));
            }
            element.setControlCambioCamposList(listaSet);
        }

        return listaLicitacionControlCambio;
    }
}
