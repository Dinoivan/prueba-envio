package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.GuiaRemisionDetalle;
import com.incloud.hcp.repository.GuiaRemisionDetalleRepository;
import com.incloud.hcp.service.GuiaRemisionDetalleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GuiaRemisionServiceDetalleImpl implements GuiaRemisionDetalleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GuiaRemisionDetalleRepository guiaRemisionDetalleRepository;


    @Override
    public List<GuiaRemisionDetalle> getAllGuiaRemisionDetalle() {

        return guiaRemisionDetalleRepository.findAll();
    }

    @Override
    public GuiaRemisionDetalle save(GuiaRemisionDetalle guiaRemisionDetalle) {


        return guiaRemisionDetalleRepository.save(guiaRemisionDetalle);


    }

    @Override
    public List<GuiaRemisionDetalle> getGuiaRemisionDetalleByTicket(String ticketPesajeId) {
        return this.guiaRemisionDetalleRepository.getGuiaRemisionDetalleByTicket(ticketPesajeId);
    }

}
