package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.dto.SolicitudBlackListDto;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.BlackListService;
import com.incloud.hcp.util.Error;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;


/**
 * Created by Administrador on 12/09/2017.
 */
@RestController
@RequestMapping(value = "/api/blacklist")
public class BlackListRest extends AppRest {

    @Autowired
    private BlackListService blackListService;

    @RequestMapping(value = "/solicitud",
            method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> save(@Valid @RequestBody SolicitudBlackListDto solicitud, BindingResult result,@AuthenticationPrincipal Token token) {
        if (result.hasErrors()) {
            PortalException ex = new PortalException("Información inválida");
            result.getFieldErrors().stream().forEach(f -> ex.addDetail(f.getField(), f.getDefaultMessage()));
            throw ex;
        }
        UserSession user = this.getUserSession(token);
        solicitud.setCorreo(user.getMail());
        solicitud.setUsuario(user.getDisplayName());
        solicitud.setId(user.getId());
        return this.procesar(blackListService.registrar(solicitud));
    }

    @RequestMapping(value = "/proveedor/{ruc}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> findSociedadByRuc(@PathVariable("ruc") String ruc) {

        Optional<SolicitudBlackListDto> optional = Optional.ofNullable(this.blackListService.getSociedadByRuc(ruc));

        if (!optional.isPresent()) {
            List<Error> errors = new ArrayList<>();
            errors.add(new Error("ruc", "No existe"));
            throw new PortalException("Información inválida", errors);
        }

        return this.procesar(optional.get());
    }

    @RequestMapping(value = "/tipo-solicitud",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListTipoSolicitud(){
        return this.procesar( this.blackListService.getListSolicitudBlackList());
    }

    @RequestMapping(value = "/tipo-solicitud/{idTipoSolicitud}/criterios",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getListCriteriosByTipoSolicitud(@PathVariable("idTipoSolicitud") int idTipoSolicitud){
        return this.procesar(this.blackListService.getListCriteriosByTipoSolicitud(idTipoSolicitud));
    }

    @RequestMapping(value = "/lista-solicitud-generada", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<SolicitudBlacklist>> getListSolicitudBlackList()  {
        List<SolicitudBlacklist> lista = this.blackListService.getListSolicitudesGeneradas();
        return ResponseEntity.ok().body(lista);
    }


    @RequestMapping(value = "/lista-solicitud-pendiente-aprobacion", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<List<SolicitudBlacklist>> getListSolicitudPendienteBlackList(@AuthenticationPrincipal Token token)  {
        List<SolicitudBlacklist> lista = this.blackListService.getListSolicitudesPendientesApprobacionByUser(token);
        return ResponseEntity.ok().body(lista);
    }


    @RequestMapping(value = "/asignar-aprobadores", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> asignarAprobador(@RequestBody SolicitudBlacklist solicitud,@AuthenticationPrincipal Token token)   {

        String response = "";
        try{
            response = this.blackListService.setAprobadorBlackList(solicitud,token);
        }catch(Exception e){
            response = e.getMessage();
        }

        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @RequestMapping(value = "/aprobar-solicitud", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> aprobarSolicitud(@RequestBody SolicitudBlacklist solicitud,@AuthenticationPrincipal Token token)   {

        String response = "";
        try{
            response = this.blackListService.aprobarSolicitud(solicitud.getIdSolicitud(),token);
        }catch(Exception e){
            response = e.getMessage();
        }

        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @RequestMapping(value = "/rechazar-solicitud", method = RequestMethod.POST, headers = "Accept=application/json")
     public ResponseEntity<String> rechazarSolicitud(@RequestBody SolicitudBlacklist solicitud,@AuthenticationPrincipal Token token)   {

        String response = "";
        try{
            response = this.blackListService.rechazarSolicitud(solicitud,token);
        }catch(Exception e){
            response = e.getMessage();
        }

        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/rechazar-solicitud-admin", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> rechazarSolicitudAdmin(@RequestBody SolicitudBlacklist solicitud,@AuthenticationPrincipal Token token)   {

        String response = "";
        try{
            response = this.blackListService.rechazarSolicitudAdmin(solicitud,token);
        }catch(Exception e){
            response = e.getMessage();
        }

        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    private ResponseEntity<Map> procesar(List list) {
        return Optional.ofNullable(list)
                .map(p -> {
                    Map response = new HashMap();
                    response.put("data", p);
                    return response;
                })
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<Map> procesar(SolicitudBlackListDto proveedor) {
        return Optional.ofNullable(proveedor)
                .map(p -> {
                    Map response = new HashMap();
                    response.put("data", p);
                    return response;
                })
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}