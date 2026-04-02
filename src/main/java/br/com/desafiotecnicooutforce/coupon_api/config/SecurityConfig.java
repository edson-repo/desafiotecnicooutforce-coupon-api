package br.com.desafiotecnicooutforce.coupon_api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança da aplicação.
 *
 * permitir acesso ao Swagger e ao H2 Console.
 */
@Configuration
public class SecurityConfig {

    /**
     * Define a cadeia de filtros de segurança da aplicação.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Como é uma API simples sem login, o CSRF é desabilitado.
                .csrf(csrf -> csrf.disable())

                // Permite uso do H2 Console em frame.
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                // Libera todos os endpoints para este desafio técnico.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/h2-console/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().permitAll()
                )

                // Mantém a configuração padrão do Spring Security.
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}