package com.incloud.hcp.config.security;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {

    /*@Bean
    RestTemplate myRestTemplate() {
        return new RestTemplate();
    }*/

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /*@Bean
    RestTemplate xsuaaRestOperations() {
        return new RestTemplate();
    }*/

}
