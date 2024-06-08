package ru.cft.shiftlab.contentmaker.configuration;


import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class KeyCloakConfiguration {
    @Value("${keycloak.realm}")
    public String REALM;
    @Value("${keycloak.resource}")
    public String CLIENTID;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String secret;
    @Value("${spring.security.user.name}")
    private String user;
    @Value("${spring.security.user.password}")
    private String password;
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public Keycloak getAdminKeycloakUser() {
        var t = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8081")
                .grantType(OAuth2Constants.PASSWORD)
                .realm(REALM)
                .clientId(CLIENTID)
                .clientSecret(secret)
                .username("admin")
                .password("password")
                .build();
        return t;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable().authorizeHttpRequests(authorizaHttpRequest -> authorizaHttpRequest
                        .anyRequest().permitAll()

//                        .antMatchers("/createUser").permitAll())
        ).build();
    }
}
