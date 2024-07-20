package ru.cft.shiftlab.contentmaker.Keycloak;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(value = HelloWorldController.class)
@RequiredArgsConstructor
public class KeycloakStartTest {
    @MockBean
    private Keycloak keycloak;
    @MockBean
    private KeycloakServiceImpl keycloakServiceImpl;
//    @Autowired
//    private MockMvc mockMvc;
//    @Test
//    public void createBeanTest() throws Exception {
//        Mockito.when(keycloakService.getRealm()).thenCallRealMethod();
//        Mockito.when(keycloakService.getRealm().getClientSessionStats()).thenCallRealMethod();
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/check-keycloak")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()
//                );
//    }


}
