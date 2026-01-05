package com.incloud.hcp.rest;

import com.incloud.hcp.domain.EstadoOrdenCompra;
import com.incloud.hcp.service.EstadoOrdenCompraService;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/EstadoOrdenCompra")
public class EstadoOrdenCompraRest {

    @Autowired
    private EstadoOrdenCompraService estadoOrdenCompraService;

    @GetMapping(value = "/ObtenerTodo")
    public ResponseEntity<List<EstadoOrdenCompra>> getAllEstadoOrdenCompra (){
        try {
            List<EstadoOrdenCompra> estadoOrdenCompraList = estadoOrdenCompraService.getAllEstadoOrdenCompra();
            if(estadoOrdenCompraList != null){
                return new ResponseEntity<>(estadoOrdenCompraList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
