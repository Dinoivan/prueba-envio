package com.incloud.hcp.rest;

import com.incloud.hcp.bean.Condicion;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.CondicionLicService;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by USER on 24/09/2017.
 */
@RestController
@RequestMapping(value = "/api/condicion-licitacion")
public class CondicionLicRest extends AppRest {

    @Autowired
    private CondicionLicService condicionLicService;



    @RequestMapping(value = "/tipo-licitacion/{tipoLicitacion}/tipo-cuestionario/{tipoCuestionario}",
            method = RequestMethod.GET, headers = "Accept=application/json", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Condicion>> getListCondicionByTipoLicitacionAndCuestion(@PathVariable("tipoLicitacion") int tipoLicitacion,
                                                                             @PathVariable("tipoCuestionario") int tipoCuestionario) {
        return Optional.of(this.condicionLicService.getListCondicionLicByTipoLicitacionAndTipoCuestionario(
                tipoLicitacion, tipoCuestionario
                ))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @RequestMapping(value = "/totalPeso/tipo-licitacion/{tipoLicitacion}/tipo-cuestionario/{tipoCuestionario}",
            method = RequestMethod.GET, headers = "Accept=application/json", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<BigDecimal> getTotalPesoByTipoCuestionario(@PathVariable("tipoLicitacion") int tipoLicitacion,
                                                                                       @PathVariable("tipoCuestionario") int tipoCuestionario) {
        return Optional.of(this.condicionLicService.getTotalPesoByTipoCuestionario(
                tipoLicitacion, tipoCuestionario
        ))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> saveCondicionLicitacion(@RequestBody Condicion obj,@AuthenticationPrincipal Token token)   {
        String response = "";
        try{
            response = this.condicionLicService.saveCondicionLicitacion(obj,token);
        }catch(Exception e){
            response = e.getMessage();
        }
        return ResponseEntity.ok().body(response);
    }


    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE, headers = "Accept=application/json", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map> deleteCondicionLic(@RequestBody ArrayList<Integer> ids){

        Map response = this.condicionLicService.deleteCondicionLicByIdCondicion(ids);

        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }



}
