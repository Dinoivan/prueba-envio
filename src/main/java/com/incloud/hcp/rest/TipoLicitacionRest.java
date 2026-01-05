package com.incloud.hcp.rest;

import com.incloud.hcp.domain.TipoLicitacion;
import com.incloud.hcp.dto.TipoLicitacionDto;
import com.incloud.hcp.repository.TipoLicitacionRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.TipoLicitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by USER on 24/08/2017.
 */
@RestController
@RequestMapping(value="/api/tipo-licitacion")
public class TipoLicitacionRest extends AppRest {

    @Autowired
    private TipoLicitacionService tipoLicitacionService;

    @Autowired
    private TipoLicitacionRepository tipoLicitacionRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<TipoLicitacion>> getListClaseDocumentoSolped() {
        return Optional.of(this.tipoLicitacionService.getListTipoLicitacion())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/{codTipoLicitacion}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<TipoLicitacion>> getListClaseDocumentoOC(@PathVariable("codTipoLicitacion") int codTipoLicitacion) {
        return Optional.of(this.tipoLicitacionService.getListTipoCuestionario(codTipoLicitacion))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/findByNivelOrderByDescripcion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<TipoLicitacion>> findByNivelOrderByDescripcion(@RequestBody Integer nivel)  {
        List<TipoLicitacion> lista = this.tipoLicitacionRepository.findByNivelOrderByDescripcion(nivel);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByIdPadreOrderByDescripcion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<TipoLicitacion>> findByIdPadreOrderByDescripcion(@RequestBody Integer idPadre)  {
        List<TipoLicitacion> lista = this.tipoLicitacionRepository.findByIdPadreOrderByDescripcion(idPadre);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/findByIdPadreInOrderByDescripcion", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<TipoLicitacion>> findByIdPadreInOrderByDescripcion(@RequestBody List<Integer> idPadre)  {
        List<TipoLicitacion> lista = this.tipoLicitacionRepository.findByIdPadreInOrderByDescripcion(idPadre);
        return ResponseEntity.ok().body(lista);
    }

    @RequestMapping(value = "/nivel/{nivel}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<TipoLicitacionDto>> getByNivel(@PathVariable("nivel") int nivel) {
        return Optional.of(this.tipoLicitacionService.getByNivel(nivel))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> save(@RequestBody TipoLicitacion tipoLicitacion){
        ResponseEntity response ;
        if(tipoLicitacion.getIdTipoLicitacion() != null){
            response = tipoLicitacionService.update(tipoLicitacion);
        } else {
            response = tipoLicitacionService.save(tipoLicitacion);
        }
        return response;
    }

    @RequestMapping(value = "",
            method = RequestMethod.DELETE,
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> delete(@RequestBody TipoLicitacion tipoLicitacion){
        return tipoLicitacionService.delete(tipoLicitacion);
    }
}
