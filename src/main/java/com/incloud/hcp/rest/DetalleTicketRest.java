package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.DetalleTicket;

import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.service.DetalleTicketService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/subticketPesaje")
public class DetalleTicketRest {

   /* private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DetalleTicketService subticketService;

    @RequestMapping(value = "/id/{idSubTicketPesaje}",
                    method = RequestMethod.GET, produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Optional<DetalleTicket>> getSubTicketPesajeById(@PathVariable("idSubTicketPesaje") Integer idSubTicketPesaje) throws PortalException {
        Optional<DetalleTicket> subticketPesaje = this.subticketService.getSubTicketPesajeById(idSubTicketPesaje);
        return ResponseEntity.ok().body(subticketPesaje);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<java.util.List<DetalleTicket>> gelAllSubTicketPesaje() {
        return Optional.ofNullable(subticketService.getAllSubTicketPesaje())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public DetalleTicket save(@RequestBody DetalleTicket subticket) {
        return this.subticketService.save(subticket);
    }
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> update(@RequestBody DetalleTicket subticket) {
        return subticketService.update(subticket);
    }
*/
}
