package com.projetointegrado.MeuBolso.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API REST com Spring Boot")
                        .version("1.0.0")
                        .description("API do projeto Meu Bolso: Aplicacao de Gestao Financeira"));
    }
}