package ru.cft.shiftlab.contentmaker.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import ru.cft.shiftlab.contentmaker.dto.UserDto;

@SpringBootTest
public class KeycloakServiceTest {
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
    @Autowired
    Keycloak keycloak;

    @Test
    public void createUserFirst() throws Exception {
        //Тест создания юзера
        RealmResource realm = keycloak.realm("content-maker");
        String username = "tesaasdsdt";
        UserDto user = UserDto.builder()
                .firstName("tesaasdsdt")
                .lastName("tesaasdsdt")
                .email("panarinforwork@gmail.com")
                .password("2219")
                .build();
        keycloakService.createUser(user);
        Assertions.assertTrue(
                realm.users().search("tesaasdsdt").size() == 1
        );
        //его удаление
        realm.users().delete(realm.users().search(username, true).get(0).getId());
    }

    @Test
    public void deleteUserFirst() throws Exception {
        RealmResource realm = keycloak.realm("content-maker");
        //создание юзера
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername("tesaasdsdt");
        realm.users().create(userRepresentation);
        //тест удаления
        keycloakService.deleteUser("tesaasdsdt");
        Assertions.assertTrue(
                realm.users().search("tesaasdsdt").isEmpty()
        );
    }
}
