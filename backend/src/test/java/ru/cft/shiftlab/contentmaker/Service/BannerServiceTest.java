package ru.cft.shiftlab.contentmaker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.BannerDto;
import ru.cft.shiftlab.contentmaker.entity.Bank;
import ru.cft.shiftlab.contentmaker.entity.Banner;
import ru.cft.shiftlab.contentmaker.repository.BankRepository;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
import ru.cft.shiftlab.contentmaker.service.implementation.BannerProcessorService;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.Image.ImageNameGenerator;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.cft.shiftlab.contentmaker.util.Constants.BANNERS_SAVE_DIRECTORY;
import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_TEST_DIRECTORY;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BannerServiceTest {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BankRepository bankRepository;

    private BannerProcessorService bannerProcessorService;

    @BeforeEach
    public void setUp() {
        DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(bannerRepository, bankRepository, new ModelMapper());
        bannerProcessorService = new BannerProcessorService(
                dtoToEntityConverter,
                new MultipartFileToImageConverter(new ImageNameGenerator()),
                new DirProcess(),
                bannerRepository,
                bankRepository
        );
    }

    private MultipartFile fromFileToMultipartFile(String path) throws IOException {
        File file = new File(path);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        return multipartFile;
    }

    @Test
    public void save_to_db_test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = BannerProcessorService.class.getDeclaredMethod("saveToDb", BannerDto.class, String[].class);
        method.setAccessible(true);

        Banner banner = Banner.builder()
                .id(0L)
                .code("test_code")
                .name("test_banner_name")
                .picture("test/test_picture.jpg")
                .icon("test/test_icon.jpg")
                .url("http://asdasdasd")
                .priority(2)
                .build();

        BannerDto bannerDto = BannerDto.builder()
                .code("test_code")
                .name("test_banner_name")
                .url("http://asdasdasd")
                .priority(2)
                .build();

        String[] arrayNames = new String[]{"test/test_picture.jpg", "test/test_icon.jpg"};
        Banner bannerActual = (Banner) method.invoke(bannerProcessorService, bannerDto, (Object) arrayNames);

        assertAll(
                () -> assertEquals(banner.getCode(), bannerActual.getCode()),
                () -> assertEquals(banner.getName(), bannerActual.getName()),
                () -> assertEquals(banner.getPicture(), bannerActual.getPicture()),
                () -> assertEquals(banner.getIcon(), bannerActual.getIcon()),
                () -> assertEquals(banner.getUrl(), bannerActual.getUrl()),
                () -> assertEquals(banner.getPriority(), bannerActual.getPriority())
        );

        Banner bannerFromDb = bannerRepository.findBannerByCode("test_code");
        assertAll(
                () -> assertEquals(banner.getCode(), bannerFromDb.getCode()),
                () -> assertEquals(banner.getName(), bannerFromDb.getName()),
                () -> assertEquals(banner.getPicture(), bannerFromDb.getPicture()),
                () -> assertEquals(banner.getIcon(), bannerFromDb.getIcon()),
                () -> assertEquals(banner.getUrl(), bannerFromDb.getUrl()),
                () -> assertEquals(banner.getPriority(), bannerFromDb.getPriority())
        );
    }
    @Test
    public void save_images_banners() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        Method method = BannerProcessorService.class.getDeclaredMethod("saveImage",
                BannerDto.class, MultipartFile.class,
                MultipartFile.class);
        method.setAccessible(true);
        MultipartFile picture = fromFileToMultipartFile( FILES_TEST_DIRECTORY+ "test_picture.png");
        MultipartFile icon = fromFileToMultipartFile( FILES_TEST_DIRECTORY+ "test_picture.png");

        Banner banner = Banner.builder()
                .id(0L)
                .bank(new Bank(UUID.randomUUID(), "test", "test_bank"))
                .code("test_code")
                .name("test_banner_name")
                .picture("test/test_picture.jpg")
                .icon("test/test_icon.jpg")
                .url("http://asdasdasd")
                .priority(2)
                .build();

        BannerDto bannerDto = BannerDto.builder()
                .code("test_code")
                .bankName("test")
                .name("test_banner_name")
                .url("http://asdasdasd")
                .priority(2)
                .build();

        String[] arrayNames = new String[]{"test/test_picture.png", "test/test_icon.png"};
        String[] names = (String[]) method.invoke(bannerProcessorService, bannerDto, picture, icon);
//        Assertions.assertEquals(arrayNames, names);

        File directory = new File(BANNERS_SAVE_DIRECTORY+"test/");
        int length = directory.listFiles().length;
        Assertions.assertEquals(length, 2);

        Set<String> setExpected = new HashSet<String>()
        {{add("test_code_pict.png");add("test_code_icon.png");}};

        Set<String> setActual = new HashSet<String>();
        Arrays.stream(directory.listFiles())
                .forEach(x -> setActual.add(x.getName()));
        Assertions.assertEquals(setExpected, setActual);

        directory.delete();
    }
    @Test
    public void addBanner_test() throws IOException {
        BannerDto bannerDto = BannerDto.builder()
                .code("test_code")
                .bankName("test")
                .name("test_banner_name")
                .url("http://asdasdasd")
                .text("any text")
                .color("green")
                .priority(2)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(bannerDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        MultipartFile pictFile = fromFileToMultipartFile(FILES_TEST_DIRECTORY+"sample.png");
        MultipartFile iconFile = fromFileToMultipartFile(FILES_TEST_DIRECTORY+"sample.png");
        bannerProcessorService.addBanner(jsonString, pictFile, iconFile);
        Banner bannerExpected = Banner.builder()
                .code("test_code")
                .bank(null)
                .name("test_banner_name")
                .url("http://asdasdasd")
                .text("any text")
                .color("green")
                .priority(2)
                .build();
        Banner bannerActual = bannerRepository.findBannerByCode("test_code");
        Assertions.assertAll(
                () -> assertEquals(bannerExpected.getCode(), bannerActual.getCode()),
                () -> assertEquals(bannerExpected.getName(), bannerActual.getName()),
                () -> assertEquals(bannerExpected.getUrl(), bannerExpected.getUrl()),
                () -> assertEquals(bannerExpected.getText(), bannerActual.getText()),
                () -> assertEquals(bannerExpected.getColor(), bannerActual.getColor()),
                () -> assertEquals(bannerExpected.getPicture(), bannerExpected.getPicture())
        );
    }
}
