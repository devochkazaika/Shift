package ru.cft.shiftlab.contentmaker.Keycloak;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.cft.shiftlab.contentmaker.controller.StoriesController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StoriesController.class)
@RequiredArgsConstructor
public class KeycloakStartTest {
    @MockBean
    private final Keycloak keycloak;
    @Test
    public void createBeanTest(){
        keycloak.realm("content-maker").getClientSessionStats();
    }
}
