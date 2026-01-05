package com.incloud.hcp.rest;

import com.incloud.hcp.domain.UnidadMedida;
import com.incloud.hcp.repository.UnidadMedidaRepository;
import com.incloud.hcp.rest._framework.AppRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by USER on 24/09/2017.
 */
@RestController
@RequestMapping(value = "/api/unidad-medida")
public class UnidadMedidaRest extends AppRest {

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UnidadMedida>> getListUnidadMedida () {

        return Optional.of(this.unidadMedidaRepository.findAll(Sort.by("descripcion")))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
