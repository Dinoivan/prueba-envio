package com.incloud.hcp.rest;

import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.service.UsuarioService;
import com.incloud.hcp.ws.ias.bean.IASResponse;
import com.incloud.hcp.ws.ias.service.IUserIASService;
import com.sap.cloud.security.xsuaa.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by MARCELO on 22/09/2017.
 */
@RestController
@RequestMapping(value = "/api/service/scim")
public class ServiceScimUsersRest extends AppRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private IUserIASService userIASService;

    @RequestMapping(value = "/Users", method = RequestMethod.GET, produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<IASResponse> gelAllUsuario(@AuthenticationPrincipal Token token) {
        IASResponse response = userIASService.getUserByEmail(token.getEmail());;


        return Optional.ofNullable(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}
