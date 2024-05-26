package ru.cft.shiftlab.contentmaker.Stories;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
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

import static ru.cft.shiftlab.contentmaker.util.Constants.*;

@ExtendWith(MockitoExtension.class)
public class DeleteRequestTest {
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
    public static void copyFile(String sourcePathStr, String destinationPathStr) {
        // Преобразуем строки в Path объекты
        Path sourcePath = Paths.get(sourcePathStr);
        Path destinationPath = Paths.get(destinationPathStr);

        try {
            // Копируем файл из sourcePath в destinationPath
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied successfully!");
        } catch (IOException e) {
            System.err.println("Failed to copy file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}