package com.incloud.hcp.rest;

import com.incloud.hcp.domain.EstadoPrefactura;
import com.incloud.hcp.service.EstadoPrefacturaService;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/EstadoPrefactura")
public class EstadoPrefacturaRest {

    @Autowired
    EstadoPrefacturaService estadoPrefacturaService;

    @GetMapping(value = "/ObtenerTodo")
    public ResponseEntity<List<EstadoPrefactura>> getAllTipoDocumentoAceptacion(){
        try {
            List<EstadoPrefactura> EstadoPrefacturaList = estadoPrefacturaService.getAllEstadoPrefacturas();
            if(EstadoPrefacturaList != null){
                return new ResponseEntity<>(EstadoPrefacturaList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
