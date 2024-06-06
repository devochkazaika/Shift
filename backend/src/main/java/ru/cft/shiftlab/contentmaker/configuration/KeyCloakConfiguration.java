package ru.cft.shiftlab.contentmaker.configuration;


import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class KeyCloakConfiguration {
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public Keycloak getAdminKeycloakUser() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8081")
                .grantType(OAuth2Constants.PASSWORD)
                .realm("master")
                .clientId("maker")
                .username("admin")
                .password("password")
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(authorizaHttpRequest -> authorizaHttpRequest
                        .anyRequest().permitAll()).build();
    }
//    @Bean
//    public Keycloak keycloak() {
//        return KeycloakBuilder.builder()
//                .serverUrl("http://localhost:8081/auth")
//                .realm("content-maker")
//                .clientId("test")
//                .username("admin")
//                .password("password")
//                .build();
//    }
}
