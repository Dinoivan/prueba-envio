package com.incloud.hcp.rest;

import com.incloud.hcp.domain.TipoOrdenCompra;
import com.incloud.hcp.service.TipoOrdenCompraService;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/TipoOrdenCompra")
public class TipoOrdenCompraRest {

    @Autowired
    TipoOrdenCompraService tipoOrdenCompraService;

    @GetMapping(value = "/ObtenerTodo")
    public ResponseEntity<List<TipoOrdenCompra>> getAllTipoOrdenCompra(){
        try {
            List<TipoOrdenCompra> tipoOrdenCompraList = tipoOrdenCompraService.getAllTipoOrdenCompra();
            if(tipoOrdenCompraList != null){
                return new ResponseEntity<>(tipoOrdenCompraList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

}
