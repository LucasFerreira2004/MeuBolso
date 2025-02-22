package com.projetointegrado.MeuBolso.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permite o CORS para todas as rotas
                .allowedOriginPatterns("*") // Permite requisições de qualquer origem
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Inclui PATCH
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(true); // Se estiver usando autenticação baseada em cookies
    }
}