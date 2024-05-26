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
import ru.cft.shiftlab.contentmaker.util.DtoToEntityConverter;
import ru.cft.shiftlab.contentmaker.util.FileNameCreator;
import ru.cft.shiftlab.contentmaker.util.ImageNameGenerator;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;

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
public class DeleteRequestTest {
    ObjectMapper objectMapper = new ObjectMapper();
    private final MultipartFileToImageConverter multipartFileToImageConverter = new MultipartFileToImageConverter(new ImageNameGenerator());
    private final DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());
    @Test
    void deleteFilesTest() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
        Method method = JsonProcessorService.class.getDeclaredMethod("deleteFilesStories", String.class, String.class, String.class);

        method.setAccessible(true);
        String bankId = "TestBank";
        String platform = "WEB";
        String saveDirectory = FILES_SAVE_DIRECTORY + bankId + "/" + platform;
        FileNameCreator.createFolders(saveDirectory);
        File img =  new File(
                FILES_TEST_DIRECTORY,
                "sample.png");
        copyFile(img.getAbsolutePath(), saveDirectory + "/" + "0_0.png");

        // Проверяем, что файл скопирован
        Assertions.assertTrue(new File(saveDirectory).exists());

        FileInputStream input = new FileInputStream(img);
        Assertions.assertNotNull(input);

        FileNameCreator.createFolders(saveDirectory);


        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                img.getName(), "image/png", IOUtils.toByteArray(input));

        JsonProcessorService service = new JsonProcessorService(multipartFileToImageConverter,
                dtoToEntityConverter);


        method.invoke(service, bankId, platform, "0");
        File file = new File(saveDirectory);
        File[] files = file.listFiles();
        Assertions.assertEquals(0, files.length);
    }
    @Test
    void deleteJsonTest() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
        Method method = JsonProcessorService.class.getDeclaredMethod("deleteJsonStories", String.class, String.class, String.class);
        File jsonFile = new File(FILES_TEST_DIRECTORY + "/story_tkbbank_web.json");
        copyFile(jsonFile.getAbsolutePath(), FILES_SAVE_DIRECTORY + "/story_tkbbank_web.json");
        File json = new File(FILES_SAVE_DIRECTORY+"/story_tkbbank_web.json");
        JsonProcessorService service = new JsonProcessorService(multipartFileToImageConverter,
                dtoToEntityConverter);

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
    public static void copyFile(String sourcePathStr, String destinationPathStr) {
        // Преобразуем строки в Path объекты
        Path sourcePath = Paths.get(sourcePathStr);
        Path destinationPath = Paths.get(destinationPathStr);

        try {
            // Копируем файл из sourcePath в destinationPath
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}