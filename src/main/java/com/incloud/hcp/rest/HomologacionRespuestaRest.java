package com.incloud.hcp.rest;

import com.incloud.hcp.domain.HomologacionRespuesta;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.HomologacionRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by MARCELO on 13/10/2017.
 */
@RestController
@RequestMapping(value = "/api/homologacion-respuesta")
public class HomologacionRespuestaRest extends AppRest {

    @Autowired
    HomologacionRespuestaService homologacionRespuestaService;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<HomologacionRespuesta>> getRespuestaHomologacionByIdHomologacion(@PathVariable("id") Integer id){
        return Optional.ofNullable(homologacionRespuestaService.getHomologacionRespuestaByHomologacion(id))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE,
                        MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> save(@RequestBody List<HomologacionRespuesta> homologacionRespuestas){
        return new ResponseEntity<Map>(HttpStatus.OK);
    }
}
