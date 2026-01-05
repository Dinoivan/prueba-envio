package com.incloud.hcp.rest;

import com.incloud.hcp.domain.balanza.Carreta;
import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.domain.balanza.Transporte;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.jco.balanza.Transporte.dto.TransporteResponseDTO;
import com.incloud.hcp.jco.balanza.Transporte.service.JCOTransporteService;
import com.incloud.hcp.service.TransporteService;
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
@RequestMapping(value = "/api/transporte")
public class TransporteRest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransporteService transporteService;

    @Autowired
    private JCOTransporteService jcoTransporteService;

    @RequestMapping(value = "/id/{idTransporte}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Optional<Transporte>> getTransporteById(@PathVariable("idTransporte") Integer idTransporte) throws PortalException {
        Optional<Transporte> transporte = this.transporteService.getTransporteById(idTransporte);
        return ResponseEntity.ok().body(transporte);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<java.util.List<Transporte>> gelAllTransporte() {
        List<Transporte> transporte = this.transporteService.getAllTransporte();
        return ResponseEntity.ok(transporte);
    }
    @RequestMapping(value = "/activo", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<java.util.List<Transporte>> getAllTransporteActivo() {
        List<Transporte> transporte = this.transporteService.getAllTransporteActivo();
        return ResponseEntity.ok(transporte);
    }

    @RequestMapping(value = "/Remolque", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<java.util.List<Transporte>> gelAllTransporteRemolque() {
        List<Transporte> transporte = this.transporteService.getAllRemolque();
        return ResponseEntity.ok(transporte);
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> save(@RequestBody Transporte transporte) {
        return transporteService.save(transporte);
    }

    @RequestMapping(value = "/update/{transporteId}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Transporte update(@PathVariable Integer transporteId,@RequestBody TransporteResponseDTO transporteResponseDTO){
        return transporteService.update(transporteId, transporteResponseDTO);
    }

    @RequestMapping(value = "cambiarEstado/{transporteId}", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Transporte cambiarEstado(@PathVariable Integer transporteId){
        try {
            return transporteService.cambiarEstado(transporteId);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @RequestMapping(value = "extraerTransporteSAP_BTP", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String extraerTransporteSAP_BTP(){
        try {
            jcoTransporteService.extraerTransporteListRFC(false);
            return "Sincronizado exitosamente";
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
