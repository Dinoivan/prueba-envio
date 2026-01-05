package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.OrdenCompraDetalle;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.OrdenCompraDetalleService;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/OrdenCompraDetalle")
public class OrdenCompraDetalleRest extends AppRest {

    private OrdenCompraDetalleService ordenCompraDetalleService;

    @Autowired
    public OrdenCompraDetalleRest(OrdenCompraDetalleService ordenCompraDetalleService) {
        this.ordenCompraDetalleService = ordenCompraDetalleService;
    }

    @RequestMapping(value = "/findOrdenCompraDetalleById/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<OrdenCompraDetalle>> getOrdenCompraDetalleByIdOcLiberada(@PathVariable("id") Integer idOrdenCompra,
                                                                                        @RequestParam(value = "parametro", required = false) String parametro,
                                                                                        @AuthenticationPrincipal Token token
                                                                                        ){
        List<OrdenCompraDetalle> ordenCompraDetalleList = new ArrayList<>();
        if(parametro != null) {
            if(parametro.equalsIgnoreCase("X")) {
                ordenCompraDetalleList = ordenCompraDetalleService.getOCDetalleListByIdOcLiberada(idOrdenCompra);
            }
        } else {
            UserSession userSession = this.getUserSession(token);
            ordenCompraDetalleList = ordenCompraDetalleService.getOrdenCompraDetalleListByIdOcLiberada(idOrdenCompra, userSession.getRuc());
        }

        if(ordenCompraDetalleList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(ordenCompraDetalleList, HttpStatus.OK);
    }

}
