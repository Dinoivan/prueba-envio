package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.DireccionAlternativa;
import com.incloud.hcp.repository.DireccionAlternativaRepository;
import com.incloud.hcp.service.DireccionAlternativaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DireccionAlternativaServiceImpl implements DireccionAlternativaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DireccionAlternativaRepository direccionAlternativaRepository;




    @Override
    public DireccionAlternativa save(DireccionAlternativa direccionAlternativa) {
        return  direccionAlternativaRepository.save(direccionAlternativa);
    }

    @Override
    public List<DireccionAlternativa> getAllDireccionAlternativas() {
        return direccionAlternativaRepository.findAll();
    }

    @Override
    public  List<DireccionAlternativa> getfindByRuc(String ruc){
        return direccionAlternativaRepository.findByRuc(ruc);
    }
}







   /* @Override
    public ResponseEntity <Map> update(TicketPesaje ticketPesaje) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try{
            TicketPesaje ticketPesajeActual = ticketPesajeRepository.findById(ticketPesaje.getId()).orElse(null);
            if(ticketPesajeActual != null) {
                ticketPesajeActual.setChofer(ticketPesaje.getChofer());
                ticketPesajeActual.setTransporte(ticketPesaje.getTransporte());
                ticketPesajeActual.setCarreta(ticketPesaje.getCarreta());
                ticketPesajeActual.setTipoProceso(ticketPesaje.getTipoProceso());
                ticketPesajeActual.setUnidadMedida(ticketPesaje.getUnidadMedida());
                ticketPesajeActual.setPesoInicial(ticketPesaje.getPesoInicial());
                ticketPesajeActual.setFechaModificacion(ticketPesaje.getFechaModificacion());
                ticketPesajeActual = ticketPesajeRepository.save(ticketPesajeActual);
            }
            ticketPesaje = ticketPesajeActual;
            LocalDateTime fechaActualmodi = LocalDateTime.now();
            ticketPesaje.setFechaModificacion(fechaActualmodi);
            map.put("data", ticketPesajeRepository.save(ticketPesaje));
            response = new ResponseEntity(map, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }


        return response;
    }
*/


