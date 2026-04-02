package br.com.desafiotecnicooutforce.coupon_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração de CORS da aplicação.
 *
 * Este arquivo permite que um frontend local, como Angular,
 * consiga consumir a API durante o desenvolvimento.
 */
@Configuration
public class CorsConfig {

    /**
     * Configura as origens, métodos e headers permitidos para chamadas HTTP.
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:4200",
                                "http://127.0.0.1:4200"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Content-Type", "Authorization");
            }
        };
    }
}