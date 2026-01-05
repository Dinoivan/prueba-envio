package com.incloud.hcp.rest;

import com.incloud.hcp.domain.ClaseDocumento;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.ClaseDocumentoService;
import com.incloud.hcp.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by USER on 24/08/2017.
 */
@RestController
@RequestMapping(value = "/api/clase-documento")
public class ClaseDocumentoRest extends AppRest {

    @Autowired
    private ClaseDocumentoService claseDocumentoService;


    @RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ClaseDocumento>> getListClaseDocumentoSolped() {
        return Optional.of(this.claseDocumentoService.getListClaseDocumentoSolped())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @RequestMapping(value = "/{codClaseDocSolped}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ClaseDocumento>> getListClaseDocumentoOC(@PathVariable("codClaseDocSolped") int codClaseDocSolped) {
        return Optional.of(this.claseDocumentoService.getListClaseDocumentoOC(codClaseDocSolped))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/nivel/{nivel}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ClaseDocumento>> getListClaseDocumentByNivel(@PathVariable("nivel") int nivel) {
        return Optional.of(this.claseDocumentoService.findByNivel(nivel))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/ordenCompra/{idClaseDocumento}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ClaseDocumento>> getListClaseDocumentoOrdenCompra(@PathVariable Integer idClaseDocumento) {
        return Optional.of(this.claseDocumentoService.getListClaseDocumentoOrdenCompra(idClaseDocumento))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @Operation(summary = "Devuelve lista de clase de documento en base al ID Licitación")
    @GetMapping(value = "/licitacion/{idLicitacion}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClaseDocumento>> getListClaseDocumentoLicitacion(@PathVariable Integer idLicitacion) throws URISyntaxException {
        try {
            List<ClaseDocumento>  result = this.claseDocumentoService.getListClaseDocumentoLicitacion(idLicitacion);
            return Optional.ofNullable(result)
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


}


