package ru.cft.shiftlab.contentmaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.cft.shiftlab.contentmaker.controller.StoriesController;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StoriesController.class)
public class DeleteStoriesRequestTest {
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
    public void deleteTest() throws Exception {

        Mockito.doThrow(new Exception()).when(jsonProcessorService).getFilePlatform("testBank", "asdas");

        mockMvc.perform(get("/bank/info")
                        .
                .contentType(MediaType.MULTIPART_FORM_DATA)
        );
    }
}
