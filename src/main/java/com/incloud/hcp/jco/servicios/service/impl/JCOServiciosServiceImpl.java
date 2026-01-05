package com.incloud.hcp.jco.servicios.service.impl;

import com.incloud.hcp.jco.servicios.dto.ServiciosRFCResponseDto;
import com.incloud.hcp.jco.servicios.service.JCOServiciosService;
import com.incloud.hcp.jco.servicios.service.JCOServiciosServiceNew;
import com.incloud.hcp.repository.TempBienServicioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOServiciosServiceImpl implements JCOServiciosService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOServiciosServiceNew jcoServiciosServiceNew;

    @Autowired
    private TempBienServicioRepository tempBienServicioRepository;

    public ServiciosRFCResponseDto actualizarMaterialesRFC(String fechaInicio, String fechaFin) throws Exception {
//        Gson gson = new Gson();
        logger.error("<--MC_LOG-->:actualizarMaterialesRFC_0:>");
        ServiciosRFCResponseDto serviciosRFCResponseDto = this.jcoServiciosServiceNew.getListServicios(fechaInicio, fechaFin);
//        logger.error("<--MC_LOG-->:serviciosRFCResponseDto:>"+gson.toJson(serviciosRFCResponseDto));
        logger.error("<--MC_LOG-->:serviciosRFCResponseDto:>");
        this.tempBienServicioRepository.saveNeworUpdateActualizados();
        logger.error("<--MC_LOG-->:actualizarMaterialesRFC_1:>");
        return serviciosRFCResponseDto;
    }
}
