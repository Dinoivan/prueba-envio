package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.domain.CriteriosBlacklist;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.CriterioBlackListService;
import com.sap.cloud.security.xsuaa.token.Token;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by MARCELO on 13/09/2017.
 */
@RestController
@RequestMapping(value = "/api/criterio-blackList")
public class CriterioBlackListRest extends AppRest {

    @Autowired
    private CriterioBlackListService criterioBlackListService;

    /*@RequestMapping(value = "", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<CriteriosBlacklist>> getAllCriterioBlackList() {
        return Optional.of(this.criterioBlackListService.getAllCriterioBlackList())
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }*/

    @RequestMapping(value = "/{codTipoSolicitud}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<CriteriosBlacklist>> getListCriterioByTipoSolicitud(@PathVariable("codTipoSolicitud") int codTipoSolicitud) {
        return Optional.of(this.criterioBlackListService.getCriterioBlackListByTipoSolicitud(codTipoSolicitud))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> save(@RequestBody CriteriosBlacklist criterio,@AuthenticationPrincipal Token token) {
        UserSession user = this.getUserSession(token);
        criterio.setUsuarioCreacion(user.getId());
        criterio.setUsuarioModificacion(user.getId());
        return criterioBlackListService.save(criterio);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> edit( @RequestBody CriteriosBlacklist criterio,@AuthenticationPrincipal Token token) {
        UserSession user = this.getUserSession(token);
        criterio.setUsuarioModificacion(user.getId());
        return criterioBlackListService.update(criterio);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Map> delete(HttpServletRequest request, @RequestBody CriteriosBlacklist criterio) {
        return criterioBlackListService.delete(criterio);
    }

}
