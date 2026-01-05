package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.almacen.OrdenDespachoDetalle;
import com.incloud.hcp.dto.OrdenDespachoDetalleDto;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.OrdenDespachoDetalleService;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/OrdenDespachoDetalle")
public class OrdenDespachoDetalleRest extends AppRest {

    private OrdenDespachoDetalleService ordenDespachoDetalleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrdenDespachoDetalleRest(OrdenDespachoDetalleService ordenDespachoDetalleService) {
        this.ordenDespachoDetalleService = ordenDespachoDetalleService;
    }

    @RequestMapping(value = "/findOrdenDespachoDetalleById/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<OrdenDespachoDetalle>> getOrdenCompraDetalleByIdOcLiberada(@PathVariable("id") Integer idOrdenCompra,
                                                                                          @RequestParam(value = "parametro", required = false) String parametro,
                                                                                          @AuthenticationPrincipal Token token
    ){
        List<OrdenDespachoDetalle> ordenDespachoDetalleList = new ArrayList<>();
        if(parametro != null) {
            if(parametro.equalsIgnoreCase("X")) {
                ordenDespachoDetalleList = ordenDespachoDetalleService.getOCDetalleListByIdOcLiberada(idOrdenCompra);
            }
        } else {
            UserSession userSession = this.getUserSession(token);
            ordenDespachoDetalleList = ordenDespachoDetalleService.getOrdenDespachoDetalleListByIdOcLiberada(idOrdenCompra, userSession.getRuc());
        }

        if(ordenDespachoDetalleList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(ordenDespachoDetalleList, HttpStatus.OK);
    }

    @RequestMapping(value = "/findOrdenDespachoDetalleByOc/{numeroOrdenCompra}", method = RequestMethod.GET)
    public ResponseEntity<List<OrdenDespachoDetalleDto>> getOrdenCompraDetalleByOcLiberada(@PathVariable("numeroOrdenCompra") String numeroOrdenCompra,
                                                                                        @RequestParam(value = "parametro", required = false) String parametro,
                                                                                        @AuthenticationPrincipal Token token
                                                                                        ){
        List<OrdenDespachoDetalleDto> ordenDespachoDetalleList = new ArrayList<>();
        if(parametro != null) {
            if(parametro.equalsIgnoreCase("X")) {
                ordenDespachoDetalleList = ordenDespachoDetalleService.getOrdenDespachoDetalleListByOcLiberada(numeroOrdenCompra, "");
            }
        } else {
            UserSession userSession = this.getUserSession(token);
            logger.error("ods " + userSession);
            ordenDespachoDetalleList = ordenDespachoDetalleService.getOrdenDespachoDetalleListByOcLiberada(numeroOrdenCompra, userSession.getRuc());
        }

        if(ordenDespachoDetalleList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(ordenDespachoDetalleList, HttpStatus.OK);
    }

}
