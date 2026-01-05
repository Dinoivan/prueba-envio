package com.incloud.hcp.rest;

import com.incloud.hcp.domain.InformacionNoticia;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.InformacionNoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by MARCELO on 26/09/2017.
 */
@RestController
@RequestMapping(value = "/api/informacion-noticia")
public class InformacionNoticiaRest extends AppRest {

    @Autowired
    InformacionNoticiaService informacionNoticiaService;

    @RequestMapping(value = "/{idTipoInfoNoticia}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<InformacionNoticia>> getInfoNoticiaByTipoInfoNoticia(@PathVariable("idTipoInfoNoticia") int idTipoSolicitud){
        return Optional.ofNullable(informacionNoticiaService.getInfoNoticiaByTipoInfoNoticia(idTipoSolicitud))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> save(@RequestBody InformacionNoticia informacionNoticia){
        return informacionNoticiaService.save(informacionNoticia);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map> update(@RequestBody InformacionNoticia informacionNoticia){
        return informacionNoticiaService.update(informacionNoticia);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map> delete(@RequestBody InformacionNoticia informacionNoticia){
        return informacionNoticiaService.delete(informacionNoticia);
    }
}
