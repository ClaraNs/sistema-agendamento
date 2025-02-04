package com.serenaterapias.agendamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Habilita CORS para todas as rotas
                registry.addMapping("/**") // Permite todas as rotas
                        .allowedOrigins("http://127.0.0.1:5500") // Permite somente o frontend
                        .allowedMethods("GET", "POST", "PATCH", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*") // Todos os cabeçalhos
                        .allowCredentials(true); // Permite cookies, se necessário
            }
        };
    }
}
