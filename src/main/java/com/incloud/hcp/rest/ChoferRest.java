package com.incloud.hcp.rest;


import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.balanza.Carreta;
import com.incloud.hcp.domain.balanza.Chofer;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.jco.balanza.Chofer.dto.ChoferResponseDTO;
import com.incloud.hcp.jco.balanza.Chofer.service.JCOChoferService;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.ChoferService;
import com.incloud.hcp.util.Utils;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/chofer")
public class ChoferRest extends AppRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JCOChoferService jcoChoferService;
    @Autowired
    private ChoferService choferService;

    @RequestMapping(value = "/dni/{dni}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Chofer>getChoferByDni(@PathVariable("dni") String dni) throws PortalException {
        Chofer chofer = this.choferService.getChoferByDni(dni);
        return ResponseEntity.ok().body(chofer);
    }

    @RequestMapping(value = "/licencia/{licencia}",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Chofer>getCarretaByCodigo(@PathVariable("licencia") String licencia) throws PortalException {
        Chofer chofer = this.choferService.getChoferByLicencia(licencia);
        return ResponseEntity.ok().body(chofer);
    }


    @RequestMapping(value = "/listar", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Chofer>> getAllChofer(){
        return Optional.ofNullable(choferService.getAllChofer())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/grabar", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> save(@RequestBody Chofer chofer) {
        //UserSession userSession = this.getUserSession(token);
        //chofer.setCreatedBy(userSession.g);
        return choferService.save(chofer);
    }

    @RequestMapping(value = "/update/{choferId}", method = RequestMethod.POST, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Chofer update(@PathVariable Integer choferId,@RequestBody ChoferResponseDTO choferResponseDTO){
        return choferService.update(choferId, choferResponseDTO);
    }

    @RequestMapping(value = "cambiarEstado/{choferId}", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Chofer cambiarEstado(@PathVariable Integer choferId){
        try {
            return choferService.cambiarEstado(choferId);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @RequestMapping(value = "extraerChoferSAP_BTP", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String extraerChoferSAP_BT(){
        try {

            jcoChoferService.extraerChoferListRFC(false);
            return "Sincronizado exitosamente";
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }



}
