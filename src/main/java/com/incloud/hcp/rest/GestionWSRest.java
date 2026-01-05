package com.incloud.hcp.rest;

import com.incloud.hcp.jco.comprobanteRetencion.dto.ComprobanteRetencionDto;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.delta.GestionWSDeltaService;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/gestionws")
public class GestionWSRest extends AppRest {
    private static final Logger logger = LoggerFactory.getLogger(GestionWSRest.class);
    @Autowired
    private GestionWSDeltaService gestionWSDeltaService;

    @RequestMapping(value = "/comprobanteRetencionBase64", method = RequestMethod.POST, headers = "Accept=application/json")
    public String getComprobanteRetencionBase64(@RequestBody ComprobanteRetencionDto comprobanteRetencionDto) {

        try {
            return gestionWSDeltaService.getBase64ComprobanteRetencion(comprobanteRetencionDto);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
