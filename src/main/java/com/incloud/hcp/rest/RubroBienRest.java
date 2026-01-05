package com.incloud.hcp.rest;

import com.incloud.hcp.domain.RubroBien;
import com.incloud.hcp.repository.RubroBienRepository;
import com.incloud.hcp.rest._framework.AppRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by USER on 20/09/2017.
 */
@RestController
@RequestMapping(value = "/api/rubro_bien")
public class RubroBienRest extends AppRest {

@Autowired
private RubroBienRepository rubroBienRepository;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<RubroBien>> getListRubroBienAll() {

        return Optional.of(this.rubroBienRepository.findAll())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }



}
