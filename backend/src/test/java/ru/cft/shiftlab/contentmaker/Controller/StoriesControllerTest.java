package ru.cft.shiftlab.contentmaker.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import ru.cft.shiftlab.contentmaker.controller.StoriesController;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.exceptionhandling.ValidationException;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
import ru.cft.shiftlab.contentmaker.util.WhiteList;
import ru.cft.shiftlab.contentmaker.util.validation.validator.PlatformValidator;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_TEST_DIRECTORY;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StoriesController.class)
public class StoriesControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @MockBean
    JsonProcessorService jsonProcessorService;



    @Test
    public void addStoriesTest() throws Exception {
        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "Конвертируй",
                "Обменивайте валюту онлайн по выгодному курсу",
                "FFFFFF",
                "NONE",
                "Попробовать",
                "FFFFFF",
                "FFFFFF",
                "buttonurl",
                "EMPTY");
        StoryDto storyDto = new StoryDto(
                "Конвертируй валюту",
                "FFFFFF",
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto)));

        StoriesRequestDto storiesRequestDto = new StoriesRequestDto("nskbl", "ALL PLATFORMS",
                new ArrayList<>(Collections.singletonList(storyDto)));

        String path = FILES_TEST_DIRECTORY + "sample.png";
        File img =  new File(
                FILES_TEST_DIRECTORY,
                "sample.png");
        FileInputStream input = new FileInputStream(img);
        Assertions.assertNotNull(input);

        MockMultipartFile previewImage = new MockMultipartFile(
                "previewImage",
                path,
                "image/png",
                input);

        input = new FileInputStream(img);
        Assertions.assertNotNull(input);
        MockMultipartFile cardImages = new MockMultipartFile(
                "cardImages",
                path,
                "image/png",
                input);
        Assertions.assertNotNull(cardImages);

        ResultActions andReturn = mockMvc.perform(MockMvcRequestBuilders
                .multipart("/stories/add")
                .file(previewImage)
                .file(cardImages)
                .param("json", asJsonString(storiesRequestDto))
                .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        andReturn.andExpect(status().isCreated());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void ValidationBankIdDeleteTest() throws Throwable {
        String bank = "testBanksad";
        String platform = "WEB";

        Mockito.when(jsonProcessorService.deleteService(bank, platform, "1"))
                .thenReturn(new ResponseEntity<>(HttpStatus.valueOf(202)));
        mockMvc.perform(delete("/stories/bank/info/delete")
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
            mockMvc.perform(delete("/stories/bank/info/delete")
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
        mockMvc.perform(delete("/stories/bank/info/delete")
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
            mockMvc.perform(delete("/stories/bank/info/delete")
                            .param("bankId", bank)
                            .param("platform", x)
                            .param("id", "1")
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                    )
                    .andExpect(status().is(202));
        }
    }

    @Test
    public void get_story_Test() throws Exception {
        String bank = "testBanksad";
        String platform = "WEB";
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

    @Test
    public void delete_story_successful_test() throws Throwable {
        String bank = "tkbbank";
        String platform = "WEB";
        Mockito.when(jsonProcessorService.deleteService(bank, platform, "0"))
                .thenReturn(new ResponseEntity<>(HttpStatus.valueOf(202)));;
        mockMvc.perform(delete("/stories/bank/info/delete")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("bankId", bank)
                .param("platform", platform)
                .param("id", "0"))
                .andExpect(status().is(202))
        ;
    }
    @Test
    public void delete_story_with_bad_bank_test() throws Throwable {
        String bank = "asdasd";
        String platform = "WEB";
        Mockito.when(jsonProcessorService.deleteService(bank, platform, "0"))
                .thenReturn(new ResponseEntity<>(HttpStatus.valueOf(202)));;
        mockMvc.perform(delete("/stories/bank/info/delete")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("bankId", bank)
                        .param("platform", platform)
                        .param("id", "0"))
                .andExpect(status().is(400));
    }
    @Test
    public void delete_story_with_bad_platform_test() throws Throwable {
        String bank = "tkbbank";
        String platform = "asdasdasd";
        Mockito.when(jsonProcessorService.deleteService(bank, platform, "0"))
                .thenReturn(new ResponseEntity<>(HttpStatus.valueOf(202)));;
        mockMvc.perform(delete("/stories/bank/info/delete")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("bankId", bank)
                        .param("platform", platform)
                        .param("id", "0"))
                .andExpect(status().is(400));
    }
}
