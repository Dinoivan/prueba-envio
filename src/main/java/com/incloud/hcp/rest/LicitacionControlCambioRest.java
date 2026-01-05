package com.incloud.hcp.rest;

import com.incloud.hcp.domain.LicitacionControlCambio;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.LicitacionControlCambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by Gadiel on 14/02/2018.
 */
@RestController
@RequestMapping(value = "/api/licitacion-control-cambio")
public class LicitacionControlCambioRest extends AppRest {

    @Autowired
    LicitacionControlCambioService licitacionControlCambioService;

    @RequestMapping(value = "/by-licitacion/{idLicitacion}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<LicitacionControlCambio>> getControlCambiosByLicitacion(@PathVariable Integer idLicitacion)  {

        List<LicitacionControlCambio> lista=null;
        try{
            lista = this.licitacionControlCambioService.getLicitacionControlCambio(idLicitacion);
        }catch (Exception e){
            e.printStackTrace();
            throw new PortalException(e.getMessage());
        }
        return Optional.of(lista)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
