package com.incloud.hcp.rest;

import com.incloud.hcp.domain.EstadoDocumentoAceptacion;
import com.incloud.hcp.service.EstadoDocumentoAceptacionService;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/EstadoDocumentoAceptacion")
public class EstadoDocumentoAceptacionRest {

    @Autowired
    EstadoDocumentoAceptacionService estadoDocumentoAceptacionService;

    @GetMapping(value = "/ObtenerTodo")
    public ResponseEntity<List<EstadoDocumentoAceptacion>> getAllEstadoDocumentoAceptacion(){
        try {
            List<EstadoDocumentoAceptacion> estadoDocumentoAceptacionList = estadoDocumentoAceptacionService.getAllEstadoDocumentoAceptacion();
            if(estadoDocumentoAceptacionList != null){
                return new ResponseEntity<>(estadoDocumentoAceptacionList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
