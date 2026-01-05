package com.incloud.hcp.rest;

import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.cargadata.BienServicioLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Administrador on 28/09/2017.
 */
@RestController
@RequestMapping(value = "/api/generic")
public class GenericRest extends AppRest{

    @Autowired
    BienServicioLoadService bienServicioLoadService;

    /**
     *
     * @param json
     * Example json: {"numberFile": 1} //Cargar el archivo bien_servicio1.csv
     * @return
     */
    @RequestMapping(value = "/bien-servicio/load", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> loadDataBienServicio(@RequestBody Map<String, Object> json)  {

        Integer numberFile = (Integer)json.get("numberFile");
        String respuesta = "";

        try{
            respuesta = bienServicioLoadService.loadDataBienServicio(numberFile);
        }catch(Exception e){
            respuesta = "error" + e.getMessage();
        }
        //return ResponseEntity.ok().body(lista);
        return Optional.of(respuesta)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
