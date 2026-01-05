package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.balanza.DetalleTicket;

import com.incloud.hcp.repository.DetalleTicketRepository;

import com.incloud.hcp.service.DetalleTicketService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DetalleTicketServiceImpl implements DetalleTicketService {

   /* private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DetalleTicketRepository subticketRepository;


    @Override
    public List<DetalleTicket> getAllSubTicketPesaje() {
        return subticketRepository.findAll();
    }

    @Override
    public Optional<DetalleTicket> getSubTicketPesajeById(Integer idSubTicketPesaje) {
        return  subticketRepository.findById(idSubTicketPesaje);
    }

    @Override
    public DetalleTicket save(DetalleTicket subticket) {
        LocalDateTime fechaActual = LocalDateTime.now();
        subticket.setFechaCreacion(fechaActual);
        return subticketRepository.save(subticket);
    }

    @Override
    public ResponseEntity<Map> update(DetalleTicket subticket) {
        ResponseEntity response = null;
        Map map = new HashMap<>();
        try{
            DetalleTicket subticketActual = subticketRepository.findById(subticket.getId()).orElse(null);
            if(subticketActual != null) {
                subticketActual.setProducto(subticket.getProducto());
                subticketActual.setPeso_bruto (subticket.getPeso_bruto());
                subticketActual.setPeso_neto(subticket.getPeso_neto());
                subticketActual.setPosicion(subticket.getPosicion());
                subticketActual.setTipo_peso(subticket.getTipo_peso());
                subticketActual.setFechaModificacion(subticket.getFechaModificacion());
                subticketActual.setEstado(subticket.getEstado());
                subticketActual = subticketRepository.save(subticketActual);
            }
            subticket = subticketActual;
            LocalDateTime fechaActualmodi = LocalDateTime.now();
            subticket.setFechaModificacion(fechaActualmodi);
            map.put("data", subticketRepository.save(subticket));
            response = new ResponseEntity(map, HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }


        return response;
    }*/
}
