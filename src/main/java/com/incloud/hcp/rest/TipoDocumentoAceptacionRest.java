package com.incloud.hcp.rest;

import com.incloud.hcp.domain.TipoDocumentoAceptacion;
import com.incloud.hcp.service.TipoDocumentoAceptacionService;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/TipoDocumentoAceptacion")
public class TipoDocumentoAceptacionRest {

    @Autowired
    TipoDocumentoAceptacionService tipoDocumentoAceptacionService;

    @GetMapping(value = "/ObtenerTodo")
    public ResponseEntity<List<TipoDocumentoAceptacion>> getAllTipoDocumentoAceptacion(){
        try {
            List<TipoDocumentoAceptacion> tipoDocumentoAceptacionList = tipoDocumentoAceptacionService.getAllTipoDocumentoAceptacion();
            if(tipoDocumentoAceptacionList != null){
                return new ResponseEntity<>(tipoDocumentoAceptacionList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
