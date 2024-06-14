package ru.cft.shiftlab.contentmaker.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.cft.shiftlab.contentmaker.controller.StoriesController;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;

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
}
