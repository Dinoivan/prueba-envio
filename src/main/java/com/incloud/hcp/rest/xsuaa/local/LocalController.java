package com.incloud.hcp.rest.xsuaa.local;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.test.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("local")
public class LocalController {

    @Autowired
    private XsuaaServiceConfiguration xsuaaServiceConfiguration;

    @GetMapping("getLocalToken")
    public String getLocalToken() {
        String token = new JwtGenerator().setUserName("jescudero@csticorp.biz")
				.addScopes(getGlobalScope("all-ipe"))
//                .addScopes(getGlobalScope("ReadIP"))
                .getTokenForAuthorizationHeader();

        return token;
    }

    private String getGlobalScope(String localScope) {
        return xsuaaServiceConfiguration.getAppId() + "." + localScope;
    }
}
