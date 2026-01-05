package com.incloud.hcp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    //final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI CustomOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Portal de Proveedores - iProvider")
                        .version("2.0.0")
                        .description("Sistema Backend versión 2.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("CSTI Corp.").email("contactenos@csticorp.biz")))
                //
                .addServersItem(new Server().url("/")); // Esto ayuda a configurar la opción server del swagger en approuter
                //.components(new Components().addSchemas("AnyType", new Schema<>().type("object").additionalProperties(true)));
                /*.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );*/
        //
    }

}

