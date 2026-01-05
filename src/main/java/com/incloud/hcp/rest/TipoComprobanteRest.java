package com.incloud.hcp.rest;

import com.incloud.hcp.domain.TipoComprobante;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.TipoComprobanteRepository;
import com.incloud.hcp.rest._framework.AppRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tipo-comprobante")
public class TipoComprobanteRest extends AppRest {

    @Autowired
    private TipoComprobanteRepository tipoComprobanteRepository;

    @RequestMapping(value = "",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<TipoComprobante>> getListaTipoComprobante() throws PortalException {
        List listaTipo = this.tipoComprobanteRepository.findAll();
        return ResponseEntity.ok().body(listaTipo);
    }
}
