package com.incloud.hcp.rest.xsuaa;

import com.sap.cloud.security.xsuaa.client.OAuth2TokenResponse;
import com.sap.cloud.security.xsuaa.token.Token;
import com.sap.cloud.security.xsuaa.tokenflows.TokenFlowException;
import com.sap.cloud.security.xsuaa.tokenflows.XsuaaTokenFlows;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by COROPEZA on 01/09/2021.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/index")
public class IndexController {

    private final XsuaaTokenFlows tokenFlows;
    //private final IUserIASService userIASService;
    //private final CmisBaseService cmisBaseServicecf;

    /*@Value("${sm.portal.dev}")
    private Boolean isLocal;*/

    @GetMapping(value = "/xsuaa-current-user", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> currentUser(HttpServletRequest request, @AuthenticationPrincipal Token token) {

        Map<String, String> result = new HashMap<>();

        /*if (isLocal) {

            result.put("grant_type", "authorization_code");
            result.put("client_id", "sb-iprovider-api!t54225");
            result.put("subaccount_id", "04c8a422-1f54-4304-b6c3-a01b84eb33ee");
            result.put("zone_id", "04c8a422-1f54-4304-b6c3-a01b84eb33ee");
            result.put("logon_name", "cleon@csticorp.biz");
            result.put("family_name", "Leon");
            result.put("given_name", "Carlos");
            result.put("email", "cleon@csticorp.biz");
            result.put("authorities", null);
            result.put("scopes", null);

            return new ResponseEntity<>(result, HttpStatus.OK);

        }*/

        result.put("grant_type", token.getGrantType());
        result.put("client_id", token.getClientId());
        result.put("subaccount_id", token.getSubaccountId());
        result.put("zone_id", token.getZoneId());
        result.put("logon_name", token.getLogonName());
        result.put("family_name", token.getFamilyName());
        result.put("given_name", token.getGivenName());
        result.put("email", token.getEmail());
        result.put("authorities", String.valueOf(token.getAuthorities()));
        //result.put("identificationCompany", ruc);
        result.put("scopes", String.valueOf(token.getScopes()));
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping(value = "/xsuaa-token", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getToken(@AuthenticationPrincipal Token token) throws TokenFlowException {
        OAuth2TokenResponse userTokenResponse = tokenFlows.jwtBearerTokenFlow()
                .token(token.getAppToken())
                .subdomain(token.getSubdomain())
                .execute();
        return new ResponseEntity<>(userTokenResponse.getAccessToken(), HttpStatus.OK);
    }

    /*@GetMapping(value = "/ias-user-info", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<IASResponse> userInfoIAS(HttpServletRequest request, @AuthenticationPrincipal Token token) {
        IASResponse response = userIASService.getUserByEmail(token.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/ias-user-all", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('AdminIP')")
    public ResponseEntity<IASResponse> userInfoAll(HttpServletRequest request, @AuthenticationPrincipal Token token) {
        IASResponse response = userIASService.getUserAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/ias-user-create")
    @PreAuthorize("hasAuthority('AdminIP')")
    public ResponseEntity<IASResponse> userCreate(@RequestBody IASUserDto userDto, @AuthenticationPrincipal Token token) {
        IASResponse response = userIASService.createUserProveedor(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/

}
