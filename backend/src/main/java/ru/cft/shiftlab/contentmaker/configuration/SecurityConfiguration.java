package ru.cft.shiftlab.contentmaker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .authorizeHttpRequests((authz) ->
                                authz
//                            .antMatchers("/stories/bank/info/**").hasAnyRole("ADMIN", "USER")
//                            .antMatchers("/backend/**").authenticated()
//                            .antMatchers("/stories/add/**").authenticated()
//                            .antMatchers("/stories/add").authenticated()
                                        .anyRequest().permitAll()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        final var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        converter.setPrincipalClaimName("preferred_username");
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");

            if (resourceAccess == null || !resourceAccess.containsKey("maker")) {
                return authorities;
            }

            Map<String, List<String>> makerAccess = (Map<String, List<String>>) resourceAccess.get("maker");
            if (makerAccess == null || !makerAccess.containsKey("roles")) {
                return authorities;
            }

            List<String> roles = makerAccess.get("roles");

            return Stream.concat(
                    authorities.stream(),
                    roles.stream()
                            .map(role -> "ROLE_" + role)
                            .map(SimpleGrantedAuthority::new)
                            .map(GrantedAuthority.class::cast)
            ).collect(Collectors.toList());
        });
        return converter;
    }
}
