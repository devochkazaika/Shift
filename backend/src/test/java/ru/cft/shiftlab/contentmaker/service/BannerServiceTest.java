//package ru.cft.shiftlab.contentmaker.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.io.IOUtils;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.multipart.MultipartFile;
//import ru.cft.shiftlab.contentmaker.dto.BannerDto;
//import ru.cft.shiftlab.contentmaker.entity.Banner;
//import ru.cft.shiftlab.contentmaker.exceptionhandling.ResourceNotFoundException;
//import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
//import ru.cft.shiftlab.contentmaker.service.implementation.BannerProcessorService;
//import ru.cft.shiftlab.contentmaker.util.DirProcess;
//import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
//import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.*;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static ru.cft.shiftlab.contentmaker.util.Constants.BANNERS_SAVE_DIRECTORY;
//import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_TEST_DIRECTORY;
//
//@SpringBootTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//@ExtendWith(SpringExtension.class)
//public class BannerServiceTest {
//
//    ObjectMapper mapper = new ObjectMapper();
//
//    @Mock
//    private BannerRepository bannerRepository;
//
//    private BannerProcessorService bannerProcessorService;
//
//    @Mock
//    private MultipartFileToImageConverter multipartFileToImageConverter;
//
//    @BeforeEach
//    public void setUp() {
//        DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper(), multipartFileToImageConverter);
//        bannerProcessorService = new BannerProcessorService(
//                dtoToEntityConverter,
//                multipartFileToImageConverter,
//                new DirProcess(),
//                bannerRepository
//        );
//    }
//
//    /**
//     * Для удобной конвертации из файла в Multipart
//     * @param path
//     * @return
//     * @throws IOException
//     */
//    private MultipartFile fromFileToMultipartFile(String path) throws IOException {
//        File file = new File(path);
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file",
//                file.getName(), "text/plain", IOUtils.toByteArray(input));
//        return multipartFile;
//    }
//
//    /**
//     * Тест для приватного метода save_to_db для добавления банера в bd
//     * @throws NoSuchMethodException
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    @Test
//    public void save_to_db_test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Method method = BannerProcessorService.class.getDeclaredMethod("saveToDb", BannerDto.class, String[].class);
//        method.setAccessible(true);
//
//        Banner banner = Banner.builder()
//                .id(0L)
//                .code("test_code")
//                .name("test_banner_name")
//                .picture("test/test_picture.jpg")
//                .icon("test/test_icon.jpg")
//                .url("http://asdasdasd")
//                .priority(2)
//                .build();
//
//        BannerDto bannerDto = BannerDto.builder()
//                .code("test_code")
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .priority(2)
//                .build();
//
//        String[] arrayNames = new String[]{"test/test_picture.jpg", "test/test_icon.jpg"};
//        Banner bannerActual = (Banner) method.invoke(bannerProcessorService, bannerDto, (Object) arrayNames);
//
//        assertAll(
//                () -> assertEquals(banner.getCode(), bannerActual.getCode()),
//                () -> assertEquals(banner.getName(), bannerActual.getName()),
//                () -> assertEquals(banner.getPicture(), bannerActual.getPicture()),
//                () -> assertEquals(banner.getIcon(), bannerActual.getIcon()),
//                () -> assertEquals(banner.getUrl(), bannerActual.getUrl()),
//                () -> assertEquals(banner.getPriority(), bannerActual.getPriority())
//        );
//
//        Banner bannerFromDb = bannerRepository.findBannerByCode("test_code").orElse(null);
//        assertAll(
//                () -> assertEquals(banner.getCode(), bannerFromDb.getCode()),
//                () -> assertEquals(banner.getName(), bannerFromDb.getName()),
//                () -> assertEquals(banner.getPicture(), bannerFromDb.getPicture()),
//                () -> assertEquals(banner.getIcon(), bannerFromDb.getIcon()),
//                () -> assertEquals(banner.getUrl(), bannerFromDb.getUrl()),
//                () -> assertEquals(banner.getPriority(), bannerFromDb.getPriority())
//        );
//    }
//    /**
//     * Тест для приватного метода save_images для сохранения банера в bd
//     * @throws NoSuchMethodException
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    @Test
//    public void save_images_banners() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
//        Method method = BannerProcessorService.class.getDeclaredMethod("saveImage",
//                BannerDto.class, MultipartFile.class,
//                MultipartFile.class);
//        method.setAccessible(true);
//        MultipartFile picture = fromFileToMultipartFile( FILES_TEST_DIRECTORY+ "test_picture.png");
//        MultipartFile icon = fromFileToMultipartFile( FILES_TEST_DIRECTORY+ "test_picture.png");
//
//        Banner banner = Banner.builder()
//                .id(0L)
//                .bankName("test_bank")
//                .code("test_code")
//                .name("test_banner_name")
//                .picture("test/test_picture.jpg")
//                .icon("test/test_icon.jpg")
//                .url("http://asdasdasd")
//                .priority(2)
//                .build();
//
//        BannerDto bannerDto = BannerDto.builder()
//                .code("test_code")
//                .bankName("test")
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .priority(2)
//                .build();
//
//        String[] arrayNames = new String[]{"test/test_picture.png", "test/test_icon.png"};
//        String[] names = (String[]) method.invoke(bannerProcessorService, bannerDto, picture, icon);
////        Assertions.assertEquals(arrayNames, names);
//
//        File directory = new File(BANNERS_SAVE_DIRECTORY+"test/");
//        int length = directory.listFiles().length;
//        Assertions.assertEquals(length, 2);
//
//        Set<String> setExpected = new HashSet<String>()
//        {{add("test_code_pict.png");add("test_code_icon.png");}};
//
//        Set<String> setActual = new HashSet<String>();
//        Arrays.stream(directory.listFiles())
//                .forEach(x -> setActual.add(x.getName()));
//        Assertions.assertEquals(setExpected, setActual);
//
//        directory.delete();
//    }
//    /**
//     * Тест для добавления банера, вызываемого контроллером
//     * @throws IOException
//     */
//    @Test
//    public void addBanner_test() throws IOException {
//        BannerDto bannerDto = BannerDto.builder()
//                .code("test_code")
//                .bankName("test")
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .text("any text")
//                .color("green")
//                .priority(2)
//                .build();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = "";
//        try {
//            jsonString = objectMapper.writeValueAsString(bannerDto);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        MultipartFile pictFile = fromFileToMultipartFile(FILES_TEST_DIRECTORY+"sample.png");
//        MultipartFile iconFile = fromFileToMultipartFile(FILES_TEST_DIRECTORY+"sample.png");
//        bannerProcessorService.addBanner(jsonString, pictFile, iconFile);
//        Banner bannerExpected = Banner.builder()
//                .code("test_code")
//                .bankName(null)
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .text("any text")
//                .color("green")
//                .priority(2)
//                .build();
//        Banner bannerActual = bannerRepository.findBannerByCode("test_code").orElse(null);
//        Assertions.assertAll(
//                () -> assertEquals(bannerExpected.getCode(), bannerActual.getCode()),
//                () -> assertEquals(bannerExpected.getName(), bannerActual.getName()),
//                () -> assertEquals(bannerExpected.getUrl(), bannerExpected.getUrl()),
//                () -> assertEquals(bannerExpected.getText(), bannerActual.getText()),
//                () -> assertEquals(bannerExpected.getColor(), bannerActual.getColor()),
//                () -> assertEquals(bannerExpected.getPicture(), bannerExpected.getPicture())
//        );
//    }
//    /**
//     * Тест для назначения банеру - mainBanner
//     */
//    @Test
//    public void setMainBanner_test(){
//        String code = "test_code";
//        String codeMainBanner = "test_MainBanner_code";
//        Banner banner = Banner.builder()
//                .code(code)
//                .bankName(null)
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .text("any text")
//                .color("green")
//                .priority(2)
//                .build();
//        Banner mainBanner = Banner.builder()
//                .code(codeMainBanner)
//                .bankName(null)
//                .name("test_mainBanner_name")
//                .url("http://mainBanner")
//                .text("any text for mainBanner")
//                .color("green")
//                .priority(2)
//                .build();
//        bannerRepository.save(banner);
//        bannerRepository.save(mainBanner);
//        bannerProcessorService.setMainBanner(code, codeMainBanner);
//        Banner bannerActual = bannerRepository.findBannerByCode(code).orElse(null);
//        Banner bannerExpected = Banner.builder()
//                .code(code)
//                .bankName(null)
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .text("any text")
//                .color("green")
//                .priority(2)
//                .mainBanner(mainBanner)
//                .build();
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(bannerExpected.getMainBanner(), bannerActual.getMainBanner()),
//                () -> Assertions.assertEquals(bannerExpected.getName(), bannerActual.getName()),
//                () -> Assertions.assertEquals(bannerExpected.getUrl(), bannerActual.getUrl()),
//                () -> Assertions.assertEquals(bannerExpected.getText(), bannerActual.getText()),
//                () -> Assertions.assertEquals(bannerExpected.getColor(), bannerActual.getColor())
//        );
//    }
//
//    @Test
//    public void setMainBanner_test_throwResourceNotFoundException(){
//        String code = "test_code";
//        String codeMainBanner = "test_MainBanner_code";
//        Banner banner = Banner.builder()
//                .code(code)
//                .bankName(null)
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .text("any text")
//                .color("green")
//                .priority(2)
//                .build();
//        Banner mainbanner = Banner.builder()
//                .code(codeMainBanner)
//                .bankName(null)
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .text("any text")
//                .color("green")
//                .priority(2)
//                .build();
//        ResourceNotFoundException thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//            bannerProcessorService.setMainBanner(code, codeMainBanner);
//        });
//        Assertions.assertEquals(String.format("banner with code= %s not found", code), thrown.getMessage());
//        bannerRepository.save(banner);
//        thrown = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
//            bannerProcessorService.setMainBanner(code, codeMainBanner);
//        });
//        Assertions.assertEquals(String.format("banner with code= %s not found", codeMainBanner), thrown.getMessage());
//        bannerRepository.save(mainbanner);
//        Assertions.assertDoesNotThrow(() -> {
//            bannerProcessorService.setMainBanner(code, codeMainBanner);
//        });
//    }
//
//    /**
//     * Для начала нужно дописать гет запрос(
//     */
//    @Test
//    public void getBanners() throws JsonProcessingException {
//        String codeFirst = "test_codeFirst";
//        String codeSecond = "test_codeSecond";
//        Banner bannerFirst = Banner.builder()
//                .code(codeFirst)
//                .bankName("tkbbank")
//                .platformType("WEB")
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .text("any text")
//                .color("green")
//                .priority(2)
//                .build();
//        Banner bannerSecond = Banner.builder()
//                .code(codeSecond)
//                .platformType("WEB")
//                .bankName("tkbbank")
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .text("any text")
//                .color("green")
//                .priority(2)
//                .build();
//        bannerRepository.save(bannerFirst);
//        bannerRepository.save(bannerSecond);
//        List<Banner> bannerList = Arrays.asList(bannerFirst, bannerSecond);
//        List<Banner> actual = bannerProcessorService.getBannersList("tkbbank", "WEB");
//        Assertions.assertEquals(bannerList, actual);
//    }
//
//    @Test
//    public void change_banner_test_success_test() throws JsonProcessingException {
//        BannerDto bannerDto = BannerDto
//                .builder()
//                .bankName("tkbbank")
//                .name("test_dto")
//                .platformType("WEB")
//                .priority(2)
//                .url("http://sadasdasd")
//                .textUrl("sample text for url")
//                .siteSection("site section")
//                .color("black")
//                .text("text")
//                .build();
//        Banner banner = Banner.builder()
//                .code("test_code")
//                .name("first name")
//                .text("first text")
//                .url("first url")
//                .textUrl("first url Text")
//                .availableForAll(false)
//                .platformType("ALL PLATFORMS")
//                .priority(3)
//                .build();
//
//        bannerRepository.save(banner);
//        bannerProcessorService.patchBanner(mapper.writeValueAsString(bannerDto), "test_code");
//        banner = bannerRepository.findBannerByCode("test_code").orElse(null);
//        Banner finalBanner = banner;
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(bannerDto.getPriority(), finalBanner.getPriority()),
//                () -> Assertions.assertEquals(bannerDto.getName(), finalBanner.getName()),
//                () -> Assertions.assertEquals(bannerDto.getPlatformType(), finalBanner.getPlatformType()),
//                () -> Assertions.assertEquals(bannerDto.getUrl(), finalBanner.getUrl()),
//                () -> Assertions.assertEquals(bannerDto.getTextUrl(), finalBanner.getTextUrl()),
//                () -> Assertions.assertEquals(false, finalBanner.getAvailableForAll()),
//                () -> Assertions.assertEquals(bannerDto.getColor(), finalBanner.getColor())
//
//        );
//    }
//
//    @Test
//    public void change_banner_not_found_exception_banner_test() throws JsonProcessingException {
//        BannerDto bannerDto = BannerDto
//                .builder()
//                .bankName("tkbbank")
//                .name("test_dto")
//                .platformType("WEB")
//                .priority(2)
//                .url("http://sadasdasd")
//                .textUrl("sample text for url")
//                .siteSection("site section")
//                .color("black")
//                .text("text")
//                .build();
//
//        Assertions.assertThrows(
//                ResourceNotFoundException.class,
//                () -> bannerProcessorService.patchBanner(mapper.writeValueAsString(bannerDto), "test_code")
//        );
//    }
//
//    private void saveExampleToDb(){
//        Banner mainbanner = Banner.builder()
//                .code("code_main_banner")
//                .bankName(null)
//                .name("test_banner_name")
//                .url("http://asdasdasd")
//                .text("any text")
//                .color("green")
//                .priority(2)
//                .build();
//        Banner banner = Banner.builder()
//                .code("test_code")
//                .name("first name")
//                .text("first text")
//                .url("first url")
//                .mainBanner(mainbanner)
//                .textUrl("first url Text")
//                .availableForAll(false)
//                .platformType("ALL PLATFORMS")
//                .priority(3)
//                .build();
//        bannerRepository.save(mainbanner);
//        bannerRepository.save(banner);
//    }
//
//    @Test
//    public void delete_banner_successful_test(){
//        saveExampleToDb();
//        bannerProcessorService.deleteBanner("test_code");
//        Assertions.assertEquals(
//                 bannerRepository.findBannerByCode("test_code").orElse(null),
//                null
//        );
//    }
//
//    @Test
//    public void delete_banner_cascade_successful_test(){
//        saveExampleToDb();
//        bannerProcessorService.deleteBannerCascade("test_code");
//        Assertions.assertEquals(
//                bannerRepository.findBannerByCode("test_code").orElse(null),
//                null
//        );
//        Assertions.assertEquals(
//                bannerRepository.findBannerByCode("code_main_banner").orElse(null),
//                null
//        );
//    }
//}
//
