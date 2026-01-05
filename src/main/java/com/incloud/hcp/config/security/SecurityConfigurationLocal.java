package com.incloud.hcp.config.security;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Profile("uaamock")
@Configuration
@EnableWebSecurity(debug = false) // TODO "debug" may include sensitive information. Do not use in a production system!
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfigurationLocal {

    @Autowired
    XsuaaServiceConfiguration xsuaaServiceConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf(crsf -> crsf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz ->
                        authz.requestMatchers("/local/*").permitAll()
                                //
                                .requestMatchers(
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/configuration/ui",
                                        "/swagger-resources/**",
                                        "/configuration/security",
                                        "/swagger-ui.html",
                                        "/webjars/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**"
                                ).permitAll()
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(getJwtAuthenticationConverter())));
        // @formatter:on

        return http.build();
    }

    // Local dev
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/local/*").permitAll()
                //.antMatchers("/api/**").hasAuthority("ReadUC") // Otra opción por si necesitan -> Pero basarse de lo configurado en /api/local/getLocalToken (config scopes similar xs-security.json)
                .antMatchers("/api/**").permitAll() // Only for local environment. Its important consider scope to generate token in postman or other tools in some requests.
                .antMatchers("/v1/sayHello").hasAuthority("all-ip")
                .antMatchers("/v1/*").authenticated()
                .antMatchers("/v2/*").hasAuthority("all-ip")
                .antMatchers("/v3/*").hasAuthority("all-ip")
                .antMatchers("/v3/requestRefreshToken/*").hasAuthority("all-ip")
                .anyRequest().authenticated()
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(getJwtAuthenticationConverter());
    }*/

    /**
     * Customizes how GrantedAuthority are derived from a Jwt
     */
    Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        TokenAuthenticationConverter converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
        converter.setLocalScopeAsAuthorities(true);
        return converter;
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> listHttp = new ArrayList();
        listHttp.add(HttpMethod.GET.name());
        listHttp.add(HttpMethod.PUT.name());
        listHttp.add(HttpMethod.POST.name());
        listHttp.add(HttpMethod.DELETE.name());
        listHttp.add(HttpMethod.PATCH.name());
        listHttp.add(HttpMethod.HEAD.name());
        listHttp.add(HttpMethod.OPTIONS.name());
        listHttp.add(HttpMethod.TRACE.name());

        configuration.setAllowedMethods(listHttp);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }

}
