package com.autobots.automanager.configuracao;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // Define as informações gerais da sua API que aparecerão no topo da página do Swagger.
        Info info = new Info()
            .title("AutoManager API Gateway")
            .version("1.0")
            .description("API Gateway para o sistema de gerenciamento AutoManager.")
            .license(new License().name("Apache 2.0").url("http://springdoc.org"));

        // Define o esquema de segurança JWT (Bearer Authentication)
        final String securitySchemeName = "bearerAuth";
        SecurityScheme securityScheme = new SecurityScheme()
            .name(securitySchemeName)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");
        
        Components components = new Components().addSecuritySchemes(securitySchemeName, securityScheme);

        // Adiciona o requisito de segurança globalmente a todos os endpoints
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);

        return new OpenAPI()
            .info(info)
            .components(components)
            .addSecurityItem(securityRequirement);
    }
}