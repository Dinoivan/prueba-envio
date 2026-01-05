package com.incloud.hcp.rest;

import com.incloud.hcp.domain.Moneda;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.MonedaRepository;
import com.incloud.hcp.rest._framework.AppRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/moneda")
public class MonedaRest extends AppRest {

    @Autowired
    private MonedaRepository monedaRepository;

    @RequestMapping(value = "",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Moneda>> getListaMoneda() throws PortalException {
        List listaTipo = this.monedaRepository.findAll();
        return ResponseEntity.ok().body(listaTipo);
    }


}
