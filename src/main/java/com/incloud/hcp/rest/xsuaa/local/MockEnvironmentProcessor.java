package com.incloud.hcp.rest.xsuaa.local;

import com.sap.cloud.security.xsuaa.mock.XsuaaMockWebServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Profiles;

public class MockEnvironmentProcessor implements EnvironmentPostProcessor, DisposableBean {
	
	private final XsuaaMockWebServer mockAuthorizationServer = new XsuaaMockWebServer();
	
	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		if (environment.acceptsProfiles(Profiles.of("uaamock"))) {
            environment.getPropertySources().addFirst(this.mockAuthorizationServer);
        }
	}

	@Override
    public void destroy() throws Exception {
        this.mockAuthorizationServer.destroy();
    }

    // IMPORTANTE AGREGAR EL PACKAGE
    // ADD ON resources/META-INF/spring.factories
    //org.springframework.boot.env.EnvironmentPostProcessor=com.oropeza.controller.MockEnvironmentProcessor
}
