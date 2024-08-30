package ru.cft.shiftlab.contentmaker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
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
import ru.cft.shiftlab.contentmaker.util.Image.ImageNameGenerator;
import ru.cft.shiftlab.contentmaker.util.MultipartBodyProcess;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;
import ru.cft.shiftlab.contentmaker.util.StoryMapper;
import ru.cft.shiftlab.contentmaker.util.keycloak.KeyCloak;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_TEST_DIRECTORY;

public class StoriesProcessorServiceTest {
    @Mock
    private MultipartFileToImageConverter multipartFileToImageConverter;

    @Mock
    private ModelMapper modelMapper;
    private DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper(),
            new MultipartFileToImageConverter(new ImageNameGenerator()));
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
        Mockito.when(storyPresentationRepository.save(any())).thenReturn(storyPresentation);
        Mockito.when(storyPresentationFramesRepository.save(any())).thenReturn(firstFrame);
        Mockito.doCallRealMethod().when(modelMapper).map(any(), any(StoryPresentation.class));
        Mockito.when(storyMapper.writerWithDefaultPrettyPrinter()).thenCallRealMethod();
        try {
            Mockito.doCallRealMethod().when(storyMapper).writeValue(any(File.class), any(Map.class));
            Mockito.doCallRealMethod().when(storyMapper).putStoryToJson(any(List.class), any(), any());
            Mockito.doCallRealMethod().when(dirProcess).createFolders(any());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            .id(UUID.randomUUID())
            .title("mainText")
            .text("Text card")
            .textColor("FF0000")
            .visibleButtonOrNone("NONE")
            .build();
    StoryPresentationFrames secondFrame = StoryPresentationFrames.builder()
            .id(UUID.randomUUID())
            .title("mainText")
            .text("Text card")
            .textColor("FF0000")
            .visibleButtonOrNone("NONE")
            .build();

    StoryPresentation storyPresentation = StoryPresentation.builder()
            .id(15L)
            .bankId("tkbbank")
            .platform("IOS")
            .storyPresentationFrames(new ArrayList<>(Arrays.asList(firstFrame)))
            .build();


    @Test
    @DisplayName("Сохранение истории")
    void save_story() throws IOException {
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
        Mockito.when(keyCloak.getRoles()).thenReturn(new HashSet<KeyCloak.Roles>(Arrays.asList(KeyCloak.Roles.ADMIN)));

        // Run the service method
        var story = service.saveFiles(storiesRequestJson, multipartFile, arrImg);
        Assertions.assertAll(
                () -> Assertions.assertEquals(story.getId(), 15L),
                () -> Assertions.assertEquals(story.getPlatform(), storiesRequestDto.getPlatform()),
                () -> Assertions.assertEquals(story.getBankId(), storiesRequestDto.getBankId())
        );
        // Additional assertions and cleanup
    }

    @Test
    @DisplayName("Сохранение сущности истории в зависимости для ADMIN")
    public void save_story_by_role_ADMIN_test() throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Mockito.when(keyCloak.getRoles()).thenReturn(new HashSet<KeyCloak.Roles>(Arrays.asList(KeyCloak.Roles.ADMIN)));
        Mockito.doCallRealMethod().when(storyMapper).putStoryToJson(any(List.class), any(), any());

        Method method = JsonProcessorService.class.getDeclaredMethod("saveByRoles",
                StoryPresentation.class);
        method.setAccessible(true); //делает метод публичным
        var story = (StoryPresentation) method.invoke(service, storyPresentation);

        Assertions.assertEquals(story.getApproved(), StoryPresentation.Status.APPROVED);
        verify(storyMapper, times(1)).putStoryToJson(any(List.class), eq("tkbbank"), eq("IOS"));
    }

    @Test
    @DisplayName("Сохранение сущности истории в зависимости для USER")
    public void save_story_by_role_USER_test() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Mockito.when(keyCloak.getRoles()).thenReturn(new HashSet<KeyCloak.Roles>(Arrays.asList(KeyCloak.Roles.USER)));
        Mockito.doCallRealMethod().when(storyMapper).putStoryToJson(any(List.class), any(), any());

        Method method = JsonProcessorService.class.getDeclaredMethod("saveByRoles",
                StoryPresentation.class);
        method.setAccessible(true); //делает метод публичным
        var story = (StoryPresentation) method.invoke(service, storyPresentation);

        Assertions.assertEquals(story.getApproved(), StoryPresentation.Status.NOTAPPROVED);
        verify(storyMapper, times(0)).putStoryToJson(any(List.class), eq("tkbbank"), eq("IOS"));
    }

    @Test
    @DisplayName("Одобрение истории администратором")
    public void approve_story_by_admin_test() throws IOException {
        Mockito.when(storyPresentationRepository.findById(any())).thenReturn(Optional.of(storyPresentation));

        service.approvedStory(storiesRequestDto.getBankId(), storiesRequestDto.getPlatform(), storyPresentation.getId());
        verify(storyMapper, times(1)).putStoryToJson(any(StoryPresentation.class), eq("tkbbank"), eq("IOS"));
    }

    @Test
    @DisplayName("Сохранение карточки в зависимости от роли - ADMIN")
    public void save_frame_by_role_admin_test() throws IOException {
        Mockito.when(keyCloak.getRoles()).thenReturn(new HashSet<KeyCloak.Roles>(Arrays.asList(KeyCloak.Roles.ADMIN)));
        Mockito.when(storyMapper.getStoryList(storiesRequestDto.getBankId(), storiesRequestDto.getPlatform()))
                .thenReturn(new ArrayList<>(Arrays.asList(storyPresentation)));
        Mockito.when(storyPresentationRepository.findById(15L)).thenReturn(Optional.of(storyPresentation));
        Mockito.when(storyPresentationFramesRepository.save(any())).thenReturn(secondFrame);



    }

}
