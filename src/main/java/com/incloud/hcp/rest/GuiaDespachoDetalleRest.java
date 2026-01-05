package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.almacen.GuiaDespachoDetalle;
import com.incloud.hcp.repository.GuiaDespachoDetalleRepository;
import com.incloud.hcp.repository.GuiaDespachoRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.GuiaDespachoDetalleService;
import com.incloud.hcp.service.GuiaDespachoService;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 20/09/2017.
 */
@RestController
@RequestMapping(value = "/api/guia-despacho-detalle")
public class GuiaDespachoDetalleRest extends AppRest {

    private GuiaDespachoService guiaDespachoService;
    private GuiaDespachoRepository guiaDespachoRepository;
    private ProveedorRepository proveedorRepository;
    private GuiaDespachoDetalleRepository guiaDespachoDetalleRepository;
    private GuiaDespachoDetalleService guiaDespachoDetalleService;

    @Autowired
    public GuiaDespachoDetalleRest(GuiaDespachoService guiaDespachoService,
                                   GuiaDespachoRepository guiaDespachoRepository,
                                   ProveedorRepository proveedorRepository,
                                   GuiaDespachoDetalleRepository guiaDespachoDetalleRepository,
                                   GuiaDespachoDetalleService guiaDespachoDetalleService) {
        this.guiaDespachoService = guiaDespachoService;
        this.guiaDespachoRepository = guiaDespachoRepository;
        this.proveedorRepository = proveedorRepository;
        this.guiaDespachoDetalleRepository = guiaDespachoDetalleRepository;
        this.guiaDespachoDetalleService = guiaDespachoDetalleService;
    }

    @RequestMapping(value = "/findGuiaDespachoDetalleById/{idDespacho}", method = RequestMethod.GET)
    public ResponseEntity<List<GuiaDespachoDetalle>> getGuiaDespachoDetalleById(@PathVariable("idDespacho") Integer idDespacho,
                                                                                           @AuthenticationPrincipal Token token
    ){
        List<GuiaDespachoDetalle> guiaDespachoDetalleList = new ArrayList<>();
        UserSession userSession = this.getUserSession(token);

        guiaDespachoDetalleList = guiaDespachoDetalleService.getAllGuiaDepachoDetalleById(idDespacho);

        if(guiaDespachoDetalleList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(guiaDespachoDetalleList, HttpStatus.OK);
    }

}
