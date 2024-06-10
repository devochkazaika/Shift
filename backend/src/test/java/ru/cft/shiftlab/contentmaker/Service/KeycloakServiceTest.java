package ru.cft.shiftlab.contentmaker.Service;

import org.junit.jupiter.api.Test;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.cft.shiftlab.contentmaker.dto.UserDto;
import ru.cft.shiftlab.contentmaker.service.implementation.KeycloakServiceImpl;

@SpringBootTest
public class KeycloakServiceTest {
    @TestConfiguration
    public class KeycloakTestConfiguration {
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
            return t;
        }
    }
    @Autowired
    KeycloakServiceImpl keycloakService;

    @Test
    public void createUserFirst() throws Exception {
        UserDto user = UserDto.builder()
                .firstName("tesaasdsdt")
                .lastName("tesasdasdt")
                .email("panarinforwork@gmail.com")
                .password("2219")
                .build();
        keycloakService.createUser(user);

    }
}
