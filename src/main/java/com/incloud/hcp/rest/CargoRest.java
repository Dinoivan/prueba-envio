package com.incloud.hcp.rest;

import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MARCELO on 07/11/2017.
 */
@RestController
@RequestMapping(value = "/api/cargo")
public class CargoRest extends AppRest {

    @Autowired
    CargoService cargoService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getAllCargo() {

        return this.processList(cargoService.getAllCargo());
    }
}
