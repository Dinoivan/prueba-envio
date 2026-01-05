package com.incloud.hcp.rest;

import com.incloud.hcp.domain.TipoInformacionNoticia;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.TipoInformacionNoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by MARCELO on 26/09/2017.
 */
@RestController
@RequestMapping(value = "/api/tipo-informacion-noticia")
public class TipoInformacionNoticiaRest extends AppRest {

    @Autowired
    TipoInformacionNoticiaService tipoInformacionNoticiaService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<TipoInformacionNoticia>> getAll(){
        return Optional.ofNullable(tipoInformacionNoticiaService.getAll())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> save(@RequestBody TipoInformacionNoticia tipoInformacionNoticia){
        return tipoInformacionNoticiaService.save(tipoInformacionNoticia);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> update(@RequestBody TipoInformacionNoticia tipoInformacionNoticia){
        return tipoInformacionNoticiaService.update(tipoInformacionNoticia);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> delete(@RequestBody TipoInformacionNoticia tipoInformacionNoticia){
        return tipoInformacionNoticiaService.delete(tipoInformacionNoticia);
    }

}
