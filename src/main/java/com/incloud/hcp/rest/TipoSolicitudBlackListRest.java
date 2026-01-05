package com.incloud.hcp.rest;

import com.incloud.hcp.domain.TipoSolicitudBlacklist;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.TipoSolicitudBlackListService;
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
 * Created by MARCELO on 22/09/2017.
 */
@RestController
@RequestMapping(value = "/api/tipo-solicitud-blackList")
public class TipoSolicitudBlackListRest extends AppRest {

    @Autowired
    TipoSolicitudBlackListService tipoSolicitudBlackListService;

        @RequestMapping(value = "", method = RequestMethod.GET, produces = {
                MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
        public ResponseEntity<List<TipoSolicitudBlacklist>> getAllTipoSolicitudBlackListo() {
            return Optional.of(this.tipoSolicitudBlackListService.getListTipoSolicitudBlackList())
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        }
}
