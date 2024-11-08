//package ru.cft.shiftlab.contentmaker.service;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringEscapeUtils;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
//import ru.cft.shiftlab.contentmaker.dto.StoryDto;
//import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
//import ru.cft.shiftlab.contentmaker.dto.StoryPatchDto;
//import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
//import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;
//import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
//import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
//import ru.cft.shiftlab.contentmaker.util.DirProcess;
//import ru.cft.shiftlab.contentmaker.util.Image.ImageContainer;
//import ru.cft.shiftlab.contentmaker.util.Image.ImageNameGenerator;
//import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
//import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;
//import ru.cft.shiftlab.contentmaker.util.StoryMapper;
//import ru.cft.shiftlab.contentmaker.util.keycloak.KeyCloak;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.*;
//import java.util.stream.Stream;
//
//import static org.mockito.ArgumentMatchers.*;
//import static ru.cft.shiftlab.contentmaker.util.Constants.*;
//
//@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
//public class JsonProcessorServiceTest {
//
//    @InjectMocks
//    private JsonProcessorService service;
//
//    @Mock
//    private MultipartFileToImageConverter multipartFileToImageConverter;
//
//    @Mock
//    private DtoToEntityConverter dtoToEntityConverter;
//
//    @Mock
//    private DirProcess dirProcess;
//
//    @Mock
//    private StoryMapper storyMapper;
//
//    @Mock
//    private KeyCloak keyCloak;
//    @Mock
//    private BannerRepository bannerRepository;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    private final ImageNameGenerator imageNameGenerator = new ImageNameGenerator();
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        MockitoAnnotations.openMocks(this);
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        dirProcess.createFolders(FILES_SAVE_DIRECTORY);  // Initialize folders
//    }
//
//    @AfterEach
//    public void deleteFolderBackend() throws IOException {
//        dirProcess.deleteFolders(String.valueOf(Paths.get("backend")));  // Clean up folders
//    }
//
//
//
//    @Test
//    void should_save_files() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        StoryFramesDto storyFramesDto = new StoryFramesDto(
//                "mainText",
//                "Text card",
//                "FF0000",
//                "NONE",
//                "",
//                "",
//                "",
//                "",
//                "EMPTY");
//        StoryDto storyDto = new StoryDto(
//                "MainTextPreview",
//                "FF0000",
//                "EMPTY",
//                new ArrayList<>(Collections.singletonList(storyFramesDto)));
//        StoriesRequestDto storiesRequestDto = new StoriesRequestDto(
//                "tkbbank",
//                "IOS",
//                new ArrayList<>(Collections.singletonList(storyDto)));
//
//        File img =  new File(
//                FILES_TEST_DIRECTORY,
//                "sample.png");
//
//        FileInputStream input = new FileInputStream(img);
//        Assertions.assertNotNull(input);
//        MultipartFile multipartFile = new MockMultipartFile("fileItem",
//                img.getName(), "image/png", IOUtils.toByteArray(input));
//        var arrImg = new MultipartFile[1];
//        arrImg[0] = multipartFile;
//
//
//        String picturesDirectory = FILES_SAVE_DIRECTORY
//                + storiesRequestDto.getBankId() + "/" + storiesRequestDto.getPlatform();
//        String fileName = "story_tkbbank_iOS.json";
//        String jsonDirectory = FILES_SAVE_DIRECTORY;
//        String previewUrl = picturesDirectory + "/preview1.png";
//
//        List<StoryPresentation> storyPresentationList = new ArrayList<>();
//
//        storyPresentationList.add(dtoToEntityConverter.fromStoryDtoToStoryPresentation(
//                "tkbbank",
//                storyDto,
//                previewUrl));
//
//        Map<String, List<StoryPresentation>> presentationList = new HashMap<>();
//        presentationList.put("stories", storyPresentationList);
//
//        File jsonFile = Paths.get(jsonDirectory + fileName).toFile();
//
//        int countFiles = getCountFilesInDir(storiesRequestDto);
//
//        Mockito.when(storyMapper.readValue(anyString(), (Class<Object>) any())).thenReturn(storiesRequestDto);
//
//        service.saveFiles(objectMapper.writeValueAsString(storiesRequestDto),
//                multipartFile,
//                arrImg
//        );
//        Assertions.assertEquals(countFiles + 2, getCountFilesInDir(storiesRequestDto));
//
//        StringBuilder jsonStr = new StringBuilder();
//        Files.readAllLines(jsonFile.toPath()).forEach(jsonStr::append);
//        Map<String, List<StoryPresentation>> map = objectMapper.readValue(
//                jsonStr.toString(),
//                new TypeReference<Map<String, List<StoryPresentation>>>() {}
//        );
//
//        var storyFromJson = map.get(STORIES).get(0);
//        storyFromJson.setPreviewUrl(null);
//        for(StoryPresentationFrames card : storyFromJson.getStoryPresentationFrames()){
//            card.setPictureUrl(null);
//        }
//
//        try{
//            Assertions.assertAll(
//                    () -> Assertions.assertEquals(countFiles + 2, getCountFilesInDir(storiesRequestDto), "Images should be created"),
//                    () -> Assertions.assertTrue(Files.exists(jsonFile.toPath()), "File should exist"),
//                    () -> Assertions.assertEquals(storyFromJson.getPreviewTitle(), storyDto.getPreviewTitle()),
//                    () -> Assertions.assertEquals(storyFromJson.getPreviewGradient(), storyDto.getPreviewGradient()),
//                    () -> Assertions.assertEquals(storyFromJson.getPreviewTitleColor(), storyDto.getPreviewTitleColor()),
//                    () -> Assertions.assertEquals(storyFromJson.getStoryPresentationFrames().get(0).getText(),
//                            storyDto.getStoryFramesDtos().get(0).getText()),
//                    () -> Assertions.assertEquals(storyFromJson.getStoryPresentationFrames().get(0).getGradient(),
//                            storyDto.getStoryFramesDtos().get(0).getGradient()),
//                    () -> Assertions.assertEquals(storyFromJson.getStoryPresentationFrames().get(0).getButtonUrl(),
//                            storyDto.getStoryFramesDtos().get(0).getButtonUrl()),
//                    () -> Assertions.assertEquals(storyFromJson.getStoryPresentationFrames().get(0).getTitle(),
//                            storyDto.getStoryFramesDtos().get(0).getTitle())
//            );
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        FileUtils.deleteQuietly(jsonFile);
//        FileUtils.deleteDirectory(new File(picturesDirectory));
//    }
//
//    @Test
//    void should_save_filesWEB() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        StoryFramesDto storyFramesDto = new StoryFramesDto(
//                "mainText",
//                "Text card",
//                "FF0000",
//                "NONE",
//                "",
//                "",
//                "",
//                "",
//                "EMPTY");
//        StoryDto storyDto = new StoryDto(
//                "MainTextPreview",
//                "FF0000",
//                "EMPTY",
//                new ArrayList<>(Collections.singletonList(storyFramesDto)));
//        StoriesRequestDto storiesRequestDto = new StoriesRequestDto(
//                "tkbbank",
//                "WEB",
//                new ArrayList<>(Collections.singletonList(storyDto)));
//        File img =  new File(
//                FILES_TEST_DIRECTORY,
//                "sample.png");
//
//        FileInputStream input = new FileInputStream(img);
//        Assertions.assertNotNull(input);
//        MultipartFile multipartFile = new MockMultipartFile("fileItem",
//                img.getName(), "image/png", IOUtils.toByteArray(input));
//        var arrImg = new MultipartFile[1];
//        arrImg[0] = multipartFile;
//
//        String picturesDirectory = FILES_SAVE_DIRECTORY
//                + storiesRequestDto.getBankId() + "/" + storiesRequestDto.getPlatformType();
//        String fileName = "story_tkbbank_web.json";
//        String jsonDirectory = FILES_SAVE_DIRECTORY;
//        String previewUrl = picturesDirectory + "/preview1.png";
//
//        List<StoryPresentation> storyPresentationList = new ArrayList<>();
//
//        storyPresentationList.add(dtoToEntityConverter.fromStoryDtoToStoryPresentation(
//                "tkbbank",
//                storyDto,
//                previewUrl));
//
//        Map<String, List<StoryPresentation>> presentationList = new HashMap<>();
//        presentationList.put("WEB", storyPresentationList);
//
//        File jsonFile = Paths.get(jsonDirectory + fileName).toFile();
//
//        int countFiles = getCountFilesInDir(storiesRequestDto);
//        service.saveFiles("\"" + StringEscapeUtils.escapeJson(objectMapper.writeValueAsString(storiesRequestDto)) + "\"",
//                multipartFile,
//                arrImg
//        );
//        Assertions.assertEquals(countFiles + 2, getCountFilesInDir(storiesRequestDto));
//
//        StringBuilder jsonStr = new StringBuilder();
//
//        Files.readAllLines(jsonFile.toPath()).forEach(jsonStr::append);
//        Map<String, List<StoryPresentation>> map = objectMapper.readValue(
//                jsonStr.toString(),
//                new TypeReference<Map<String, List<StoryPresentation>>>() {}
//        );
//
//        var storyFromJson = map.get(STORIES).get(0);
//        storyFromJson.setPreviewUrl(null);
//        for(StoryPresentationFrames card : storyFromJson.getStoryPresentationFrames()){
//            card.setPictureUrl(null);
//        }
//        FileUtils.deleteQuietly(jsonFile);
//        FileUtils.deleteDirectory(new File(picturesDirectory));
//    }
//
//    @Test
//    public void add_frame_JSON_test() throws IOException {
//        String bankId = "test_bank";
//        String platform = "WEB";
//        Long id = 0L;
//        StoryFramesDto storyFramesDto = StoryFramesDto.builder()
//                .title("Sample Title")
//                .text("Sample text for the story.")
//                .textColor("FF0000")
//                .visibleButtonOrNone("BUTTON")
//                .buttonText("Click Here")
//                .buttonTextColor("FFFFFF")
//                .buttonBackgroundColor("0000FF")
//                .buttonUrl("https://example.com")
//                .gradient("EMPTY")
//                .build();
//
//        String storyString = objectMapper.writeValueAsString(storyFramesDto);
//        dirProcess.createFolders(FILES_SAVE_DIRECTORY+bankId+"/WEB");
//        copyFile(FILES_TEST_DIRECTORY+"story_test_bank_web.json",
//                FILES_SAVE_DIRECTORY + "story_test_bank_web.json");
//
//        File file = new File(FILES_TEST_DIRECTORY+"sample.png");
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file",
//                file.getName(), "text/plain", IOUtils.toByteArray(input));
//
//        //Мок для картинок
//        ImageContainer imageContainer = Mockito.mock(ImageContainer.class);
//        MultipartFileToImageConverter multipartFileToImageConverter = Mockito.mock(MultipartFileToImageConverter.class);
//
//        Mockito.lenient().when(multipartFileToImageConverter.parsePicture(
//                any(ImageContainer.class),
//                anyString(),
//                anyLong(),
//                any(UUID.class)
//        )).thenReturn("path");
//
//
//        service.addFrame(storyString, multipartFile, bankId, platform, id);
//
//        var mapStory = storyToMap(FILES_SAVE_DIRECTORY + "story_test_bank_web.json");
//        var frame = mapStory.get("stories").get(0).getStoryPresentationFrames().get(mapStory.get("stories").get(0).getStoryPresentationFrames().size()-1);
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(frame.getTitle(), storyFramesDto.getTitle()),
//                () -> Assertions.assertEquals(frame.getText(), storyFramesDto.getText()),
//                () -> Assertions.assertEquals(frame.getTextColor(), storyFramesDto.getTextColor()),
//                () -> Assertions.assertEquals(frame.getVisibleButtonOrNone(), storyFramesDto.getVisibleButtonOrNone()),
//                () -> Assertions.assertEquals(frame.getButtonText(), storyFramesDto.getButtonText()),
//                () -> Assertions.assertEquals(frame.getButtonTextColor(), storyFramesDto.getButtonTextColor()),
//                () -> Assertions.assertEquals(frame.getButtonBackgroundColor(), storyFramesDto.getButtonBackgroundColor()),
//                () -> Assertions.assertEquals(frame.getButtonUrl(), storyFramesDto.getButtonUrl()),
//                () -> Assertions.assertEquals(frame.getGradient(), storyFramesDto.getGradient())
//        );
//    }
//
//    @Test
//    public void add_frame_FILE_test() throws IOException {
//        String bankId = "tkbbank";
//        String platform = "WEB";
//        Long id = 0L;
//        StoryFramesDto storyFramesDto = StoryFramesDto.builder()
//                .title("Sample Title")
//                .text("Sample text for the story.")
//                .textColor("FF0000")
//                .visibleButtonOrNone("BUTTON")
//                .buttonText("Click Here")
//                .buttonTextColor("FFFFFF")
//                .buttonBackgroundColor("0000FF")
//                .buttonUrl("https://example.com")
//                .gradient("EMPTY")
//                .build();
//
//        String storyString = objectMapper.writeValueAsString(storyFramesDto);
//        dirProcess.createFolders(FILES_SAVE_DIRECTORY+bankId+"/WEB");
//        copyFile(FILES_TEST_DIRECTORY+"story_tkbbank_web.json",
//                FILES_SAVE_DIRECTORY + "story_tkbbank_web.json");
//
//        File file = new File(FILES_TEST_DIRECTORY+"sample.png");
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file",
//                file.getName(), "text/plain", IOUtils.toByteArray(input));
//
//        service.addFrame(storyString, multipartFile, bankId, platform, id);
//
//        var mapStory = storyToMap(FILES_SAVE_DIRECTORY + "story_tkbbank_web.json");
//        var frame = objectMapper.readTree(new File(FILES_SAVE_DIRECTORY + "story_tkbbank_web.json"));
//        System.out.println(frame);
//
////        File[] files = new File(FILES_SAVE_DIRECTORY+bankId+"/WEB").listFiles();
////        Set<String> pict = Arrays.stream(files).map(x -> x.getName()).collect(Collectors.toSet());
////        System.out.println(pict);
////        System.out.println(frame.getPictureUrl());
////        Assertions.assertAll(
////                () -> Assertions.assertEquals(pict.contains(frame.getPictureUrl().split("/WEB/")[1]), true)
////        );
//    }
//
//    private Map<String, List<StoryPresentation>> storyToMap(String path) throws IOException {
//        StringBuilder jsonStr = new StringBuilder();
//        Files.readAllLines(new File(path).toPath()).forEach(jsonStr::append);
//        Map<String, List<StoryPresentation>> map = objectMapper.readValue(
//                jsonStr.toString(),
//                new TypeReference<Map<String, List<StoryPresentation>>>() {}
//        );
//        return map;
//    }
//
//    private int getCountFilesInDir(StoriesRequestDto storiesRequestDto) {
//        File f = new File(
//                FILES_SAVE_DIRECTORY +
//                        storiesRequestDto.getBankId() + "/" + storiesRequestDto.getPlatformType() + "/");
//        File[] files = f.listFiles();
//        if(files == null){
//            return 0;
//        }
//        return files.length;
//    }
//
//
//
//    /**
//     * Удаление stories
//     */
//    @Test
//    void deleteFilesTest() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
//        Method method = JsonProcessorService.class.getDeclaredMethod("deleteFilesStories", String.class, String.class, String.class);
//
//        method.setAccessible(true);
//        String bankId = "TestBank";
//        String platform = "WEB";
//        String saveDirectory = FILES_SAVE_DIRECTORY + bankId + "/" + platform;
//        dirProcess.createFolders(saveDirectory);
//        File img =  new File(
//                FILES_TEST_DIRECTORY,
//                "sample.png");
//        copyFile(img.getAbsolutePath(), saveDirectory + "/" + "0_0.png");
//
//        // Проверяем, что файл скопирован
//        Assertions.assertTrue(new File(saveDirectory).exists());
//
//        FileInputStream input = new FileInputStream(img);
//        Assertions.assertNotNull(input);
//
//        dirProcess.createFolders(saveDirectory);
//
//
//        MultipartFile multipartFile = new MockMultipartFile("fileItem",
//                img.getName(), "image/png", IOUtils.toByteArray(input));
//
//        method.invoke(service, bankId, platform, "0");
//
//
//        method.invoke(service, bankId, platform, "0");
//        File file = new File(saveDirectory);
//        File[] files = file.listFiles();
//        Assertions.assertEquals(0, files.length);
//    }
//    @Test
//    void deleteJsonTest() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
//        Method method = JsonProcessorService.class.getDeclaredMethod("deleteJsonStories", String.class, String.class, String.class);
//        File jsonFile = new File(FILES_TEST_DIRECTORY + "/story_tkbbank_web.json");
//        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_test_web.json");
//        File json = new File(FILES_SAVE_DIRECTORY + "/story_test_web.json");
//
//        method.setAccessible(true);
//        method.invoke(service, "test", "WEB", "0");
//        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_tkbbank_web.json");
//        json = new File(FILES_SAVE_DIRECTORY + "/story_tkbbank_web.json");
//
//        method.invoke(service, "tkbbank", "WEB", "0");
//
//        StringBuilder jsonStr = new StringBuilder();
//        Files.readAllLines(json.toPath()).forEach(jsonStr::append);
//        Map<String, List<StoryPresentation>> map = objectMapper.readValue(
//                jsonStr.toString(),
//                new TypeReference<Map<String, List<StoryPresentation>>>() {}
//        );
//        Set<Long> set = new HashSet<>();
//        Stream.of(map.get("stories"))
//                .forEach(x -> set.add(x.get(0).getId()));
//        Assertions.assertTrue(!set.contains(0L));
//    }
//    public static void copyFile(String sourcePathStr, String destinationPathStr) {
//        // Преобразуем строки в Path объекты
//        Path sourcePath = Paths.get(sourcePathStr);
//        Path destinationPath = Paths.get(destinationPathStr);
//
//        try {
//            // Копируем файл из sourcePath в destinationPath
//            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void changeStory_method_test() throws Exception{
//        StoryPatchDto storyPatchDto = StoryPatchDto.builder()
////                .previewTitle("test_previewTitle")
//                .previewGradient("gradient_test")
//                .previewTitleColor("color_test")
//                .build();
//        String bankId = "test_bank";
//        String platform = "WEB";
//        String json = objectMapper.writeValueAsString(storyPatchDto);
//        copyFile(FILES_TEST_DIRECTORY+"story_test_bank_web.json", FILES_SAVE_DIRECTORY+"story_test_bank_web.json");
////        File file = new File(FILES_TEST_DIRECTORY+"story_test_bank_web.json");
//        service.changeStory(json, null, bankId, platform, 0L);
//        Map<String, List<StoryPresentation>> resultMap = objectMapper.readValue(new File(FILES_SAVE_DIRECTORY, "story_test_bank_web.json"),
//                new TypeReference<>(){});
//        StoryPresentation storyPresentation = resultMap.get("stories").get(0);
//        Assertions.assertAll(
////                () -> Assertions.assertEquals(storyPresentation.getPreviewTitle(), storyPatchDto.getPreviewTitle()),
//                () -> Assertions.assertEquals(storyPresentation.getPreviewGradient(), storyPatchDto.getPreviewGradient()),
//                () -> Assertions.assertEquals(storyPresentation.getPreviewTitleColor(), storyPatchDto.getPreviewTitleColor())
//        );
//    }
//    @Test
//    public void changeStoryFrame__method_test() throws Exception{
//        StoryFramesDto storyPatchDto = StoryFramesDto.builder()
//                .text("Sample text for the story.")
//                .textColor("FF0000")
//                .visibleButtonOrNone("BUTTON")
//                .buttonText("Click Here")
//                .buttonTextColor("FFFFFF")
//                .buttonBackgroundColor("0000FF")
//                .buttonUrl("https://example.com")
//                .build();
//        String bankId = "test_bank";
//        String platform = "WEB";
//        String json = objectMapper.writeValueAsString(storyPatchDto);
//        copyFile(FILES_TEST_DIRECTORY+"story_test_bank_web.json", FILES_SAVE_DIRECTORY+"story_test_bank_web.json");
//        service.changeFrameStory(json, bankId, platform,
//                0L,
//                "dc430619-a772-4f80-81e5-bc66218ddd0c",
//                null);
//        Map<String, List<StoryPresentation>> resultMap = objectMapper.readValue(new File(FILES_SAVE_DIRECTORY, "story_test_bank_web.json"),
//                new TypeReference<>(){});
//        StoryPresentationFrames storyPresentation = resultMap.get("stories").get(0).getStoryPresentationFrames().get(0);
//        Assertions.assertAll(
//                () -> Assertions.assertEquals("mainText", storyPresentation.getTitle()),
//                () -> Assertions.assertEquals(storyPatchDto.getText(), storyPresentation.getText()),
//                () -> Assertions.assertEquals(storyPatchDto.getTextColor(), storyPresentation.getTextColor()),
//                () -> Assertions.assertEquals(storyPatchDto.getButtonText(), storyPresentation.getButtonText()),
//                () -> Assertions.assertEquals(storyPatchDto.getButtonUrl(), storyPresentation.getButtonUrl())
//        );
//    }
//    @Test
//    public void delete_JsonFrame_test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
//        Method method = JsonProcessorService.class.getDeclaredMethod("deleteJsonFrame",
//                String.class, String.class, String.class, String.class);
//        File jsonFile = new File(FILES_TEST_DIRECTORY + "/story_tkbbank_web.json");
//        File storyDir = new File(FILES_SAVE_DIRECTORY + "/story_test_web.json");
//        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_test_web.json");
//
//
//        method.setAccessible(true);
//        method.invoke(service, "test", "WEB", "1", "edfb9afb-d795-4979-b7cf-b60923d3f266");
//        ObjectNode node = (ObjectNode) objectMapper.readTree(new File(FILES_SAVE_DIRECTORY + "/story_test_web.json"));
//        ArrayNode arrayNode = (ArrayNode) node.get("stories").get(1).get("storyFrames");
//        Assertions.assertEquals(arrayNode.size(), 1);
//        storyDir.delete();
//    }
//    @Test
//    public void delete_FileFrame_test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
//        Method method = JsonProcessorService.class.getDeclaredMethod("deleteFileFrame",
//                String.class, String.class, String.class, UUID.class);
//        File jsonFile = new File(FILES_TEST_DIRECTORY + "/sample.png");
//        dirProcess.createFolders(FILES_SAVE_DIRECTORY + "test/WEB");
//        File storyDir = new File(FILES_SAVE_DIRECTORY + "test");
//        UUID uuid = UUID.randomUUID();
////        UUID uuid2 = UUID.randomUUID();
//        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "test/WEB/1_"+0+".png");
//        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "test/WEB/1_"+uuid+".png");
//
//        method.setAccessible(true);
//        method.invoke(service, "test", "WEB", "1", uuid);
//        File[] directory = new File(FILES_SAVE_DIRECTORY+"test"+"/WEB").listFiles();
//        Assertions.assertEquals(directory.length, 1);
//        FileUtils.deleteDirectory(storyDir);
//    }
//
////    Надо дописать для всего удаления
//    @Test
//    public void delete_Frame_test() throws Throwable {
//        String bankId = "test";
//        String platform = "WEB";
//        String id = "0";
//        String frameId = "72f250d4-2fea-4f00-a0b2-3259555ceb81";
//
//        File jsonFile = new File(FILES_TEST_DIRECTORY + "/story_tkbbank_web.json");
//        File storyDir = new File(FILES_SAVE_DIRECTORY + "/story_test_web.json");
//        //копирую json file
//        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_test_web.json");
//
//        //копирую изображения
//        File file = new File(FILES_TEST_DIRECTORY + "/sample.png");
//        dirProcess.createFolders(FILES_SAVE_DIRECTORY + "test/WEB");
//        UUID uuid = UUID.fromString("72f250d4-2fea-4f00-a0b2-3259555ceb81");
//
//        copyFile(file.getAbsolutePath(), FILES_SAVE_DIRECTORY + "test/WEB/0_"+0+".png");
//        copyFile(file.getAbsolutePath(), FILES_SAVE_DIRECTORY + "test/WEB/0_"+uuid+".png");
//
//        service.deleteStoryFrame(bankId, platform, id, frameId);
//        ObjectNode node = (ObjectNode) objectMapper.readTree(new File(FILES_SAVE_DIRECTORY + "/story_test_web.json"));
//        ArrayNode arrayNode = (ArrayNode) node.get("stories").get(0).get("storyFrames");
//        Assertions.assertEquals(arrayNode.size(), 1);
//
//        File[] directoryPict = new File(FILES_SAVE_DIRECTORY+"test/WEB/").listFiles();
//        Assertions.assertEquals(directoryPict.length, 1);
//
//        dirProcess.deleteFolders(FILES_SAVE_DIRECTORY+"test/");
//        storyDir.delete();
//    }
//
//    /**
//     * Тест для ловли ошибки в JsonDelete
//     * @throws Throwable
//     */
//    @Test
//    public void deleteStoryFrame_IndexOutOfBoundsException_test() throws Throwable {
//        String bankId = "test";
//        String platform = "WEB";
//        String id = "0";
//        String frameId = "123";
//
//        File jsonFile = new File(FILES_TEST_DIRECTORY + "/story_tkbbank_web.json");
//        File storyDir = new File(FILES_SAVE_DIRECTORY + "/story_test_web.json");
//        //копирую json file
//        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_test_web.json");
//
//        //копирую изображения
//        File file = new File(FILES_TEST_DIRECTORY + "/sample.png");
//        dirProcess.createFolders(FILES_SAVE_DIRECTORY + "test/WEB");
//        UUID uuid = UUID.fromString("72f250d4-2fea-4f00-a0b2-3259555ceb81");
//
//        copyFile(file.getAbsolutePath(), FILES_SAVE_DIRECTORY + "test/WEB/0_"+0+".png");
//        copyFile(file.getAbsolutePath(), FILES_SAVE_DIRECTORY + "test/WEB/0_"+uuid+".png");
//
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                ()->service.deleteStoryFrame(bankId, platform, id, frameId)
//        );
//        dirProcess.deleteFolders(FILES_SAVE_DIRECTORY+"test/");
//
//    }
//
////    @Test
////    public void swap_frames_from_stories() throws IOException {
////        String bankId = "test_bank";
////        String platform = "WEB";
////        Long id = 0L;
////        String first = "dc430619-a772-4f80-81e5-bc66218ddd0c";
////        String second = "7b0c950b-c43b-4461-8aa8-295c308990c8";
////
////        File jsonFile = new File(FILES_TEST_DIRECTORY + "/story_test_bank_web.json");
////        File storyDir = new File(FILES_SAVE_DIRECTORY + "/story_test_bank_web.json");
////        //копирую json file
////        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_test_bank_web.json");
////
////        service.swapFrames(id, bankId, platform, first, second);
////        ObjectNode node = (ObjectNode) objectMapper.readTree(new File(FILES_SAVE_DIRECTORY + "/story_test_bank_web.json"));
////        ArrayNode arrayNode =
////                (ArrayNode) node
////                .get("stories")
////                .get(0)
////                .get("storyFrames");
////        Assertions.assertAll(
////                () -> Assertions.assertEquals(arrayNode.get(0).get("id").toString(), "\"".concat(second).concat("\"")),
////                () -> Assertions.assertEquals(arrayNode.get(1).get("id").toString(), "\"".concat(first).concat("\""))
////        );
////
////    }
//
//}