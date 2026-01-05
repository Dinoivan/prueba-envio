package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.Carreta;
import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.jco.balanza.Carreta.dto.CarretaResponseDTO;
import com.incloud.hcp.service.CarretaService;

import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/carreta")
public class CarretaRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarretaService carretaService;

    @RequestMapping(value = "/id/{idCarreta}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Optional<Carreta>> getCarretaById(@PathVariable("idCarreta") Integer idCarreta) throws PortalException {
        Optional<Carreta> carreta = this.carretaService.getCarretaById(idCarreta);
        return ResponseEntity.ok().body(carreta);
    }

    @RequestMapping(value = "/descripcion/{descripcion}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Carreta>getCarretaByDescripcion(@PathVariable("descripcion") String descripcion) throws PortalException {
        Carreta carreta = this.carretaService.getCarretaByDescripcion(descripcion);
        return ResponseEntity.ok().body(carreta);
    }

    @RequestMapping(value = "/codigo/{codigo}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Carreta>getCarretaByCodigo(@PathVariable("codigo") String codigo) throws PortalException {
        Carreta carreta = this.carretaService.getCarretaByCodigo(codigo);
        return ResponseEntity.ok().body(carreta);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<java.util.List<Carreta>> gelAllCarreta() {
        List<Carreta> carreta = this.carretaService.getAllCarreta();
        return ResponseEntity.ok(carreta);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> save(@RequestBody Carreta carreta) {

        return carretaService.save(carreta);
    }

    @RequestMapping(value = "/update/{carretaId}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Carreta update(@PathVariable Integer carretaId,@RequestBody CarretaResponseDTO carretaResponseDTO){
        return carretaService.update(carretaId, carretaResponseDTO);
    }

    @RequestMapping(value = "cambiarEstado/{carretaId}", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Carreta cambiarEstado(@PathVariable Integer carretaId){
        try {
            return carretaService.cambiarEstado(carretaId);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


}
