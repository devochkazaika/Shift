package ru.cft.shiftlab.contentmaker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationFramesRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationRepository;
import ru.cft.shiftlab.contentmaker.service.implementation.HistoryService;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.MultipartBodyProcess;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;
import ru.cft.shiftlab.contentmaker.util.StoryMapper;
import ru.cft.shiftlab.contentmaker.util.keycloak.KeyCloak;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_TEST_DIRECTORY;

public class StoriesProcessorServiceTest {
    @Mock
    private MultipartFileToImageConverter multipartFileToImageConverter;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BannerRepository bannerRepository;

    private DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter();
    @Mock
    private StoryPresentationRepository storyPresentationRepository;
    @Mock
    private StoryPresentationFramesRepository storyPresentationFramesRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private DirProcess dirProcess;
    @Mock
    private HistoryService historyService;

    @Mock
    private StoryMapper storyMapper;

    @Mock
    private BannerRepository bannerRepository;

    @Mock
    private KeyCloak keyCloak;

    @InjectMocks
    private JsonProcessorService service;

    @BeforeEach
    public void setUp() {
        // Инициализация моков
        MockitoAnnotations.openMocks(this);

        // Передача моков в конструктор сервиса
        service = new JsonProcessorService(
                storyMapper,
                multipartFileToImageConverter,
                dtoToEntityConverter,
                dirProcess,
                storyPresentationRepository,
                storyPresentationFramesRepository,
                historyService,
                keyCloak
        );
    }

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

    StoryPresentationFrames firstFrame = StoryPresentationFrames.builder()
            .title("mainText")
            .text("Text card")
            .textColor("FF0000")
            .visibleButtonOrNone("NONE")
            .build();

    StoryPresentation storyPresentation = StoryPresentation.builder()
            .bankId("tkkbank")
            .platform("IOS")
            .storyPresentationFrames(new ArrayList<>(Arrays.asList(firstFrame)))
            .build();

    @Test
    void should_save_files() throws IOException {
        // Prepare the input JSON string
        String storiesRequestJson = storyMapper.writeValueAsString(storiesRequestDto);

        // Mock the deserialization of the JSON
        Mockito.when(storyMapper.readValue(eq(storiesRequestJson), eq(StoriesRequestDto.class)))
                .thenReturn(storiesRequestDto);

        File img =  new File(FILES_TEST_DIRECTORY, "sample.png");

        FileInputStream input = new FileInputStream(img);
        Assertions.assertNotNull(input);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                img.getName(), "image/png", IOUtils.toByteArray(input));
        var arrImg = new MultipartFile[1];
        arrImg[0] = multipartFile;
        Mockito.when(multipartFileToImageConverter.convertMultipartFileToImageAndSave(any(), any(), any()))
                .thenCallRealMethod();
        Mockito.doCallRealMethod().when(dirProcess).createFolders(any());
        Mockito.when(keyCloak.getRoles()).thenReturn(new HashSet<KeyCloak.Roles>(Arrays.asList(KeyCloak.Roles.ADMIN)));
        Mockito.doCallRealMethod().when(modelMapper).map(any(), any(StoryPresentation.class));
        Mockito.when(dtoToEntityConverter.fromStoryDtoToStoryPresentation(
                        eq(storiesRequestDto.getBankId()),
                        eq(storiesRequestDto.getPlatformType()),
                        any(),
                        any()))
                .thenCallRealMethod();
        Mockito.when(storyMapper.writerWithDefaultPrettyPrinter()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(storyMapper).writeValue(any(File.class), any(Map.class));
        Mockito.doCallRealMethod().when(storyMapper).putStoryToJson(any(List.class), any(), any());
        // Run the service method
        service.saveFiles(storiesRequestJson, multipartFile, arrImg);

        // Additional assertions and cleanup
    }

}
