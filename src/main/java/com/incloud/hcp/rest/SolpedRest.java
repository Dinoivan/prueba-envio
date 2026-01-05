package com.incloud.hcp.rest;

import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.sap.solped.SolpedResponse;
import com.incloud.hcp.service.SolpedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by USER on 11/09/2017.
 */
@RestController
@RequestMapping(value = "/api/solped")
public class SolpedRest extends AppRest {

    @Autowired
    private SolpedService solpedService;



    @RequestMapping(value = "/codigo/{solped}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<SolpedResponse> getSolpedByCodigo(@PathVariable String solped)  {

        SolpedResponse objSolped = new SolpedResponse();
        try {
           objSolped = this.solpedService.getSolpedResponseByCodigo(solped);
        }catch(Exception e){
            e.printStackTrace();

            SapLog log = new SapLog();
            log.setMesaj(e.getMessage());
            objSolped.setSapLog(log);
        }

        return Optional.of(objSolped)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }



}
