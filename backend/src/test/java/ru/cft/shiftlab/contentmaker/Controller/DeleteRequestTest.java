package ru.cft.shiftlab.contentmaker.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.cft.shiftlab.contentmaker.controller.StoriesController;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
import ru.cft.shiftlab.contentmaker.util.WhiteList;
import ru.cft.shiftlab.contentmaker.util.validation.validator.PlatformValidator;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StoriesController.class)
public class DeleteRequestTest {
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
    public void ValidationBankIdDeleteTest() throws Throwable {
        String bank = "testBanksad";
        String platform = "WEB";

        Mockito.when(jsonProcessorService.deleteService(bank, platform, "1"))
                .thenReturn(new ResponseEntity<>(HttpStatus.valueOf(202)));
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/stories/bank/info/delete")
                .param("bankId", bank)
                .param("platform", platform)
                .param("id", "1")
                .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andExpect(status().is(400));
        //Перебор всех доступных BankId
        for (Map.Entry<String, String> x : WhiteList.whitelistBank.entrySet()) {
            Mockito.when(jsonProcessorService.deleteService(x.getKey(), platform, "1"))
                    .thenReturn(new ResponseEntity<>(HttpStatus.valueOf(202)));
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/stories/bank/info/delete")
                            .param("bankId", x.getKey())
                            .param("platform", platform)
                            .param("id", "1")
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                    )
                    .andExpect(status().is(202));
        }
    }
    @Test
    public void ValidationPlatformDeleteTest() throws Throwable {
        String bank = "tkbbank";
        String platform = "asdasdasd";

        Mockito.when(jsonProcessorService.deleteService(bank, platform, "1"))
                .thenReturn(new ResponseEntity<>(HttpStatus.valueOf(202)));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/stories/bank/info/delete")
                        .param("bankId", bank)
                        .param("platform", platform)
                        .param("id", "1")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().is(400));
        //Перебор всех доступных Platform
        for (String x : PlatformValidator.platforms) {
            Mockito.when(jsonProcessorService.deleteService(bank, x, "1"))
                    .thenReturn(new ResponseEntity<>(HttpStatus.valueOf(202)));
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/stories/bank/info/delete")
                            .param("bankId", bank)
                            .param("platform", x)
                            .param("id", "1")
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                    )
                    .andExpect(status().is(202));
        }
    }
}
