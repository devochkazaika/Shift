package ru.cft.shiftlab.contentmaker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.dto.StoryPatchDto;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationFramesRepository;
import ru.cft.shiftlab.contentmaker.repository.StoryPresentationRepository;
import ru.cft.shiftlab.contentmaker.service.implementation.HistoryService;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.Image.ImageContainer;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_TEST_DIRECTORY;

@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
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
    MultipartFile result;

    @Mock
    private DirProcess dirProcess;
    @Mock
    private HistoryService historyService;

    @Mock
    StoryMapper storyMapper;

    @Mock
    private KeyCloak keyCloak;

    @InjectMocks
    private JsonProcessorService service;

    @BeforeEach
    public void setUp() {
//        // Инициализация моков
        MockitoAnnotations.openMocks(this);
        StoryMapper realMapper = new StoryMapper(new DirProcess());
        storyMapper = Mockito.spy(realMapper);

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
        when(storyPresentationRepository.save(any())).thenReturn(storyPresentation);
        when(storyPresentationFramesRepository.save(any())).thenReturn(firstFrame);
        doCallRealMethod().when(modelMapper).map(any(), any(StoryPresentation.class));
        try {
            doCallRealMethod().when(dirProcess).createFolders(any());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path path = Paths.get(FILES_TEST_DIRECTORY + "sample.png");
        String name = "image.png";
        String originalFileName = "image.png";
        String contentType = "image/*";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        result = new MockMultipartFile(name,
                originalFileName, contentType, content);
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

    StoryPatchDto storyPatchDto = StoryPatchDto.builder()
            .previewTitleColor("asdas")
            .previewTitleColor("test")
            .build();


    @Test
    @DisplayName("Save story")
    void save_story() throws IOException {
        String storiesRequestJson = storyMapper.writeValueAsString(storyMapper.writeValueAsString(storiesRequestDto));

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
    @DisplayName("Save story by role = admin")
    public void save_story_by_role_ADMIN_test() throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Mockito.when(keyCloak.getRoles()).thenReturn(new HashSet<KeyCloak.Roles>(Arrays.asList(KeyCloak.Roles.ADMIN)));

        Method method = JsonProcessorService.class.getDeclaredMethod("saveByRoles",
                StoryPresentation.class);
        method.setAccessible(true); //делает метод публичным
        var story = (StoryPresentation) method.invoke(service, storyPresentation);

        Assertions.assertEquals(story.getApproved(), StoryPresentation.Status.APPROVED);
        verify(storyMapper, times(1)).putStoryToJson(any(List.class), eq("tkbbank"), eq("IOS"));
    }

    @Test
    @DisplayName("Save story entity by role = user")
    public void save_story_by_role_USER_test() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Mockito.when(keyCloak.getRoles()).thenReturn(new HashSet<KeyCloak.Roles>(Arrays.asList(KeyCloak.Roles.USER)));

        Method method = JsonProcessorService.class.getDeclaredMethod("saveByRoles",
                StoryPresentation.class);
        method.setAccessible(true); //делает метод публичным
        var story = (StoryPresentation) method.invoke(service, storyPresentation);

        Assertions.assertEquals(story.getApproved(), StoryPresentation.Status.NOTAPPROVED);
        verify(storyMapper, times(0)).putStoryToJson(any(List.class), eq("tkbbank"), eq("IOS"));
    }

    @Test
    @DisplayName("Accept story by admin")
    public void approve_story_by_admin_test() throws IOException {
        Mockito.when(storyPresentationRepository.findById(any())).thenReturn(Optional.of(storyPresentation));

        service.approveStory(storiesRequestDto.getBankId(), storiesRequestDto.getPlatform(), storyPresentation.getId());
        verify(storyMapper, times(1)).putStoryToJson(any(StoryPresentation.class), eq("tkbbank"), eq("IOS"));
    }

    @Test
    @DisplayName("Save story by role = admin")
    public void save_frame_by_role_admin_test() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Mockito.when(keyCloak.getRoles()).thenReturn(new HashSet<KeyCloak.Roles>(Arrays.asList(KeyCloak.Roles.ADMIN)));
        Mockito.when(storyMapper.getStoryList(storiesRequestDto.getBankId(), storiesRequestDto.getPlatform()))
                .thenReturn(new ArrayList<>(Arrays.asList(storyPresentation)));
        Mockito.when(storyPresentationRepository.findById(15L)).thenReturn(Optional.of(storyPresentation));
        Mockito.when(storyPresentationFramesRepository.save(any())).thenReturn(secondFrame);

        Method method = JsonProcessorService.class.getDeclaredMethod("saveToJsonByRoles",
                StoryPresentationFrames.class, MultipartFile.class, Long.class, String.class, String.class);
        method.setAccessible(true); //делает метод публичным

        var story = (StoryPresentation) method.invoke(service, secondFrame, result, 15L, storiesRequestDto.getBankId(), storiesRequestDto.getPlatform());

        verify(storyMapper, times(1)).putStoryToJson(any(List.class), eq("tkbbank"), eq("IOS"));
    }

    @Test
    @DisplayName("Get stories")
    public void get_frame_from_story_test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = JsonProcessorService.class.getDeclaredMethod("getFrameFromStory",
                StoryPresentation.class, String.class);
        method.setAccessible(true); //делает метод публичным

        StoryPresentationFrames storyPresentationFrames = (StoryPresentationFrames) method.invoke(service, storyPresentation, firstFrame.getId().toString());
        Assertions.assertEquals(storyPresentationFrames, firstFrame);
    }

    @Test
    @DisplayName("Get story with throw exception")
    public void get_frame_from_story_test_throw_exception() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = JsonProcessorService.class.getDeclaredMethod("getFrameFromStory",
                StoryPresentation.class, String.class);
        method.setAccessible(true); //делает метод публичным

        Assertions.assertThrows(InvocationTargetException.class,
                () -> method.invoke(service, storyPresentation, UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("Change story test")
    public void change_story_test() throws IOException {
        Mockito.when(multipartFileToImageConverter.parsePicture(any(ImageContainer.class), anyString(), any()))
                .thenReturn("норм путь");
        Mockito.when(storyMapper.getStoryList(any(), any())).thenReturn(Arrays.asList(storyPresentation));
        Mockito.doReturn(storyPresentation)
                .when(storyMapper)
                .getStoryModel(Mockito.anyList(), Mockito.eq(0L));

        service.changeStory(objectMapper.writeValueAsString(storyPatchDto), result,
                storiesRequestDto.getBankId(), storiesRequestDto.getPlatform(), 0L);

        verify(multipartFileToImageConverter, times(1)).parsePicture(any(), anyString(), any());
        verify(storyMapper, times(1)).readerForUpdating(any());
        verify(storyMapper, times(1)).putStoryToJson(any(List.class), any(), any());
    }

    @Test
    @DisplayName("Change card story")
    public void change_frame_story_test() throws IOException {
        Mockito.when(storyMapper.getStoryList(any(), any())).thenReturn(new ArrayList<>(Arrays.asList(storyPresentation)));
        Mockito.doReturn(storyPresentation)
                .when(storyMapper).getStoryModel(any(), any());

        Mockito.when(multipartFileToImageConverter.parsePicture(any(ImageContainer.class), anyString(), any()))
                .thenReturn("норм путь");
        Mockito.when(storyMapper.writeValueAsString(storyPresentation)).thenCallRealMethod();

        ObjectMapper realMapper = new ObjectMapper();
        ObjectMapper spyMapper = Mockito.spy(realMapper);

        service.changeFrameStory(spyMapper.writeValueAsString(storyFramesDto),
                storiesRequestDto.getBankId(), storiesRequestDto.getPlatform(),
                15L, firstFrame.getId().toString(), result);

        verify(multipartFileToImageConverter, times(1)).parsePicture(any(), any(), any(), any());
        verify(storyMapper, times(1)).readerForUpdating(any());
        verify(storyMapper, times(1)).putStoryToJson(anyList(), any(), any());
    }

    @Test
    @DisplayName("Delete story")
    public void delete_story_test() throws Throwable {
        Mockito.when(storyPresentationRepository.findById(15L)).thenReturn(Optional.of(storyPresentation));
        Mockito.when(storyMapper.getStoryList(any(), any())).thenReturn(new ArrayList<>(Arrays.asList(storyPresentation)));
        doCallRealMethod().when(storyMapper).putStoryToJson(storyPresentation, storyPresentation.getBankId(), storyPresentation.getPlatform());

        service.deleteService(storyPresentation.getBankId(), storyPresentation.getPlatform(), storyPresentation.getId());

    }
}
