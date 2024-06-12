package ru.cft.shiftlab.contentmaker.Stories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.Image.ImageNameGenerator;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;
import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_TEST_DIRECTORY;

@ExtendWith(MockitoExtension.class)
public class DeleteStoriesServiceTest {
    ObjectMapper objectMapper = new ObjectMapper();
    private final MultipartFileToImageConverter multipartFileToImageConverter = new MultipartFileToImageConverter(new ImageNameGenerator());
    private final DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());
    private final DirProcess dirProcess = new DirProcess();

    /**
     * Тест для метода Закрытого deleteFilesStories
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void deleteStoriesFilesTest() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
        Method method = JsonProcessorService.class.getDeclaredMethod("deleteFilesStories", String.class, String.class, String.class);

        method.setAccessible(true);
        String bankId = "TestBank";
        String platform = "WEB";
        String saveDirectory = FILES_SAVE_DIRECTORY + bankId + "/" + platform;
        dirProcess.createFolders(saveDirectory);
        File img =  new File(
                FILES_TEST_DIRECTORY,
                "sample.png");
        copyFile(img.getAbsolutePath(), saveDirectory + "/" + "0_0.png");

        // Проверяем, что файл скопирован
        Assertions.assertTrue(new File(saveDirectory).exists());

        FileInputStream input = new FileInputStream(img);
        Assertions.assertNotNull(input);

        dirProcess.createFolders(saveDirectory);


        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                img.getName(), "image/png", IOUtils.toByteArray(input));

        JsonProcessorService service = new JsonProcessorService(multipartFileToImageConverter,
                dtoToEntityConverter,
                dirProcess
                );


        method.invoke(service, bankId, platform, "0");
        File file = new File(saveDirectory);
        File[] files = file.listFiles();
        Assertions.assertEquals(0, files.length);
    }

    /**
     * Тест для метода Закрытого deleteJsonStories
     * @throws NoSuchMethodException
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    void deleteStoriesJsonTest() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
        Method method = JsonProcessorService.class.getDeclaredMethod("deleteJsonStories", String.class, String.class, String.class);
        File jsonFile = new File(FILES_TEST_DIRECTORY + "/story_tkbbank_web.json");
        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_tkbbank_web.json");
        File json = new File(FILES_SAVE_DIRECTORY+"/story_tkbbank_web.json");
        JsonProcessorService service = new JsonProcessorService(multipartFileToImageConverter,
                dtoToEntityConverter,
                dirProcess);

        method.invoke(service, "tkbbank", "WEB", "0");

        StringBuilder jsonStr = new StringBuilder();
        Files.readAllLines(json.toPath()).forEach(jsonStr::append);
        Map<String, List<StoryPresentation>> map = objectMapper.readValue(
                jsonStr.toString(),
                new TypeReference<Map<String, List<StoryPresentation>>>() {}
        );
        Set<Long> set = new HashSet<>();
        Stream.of(map.get("stories"))
                .forEach(x -> set.add(x.get(0).getId()));
        Assertions.assertTrue(!set.contains(0L));
    }
    public static File copyFile(String sourcePathStr, String destinationPathStr) {
        // Преобразуем строки в Path объекты
        Path sourcePath = Paths.get(sourcePathStr);
        Path destinationPath = Paths.get(destinationPathStr);

        try {
            // Копируем файл из sourcePath в destinationPath
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(String.valueOf(destinationPath));
    }

    /**
     * Тест для проверки закрытого метода deleteJsonFrame
     */
    @Test
    public void deleteJsonFrame() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Method method = JsonProcessorService.class.getDeclaredMethod("deleteJsonFrame",
                String.class, String.class, String.class, String.class);
        method.setAccessible(true);
        File jsonFile = new File(FILES_TEST_DIRECTORY + "/story_tkbbank_web.json");
        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_test_web.json");
        JsonProcessorService service = new JsonProcessorService(multipartFileToImageConverter,
                dtoToEntityConverter,
                dirProcess);
        method.invoke(service, "test", "WEB", "0", "0");
        method.invoke(service, "test", "WEB", "1", "0");
        File json = new File(FILES_SAVE_DIRECTORY+"/story_test_web.json");
        StringBuilder jsonStr = new StringBuilder();
        Files.readAllLines(json.toPath()).forEach(jsonStr::append);
        Map<String, List<StoryPresentation>> map = objectMapper.readValue(
                jsonStr.toString(),
                new TypeReference<Map<String, List<StoryPresentation>>>() {}
        );
        map.get("stories").stream()
                .forEach(i -> Assertions.assertEquals(0, i.getStoryPresentationFrames().size()));
        json.delete();
    }
    /**
     * Тест для проверки закрытого метода deleteJsonFrame
     * Тест для удаления только одного frame и сохранении предыдущих
     */
    @Test
    public void deleteJson_OnlyOne_Frame() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Method method = JsonProcessorService.class.getDeclaredMethod("deleteJsonFrame",
                String.class, String.class, String.class, String.class);
        method.setAccessible(true);
        File jsonFile = new File(FILES_TEST_DIRECTORY + "/testDeleteJson.json");
        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_test_web.json");
        JsonProcessorService service = new JsonProcessorService(multipartFileToImageConverter,
                dtoToEntityConverter,
                dirProcess);
        method.invoke(service, "test", "WEB", "0", "0");
        File json = new File(FILES_SAVE_DIRECTORY+"/story_test_web.json");
        StringBuilder jsonStr = new StringBuilder();
        Files.readAllLines(json.toPath()).forEach(jsonStr::append);
        Map<String, List<StoryPresentation>> map = objectMapper.readValue(
                jsonStr.toString(),
                new TypeReference<Map<String, List<StoryPresentation>>>() {}
        );
        map.get("stories").stream()
                .forEach(i -> Assertions.assertEquals(1, i.getStoryPresentationFrames().size()));
        json.delete();
    }
    /**
     * Тест для проверки закрытого метода deleteFileFrame
     * Тест для удаления файла для frame и сохранении предыдущих
     */
    @Test
    public void deleteFileFrame() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Method method = JsonProcessorService.class.getDeclaredMethod("deleteFileFrame",
                String.class, String.class, String.class, String.class);
        method.setAccessible(true);
        File file = new File(FILES_TEST_DIRECTORY + "/sample.png");
        File directory = new File(FILES_SAVE_DIRECTORY + "/test" + "/WEB");
        directory.mkdirs();
        copyFile(file.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/test" + "/WEB" + "/0_1.png");
        JsonProcessorService service = new JsonProcessorService(multipartFileToImageConverter,
                dtoToEntityConverter,
                dirProcess);
        method.invoke(service, "test", "WEB", "0", "1");
        File files = new File(FILES_SAVE_DIRECTORY + "/test" + "/WEB");
        List<File> fileList = List.of(files.listFiles());
        Assertions.assertEquals(0, fileList.size());
        directory.delete();
    }
    /**
     * Тест для проверки закрытого метода deleteFileFrame
     * Тест для удаления только одного файла и сохранении предыдущийх для frame и сохранении предыдущих
     */
    @Test
    public void delete_OnlyOne_FileFrame() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Method method = JsonProcessorService.class.getDeclaredMethod("deleteFileFrame",
                String.class, String.class, String.class, String.class);
        method.setAccessible(true);
        File file = new File(FILES_TEST_DIRECTORY + "/sample.png");
        File directory = new File(FILES_SAVE_DIRECTORY + "/test" + "/WEB");
        directory.mkdirs();
        List<File> listFiles = List.of(
                copyFile(file.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/test" + "/WEB" + "/0_1.png"),
                copyFile(file.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/test" + "/WEB" + "/0_2.png"),
                copyFile(file.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/test" + "/WEB" + "/0_3.png"),
                copyFile(file.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/test" + "/WEB" + "/0_4.png")
        );
        JsonProcessorService service = new JsonProcessorService(multipartFileToImageConverter,
                dtoToEntityConverter,
                dirProcess);
        method.invoke(service, "test", "WEB", "0", "2");
        File files = new File(FILES_SAVE_DIRECTORY + "/test" + "/WEB");
        List<File> fileList = List.of(files.listFiles());
        Assertions.assertEquals(3, fileList.size());
        listFiles.forEach(x -> x.delete());
        directory.delete();
    }
}