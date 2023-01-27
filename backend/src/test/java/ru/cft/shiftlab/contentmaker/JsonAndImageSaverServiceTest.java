package ru.cft.shiftlab.contentmaker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.shiftlab.contentmaker.dto.StoriesDto;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.services.implementations.JsonAndImageSaverService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JsonAndImageSaverServiceTest {

    @InjectMocks
    private JsonAndImageSaverService jsonAndImageSaverService;

    @Test
    void should_save_files() {
        byte[] bytes = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
        StoryFramesDto storyFramesDto = new StoryFramesDto("Конвертируй",
                "Обменивайте валюту онлайн по выгодному курсу", "FFFFFF", bytes, "text",
                "link url", true, "Попробовать", "FFFFFF",
                "FFFFFF", "buttonurl", "EMPTY");
        StoriesDto storiesDto = new StoriesDto("Конвертируй валюту", "FFFFFF",
                bytes, "EMPTY", new ArrayList<>(Collections.singletonList(storyFramesDto)));
        StoriesRequestDto storiesRequestDto = new StoriesRequestDto(new ArrayList<>(Collections.singletonList(storiesDto)));

        jsonAndImageSaverService.saveFiles(storiesRequestDto);

        Path path = Paths.get("/content-maker/backend/src/main/resources/data/stories.json");
        File file = path.toFile();

        assertAll(
                () -> assertTrue(Files.exists(file.toPath()), "File should exist"),
                () -> assertLinesMatch(Collections.singletonList(asJsonString(storiesRequestDto)),
                        Files.readAllLines(file.toPath())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
