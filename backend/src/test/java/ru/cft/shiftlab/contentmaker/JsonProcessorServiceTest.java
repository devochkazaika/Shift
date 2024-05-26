package ru.cft.shiftlab.contentmaker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
import ru.cft.shiftlab.contentmaker.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static ru.cft.shiftlab.contentmaker.util.Constants.*;

@ExtendWith(MockitoExtension.class)
public class JsonProcessorServiceTest {

    private final MultipartFileToImageConverter multipartFileToImageConverter = new MultipartFileToImageConverter(new ImageNameGenerator());


    private final DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());
    private final ImageNameGenerator imageNameGenerator = new ImageNameGenerator();

    private final JsonProcessorService jsonProcessorService = new JsonProcessorService(
            multipartFileToImageConverter,
            dtoToEntityConverter);

    @Test
    void should_save_files() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "mainText",
                "Text card",
                "FF0000",
                "NONE",
                "",
                "",
                "",
                "",
                "EMPTY");
        StoryDto storyDto = new StoryDto(
                "MainTextPreview",
                "FF0000",
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto)));
        StoriesRequestDto storiesRequestDto = new StoriesRequestDto(
                "tkbbank",
                "IOS",
                new ArrayList<>(Collections.singletonList(storyDto)));

        File img =  new File(
                FILES_TEST_DIRECTORY,
                "sample.png");
        System.out.println(img.getAbsolutePath());
        FileInputStream input = new FileInputStream(img);
        Assertions.assertNotNull(input);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                img.getName(), "image/png", IOUtils.toByteArray(input));
        var arrImg = new MultipartFile[1];
        arrImg[0] = multipartFile;

        DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());

        String picturesDirectory = FILES_SAVE_DIRECTORY
                + storiesRequestDto.getBankId() + "/" + storiesRequestDto.getPlatformType();
        String fileName = "story_tkbbank_iOS.json";
        String jsonDirectory = FILES_SAVE_DIRECTORY;
        String previewUrl = picturesDirectory + "/preview1.png";

        List<StoryPresentation> storyPresentationList = new ArrayList<>();

        storyPresentationList.add(dtoToEntityConverter.fromStoryDtoToStoryPresentation(
                "tkbbank",
                storyDto,
                previewUrl));

        Map<String, List<StoryPresentation>> presentationList = new HashMap<>();
        presentationList.put("stories", storyPresentationList);

        File jsonFile = Paths.get(jsonDirectory + fileName).toFile();

        int countFiles = getCountFilesInDir(storiesRequestDto);
        jsonProcessorService.saveFiles("\"" + StringEscapeUtils.escapeJson(objectMapper.writeValueAsString(storiesRequestDto)) + "\"",
                multipartFile,
                arrImg
        );
        Assertions.assertEquals(countFiles + 2, getCountFilesInDir(storiesRequestDto));

        StringBuilder jsonStr = new StringBuilder();
        Files.readAllLines(jsonFile.toPath()).forEach(jsonStr::append);
        Map<String, List<StoryPresentation>> map = objectMapper.readValue(
                jsonStr.toString(),
                new TypeReference<Map<String, List<StoryPresentation>>>() {}
        );

        var storyFromJson = map.get(STORIES).get(0);
        storyFromJson.setPreviewUrl(null);
        for(StoryPresentationFrames card : storyFromJson.getStoryPresentationFrames()){
            card.setPictureUrl(null);
        }

        try{
            Assertions.assertAll(
                    () -> Assertions.assertEquals(countFiles + 2, getCountFilesInDir(storiesRequestDto), "Images should be created"),
                    () -> Assertions.assertTrue(Files.exists(jsonFile.toPath()), "File should exist"),
                    () -> Assertions.assertEquals(storyFromJson.getPreviewTitle(), storyDto.getPreviewTitle()),
                    () -> Assertions.assertEquals(storyFromJson.getPreviewGradient(), storyDto.getPreviewGradient()),
                    () -> Assertions.assertEquals(storyFromJson.getPreviewTitleColor(), storyDto.getPreviewTitleColor()),
                    () -> Assertions.assertEquals(storyFromJson.getStoryPresentationFrames().get(0).getText(),
                            storyDto.getStoryFramesDtos().get(0).getText()),
                    () -> Assertions.assertEquals(storyFromJson.getStoryPresentationFrames().get(0).getGradient(),
                            storyDto.getStoryFramesDtos().get(0).getGradient()),
                    () -> Assertions.assertEquals(storyFromJson.getStoryPresentationFrames().get(0).getButtonUrl(),
                            storyDto.getStoryFramesDtos().get(0).getButtonUrl()),
                    () -> Assertions.assertEquals(storyFromJson.getStoryPresentationFrames().get(0).getTitle(),
                            storyDto.getStoryFramesDtos().get(0).getTitle())
            );
        }catch (Exception e){
            e.printStackTrace();
        }

        FileUtils.deleteQuietly(jsonFile);
        FileUtils.deleteDirectory(new File(picturesDirectory));
    }

    @Test
    void should_save_filesWEB() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        StoryFramesDto storyFramesDto = new StoryFramesDto(
                "mainText",
                "Text card",
                "FF0000",
                "NONE",
                "",
                "",
                "",
                "",
                "EMPTY");
        StoryDto storyDto = new StoryDto(
                "MainTextPreview",
                "FF0000",
                "EMPTY",
                new ArrayList<>(Collections.singletonList(storyFramesDto)));
        StoriesRequestDto storiesRequestDto = new StoriesRequestDto(
                "tkbbank",
                "WEB",
                new ArrayList<>(Collections.singletonList(storyDto)));
        File img =  new File(
                FILES_TEST_DIRECTORY,
                "sample.png");
        System.out.println(img.getAbsolutePath());
        FileInputStream input = new FileInputStream(img);
        Assertions.assertNotNull(input);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                img.getName(), "image/png", IOUtils.toByteArray(input));
        var arrImg = new MultipartFile[1];
        arrImg[0] = multipartFile;

        DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());

        String picturesDirectory = FILES_SAVE_DIRECTORY
                + storiesRequestDto.getBankId() + "/" + storiesRequestDto.getPlatformType();
        String fileName = "story_tkbbank_web.json";
        String jsonDirectory = FILES_SAVE_DIRECTORY;
        String previewUrl = picturesDirectory + "/preview1.png";

        List<StoryPresentation> storyPresentationList = new ArrayList<>();

        storyPresentationList.add(dtoToEntityConverter.fromStoryDtoToStoryPresentation(
                "tkbbank",
                storyDto,
                previewUrl));

        Map<String, List<StoryPresentation>> presentationList = new HashMap<>();
        presentationList.put("web", storyPresentationList);

        File jsonFile = Paths.get(jsonDirectory + fileName).toFile();

        int countFiles = getCountFilesInDir(storiesRequestDto);
        jsonProcessorService.saveFiles("\"" + StringEscapeUtils.escapeJson(objectMapper.writeValueAsString(storiesRequestDto)) + "\"",
                multipartFile,
                arrImg
        );
        Assertions.assertEquals(countFiles + 2, getCountFilesInDir(storiesRequestDto));

        StringBuilder jsonStr = new StringBuilder();
        System.out.println(jsonFile.toPath());
        Files.readAllLines(jsonFile.toPath()).forEach(jsonStr::append);
        Map<String, List<StoryPresentation>> map = objectMapper.readValue(
                jsonStr.toString(),
                new TypeReference<Map<String, List<StoryPresentation>>>() {}
        );

        var storyFromJson = map.get(STORIES).get(0);
        storyFromJson.setPreviewUrl(null);
        for(StoryPresentationFrames card : storyFromJson.getStoryPresentationFrames()){
            card.setPictureUrl(null);
        }
    }

    private int getCountFilesInDir(StoriesRequestDto storiesRequestDto) {
        File f = new File(
                FILES_SAVE_DIRECTORY +
                        storiesRequestDto.getBankId() + "/" + storiesRequestDto.getPlatformType() + "/");
        File[] files = f.listFiles();
        if(files == null){
            return 0;
        }
        return files.length;
    }
}