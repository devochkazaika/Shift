package ru.cft.shiftlab.contentmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.cft.shiftlab.contentmaker.controller.StoriesController;
import ru.cft.shiftlab.contentmaker.dto.StoriesDto;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.services.implementations.JsonAndImageSaverService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StoriesController.class)
public class StoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JsonAndImageSaverService jsonAndImageSaverService;

    @Test
    public void addStoriesTest() throws Exception {
        byte[] bytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        StoryFramesDto storyFramesDto = new StoryFramesDto("Конвертируй",
                "Обменивайте валюту онлайн по выгодному курсу", "FFFFFF", bytes, "text",
                "link url", true, "Попробовать", "FFFFFF",
                "FFFFFF", "buttonurl", "EMPTY");
        StoriesDto storiesDto = new StoriesDto("Конвертируй валюту", "FFFFFF", bytes, "EMPTY", new ArrayList<>(Collections.singletonList(storyFramesDto)));
        System.out.println(storiesDto);
        StoriesRequestDto storiesRequestDto = new StoriesRequestDto(new ArrayList<>(Collections.singletonList(storiesDto)));


        mockMvc.perform(post("/stories/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(storiesRequestDto)))
                .andExpect(status().isOk());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
