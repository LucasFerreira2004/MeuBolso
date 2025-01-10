package com.projetointegrado.MeuBolso.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("API REST com Spring Boot")
                .description("API do projeto Meu Bolso: Aplicacao de Gestao Financeira"))
                    .addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
                    .components(new Components().addSecuritySchemes("JavaInUseSecurityScheme", new SecurityScheme()
                            .name("JavaInUseSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }
}
