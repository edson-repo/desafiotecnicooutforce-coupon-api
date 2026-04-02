package br.com.desafiotecnicooutforce.coupon_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração básica da documentação da API.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define as informações exibidas no Swagger UI.
     */
    @Bean
    public OpenAPI couponApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Coupon API")
                        .description("API para gerenciamento de cupons do desafio técnico.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Edson Oliveira"))
                        .license(new License()
                                .name("Uso para avaliação técnica")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Ambiente local")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação de referência do desafio")
                        .url("https://n1m0i5k0zu.apidog.io/"));
    }
}