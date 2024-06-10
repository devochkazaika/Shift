package ru.cft.shiftlab.contentmaker.configuration;


import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
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
    public Keycloak createBeanKeycloak() {
        var t = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8081")
                .grantType(OAuth2Constants.PASSWORD)
                .realm(REALM)
                .clientId(CLIENTID)
                .clientSecret(secret)
                .username(user)
                .password(password)
                .build();
        System.out.println(t.realm("maker").users().get("admin").roles());
        return t;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(customJwtAuthenticationConverter())));

        return http.csrf().disable().authorizeHttpRequests(authorizaHttpRequest -> authorizaHttpRequest
                        .antMatchers("/createUser").hasRole("ADMIN")
                        .antMatchers("/check-keycloak").hasRole("ADMIN")
                        .anyRequest().permitAll()
        ).build();
    }
    @Bean
    public JwtAuthenticationConverter customJwtAuthenticationConverter(){
        var converter = new JwtAuthenticationConverter();
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");

            return
                            roles.stream()
                                    .filter(role -> role.startsWith("ROLE_"))
                                    .map(SimpleGrantedAuthority::new)
                                    .map(GrantedAuthority.class::cast)
                    .collect(Collectors.toList());
        });

        return converter;
    }

//    @Bean
//    public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2UserService() {
//        var oidcUserService = new OidcUserService();
//        return userRequest -> {
//            var oidcUser = oidcUserService.loadUser(userRequest);
//            var roles = oidcUser.getClaimAsStringList("spring_sec_roles");
//            var authorities = Stream.concat(oidcUser.getAuthorities().stream(),
//                            roles.stream()
//                                    .filter(role -> role.startsWith("ROLE_"))
//                                    .map(SimpleGrantedAuthority::new)
//                                    .map(GrantedAuthority.class::cast))
//                    .toList();
//
//            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
//        };
//    }

}
