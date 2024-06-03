package ru.cft.shiftlab.contentmaker.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import ru.cft.shiftlab.contentmaker.controller.StoriesController;
import ru.cft.shiftlab.contentmaker.exceptionhandling.ValidationException;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
import ru.cft.shiftlab.contentmaker.util.WhiteList;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StoriesController.class)
public class GetStoriesRequestTest {
    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @MockBean
    JsonProcessorService jsonProcessorService;

    @Test
    public void getTest() throws Exception {
        String bank = "testBanksad";
        String platform = "asdas";
        Mockito.when(jsonProcessorService.getFilePlatform(bank, platform))
                .thenThrow(new ValidationException());
        mockMvc.perform(get("/stories/bank/info")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("bankId", bank)
                .param("platform", platform)
        ).andExpect(status().isBadRequest());
        for (Map.Entry<String, String> x : WhiteList.whitelistBank.entrySet()) {
            HttpEntity<MultiValueMap<String, HttpEntity<?>>> testdata = new HttpEntity<>(new MultipartBodyBuilder().build());
            Mockito.when(jsonProcessorService.getFilePlatform(x.getKey(), platform))
                    .thenReturn(testdata);
            try {
                mockMvc.perform(get("/stories/bank/info")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("bankId", x.getKey())
                        .param("platform", platform)
                ).andExpect(status().isFound());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
