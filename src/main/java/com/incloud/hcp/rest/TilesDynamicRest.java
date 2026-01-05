package com.incloud.hcp.rest;

import com.incloud.hcp.bean.TileDynamic;
import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.TileDynamicService;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrador on 24/10/2017.
 */
@RestController
@RequestMapping(value = "/api/tiles")
public class TilesDynamicRest extends AppRest {
    @Autowired
    private TileDynamicService tileDynamicService;
    @Autowired
    private ProveedorService proveedorService;

    @RequestMapping(value = "/avance-homologacion",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> avanceHomologacion(HttpServletRequest request,@AuthenticationPrincipal Token token) {
        Map<String, TileDynamic> map = new HashMap<>();
        UserSession userSession = this.getUserSession(token);
        Proveedor p = this.proveedorService.getProveedorByRuc(userSession.getRuc());
        map.put("d", tileDynamicService.getTileToAvanceHomologacion(p));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @RequestMapping(value = "/contadorLicitacionesPorResponder",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> contadorLicitacionesPorResponder(HttpServletRequest request,@AuthenticationPrincipal Token token) {
        Map<String, TileDynamic> map = new HashMap<>();
        UserSession userSession = this.getUserSession(token);
        Proveedor p = this.proveedorService.getProveedorByRuc(userSession.getRuc());
        map.put("d", tileDynamicService.getTileLicitacionesPorResponder(p));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/contadorHistoLicitacionesPorResponder",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> contadorHistoLicitacionesPorResponder(HttpServletRequest request,@AuthenticationPrincipal Token token) {
        Map<String, TileDynamic> map = new HashMap<>();
        UserSession userSession = this.getUserSession(token);
        Proveedor p = this.proveedorService.getProveedorByRuc(userSession.getRuc());
        map.put("d", tileDynamicService.getTileHistoricoLicitacionesPorResponder(p));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @RequestMapping(value = "/contadorCComparativo",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> contadorCComparativo(HttpServletRequest request) {
        Map<String, TileDynamic> map = new HashMap<>();
        map.put("d", tileDynamicService.getTileCuadroComparativo());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/contadorHistoCComparativo",
            method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> contadorHistoCComparativo(HttpServletRequest request) {
        Map<String, TileDynamic> map = new HashMap<>();
        map.put("d", tileDynamicService.getTileHistoricoCuadroComparativo());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
