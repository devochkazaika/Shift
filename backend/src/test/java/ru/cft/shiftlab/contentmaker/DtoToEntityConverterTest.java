//package ru.cft.shiftlab.contentmaker;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import ru.cft.shiftlab.contentmaker.util.Image.ImageNameGenerator;
//import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
//import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.cft.shiftlab.contentmaker.dto.StoryDto;
//import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
//import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;
//import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
//import ru.cft.shiftlab.contentmaker.util.StoryMapper;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(SpringRunner.class)
//public class DtoToEntityConverterTest {
//    @Mock
//    private StoryMapper modelMapper;
//    @Mock
//    private DtoToEntityConverter converterRequestDto;
//    private StoryPresentation storyPresentation = new StoryPresentation();
//
//
//    @Mock
//    private BannerRepository bannerRepository;
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//    @Test
//    public void whenConvertStoryRequestDtoToStoryPresentation_thenCorrect(){
//        StoryFramesDto storyFramesDto = new StoryFramesDto(
//                "Конвертируй",
//                "Обменивайте валюту онлайн по выгодному курсу",
//                "FFFFFF",
//                "NONE",
//                "Попробовать",
//                "FFFFFF",
//                "FFFFFF",
//                "buttonurl",
//                "EMPTY"
//        );
//
//        StoryDto storyDto = new StoryDto(
//                "Конвертируй валюту",
//                "FFFFFF",
//                "EMPTY",
//                new ArrayList<>(Collections.singletonList(storyFramesDto))
//        );
//
//        StoryPresentation storyPresentation = converterRequestDto.fromStoryDtoToStoryPresentation("id", storyDto, null);
//
//        Assertions.assertAll(
//                () -> assertEquals(storyDto.getPreviewTitle(), storyPresentation.getPreviewTitle()),
//                () -> assertEquals(storyDto.getPreviewTitleColor(), storyPresentation.getPreviewTitleColor()),
//                () -> assertEquals(storyDto.getPreviewGradient(), storyPresentation.getPreviewGradient()),
//
//                () -> Assertions.assertEquals(storyPresentation.getPreviewTitle(), storyDto.getPreviewTitle()),
//                () -> Assertions.assertEquals(storyPresentation.getPreviewGradient(), storyDto.getPreviewGradient()),
//                () -> Assertions.assertEquals(storyPresentation.getPreviewTitleColor(), storyDto.getPreviewTitleColor()),
//                () -> Assertions.assertEquals(storyPresentation.getStoryPresentationFrames().get(0).getText(),
//                        storyDto.getStoryFramesDtos().get(0).getText()),
//                () -> Assertions.assertEquals(storyPresentation.getStoryPresentationFrames().get(0).getGradient(),
//                        storyDto.getStoryFramesDtos().get(0).getGradient()),
//                () -> Assertions.assertEquals(storyPresentation.getStoryPresentationFrames().get(0).getButtonUrl(),
//                        storyDto.getStoryFramesDtos().get(0).getButtonUrl()),
//                () -> Assertions.assertEquals(storyPresentation.getStoryPresentationFrames().get(0).getTitle(),
//                        storyDto.getStoryFramesDtos().get(0).getTitle()
//                )
//        );
//    }
//
//
//    public static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
