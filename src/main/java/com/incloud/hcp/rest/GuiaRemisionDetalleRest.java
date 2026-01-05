package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.GuiaRemisionDetalle;
import com.incloud.hcp.service.GuiaRemisionDetalleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/guiaRemisionDetalle")
public class GuiaRemisionDetalleRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GuiaRemisionDetalleService guiaRemisionDetalleService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<GuiaRemisionDetalle>> gelAllGuiaRemisionDetalle() {
        return Optional.ofNullable(guiaRemisionDetalleService.getAllGuiaRemisionDetalle())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public GuiaRemisionDetalle save(@RequestBody GuiaRemisionDetalle guiaRemisionDetalle) {

        return this.guiaRemisionDetalleService.save(guiaRemisionDetalle);

    }
}
