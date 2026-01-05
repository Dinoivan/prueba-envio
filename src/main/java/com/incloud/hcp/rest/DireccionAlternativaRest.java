package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.DireccionAlternativa;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.service.DireccionAlternativaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/direccionAlternativa")
public class DireccionAlternativaRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DireccionAlternativaService direccionAlternativaService;

  /*  @Autowired
    private DetalleTicketRepository detalleTicketRepository;

    @Autowired
    private TicketPesajeRepository ticketPesajeRepository;*/

    @RequestMapping(value = "SaveorUpdate", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public DireccionAlternativa save(@RequestBody DireccionAlternativa direccionAlternativa) {

      return this.direccionAlternativaService.save(direccionAlternativa);

    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<java.util.List<DireccionAlternativa>> gelAllDireccionAlternativa() {
        return Optional.ofNullable(direccionAlternativaService.getAllDireccionAlternativas())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @RequestMapping(value = "/ruc/{ruc}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<java.util.List<DireccionAlternativa>> getDireccionAlternativarUC(@PathVariable("ruc") String ruc) throws PortalException {
        List<DireccionAlternativa>direccionAlternativaList = new ArrayList<>();
        direccionAlternativaList = direccionAlternativaService.getfindByRuc(ruc);
        return new ResponseEntity<List<DireccionAlternativa>>(direccionAlternativaList, HttpStatus.OK);
    }
}
