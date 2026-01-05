package com.incloud.hcp.rest;

import com.incloud.hcp.bean.UserSession;
import com.incloud.hcp.rest._framework.AppRest;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/current-session")
public class CurrentSessionRest extends AppRest {


    @GetMapping(value = "/logged-user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserSession> getCurrentSession(@AuthenticationPrincipal Token token) {
        UserSession userSession = this.getUserSession(token);
        return new ResponseEntity<>(userSession, HttpStatus.OK);
    }
}
