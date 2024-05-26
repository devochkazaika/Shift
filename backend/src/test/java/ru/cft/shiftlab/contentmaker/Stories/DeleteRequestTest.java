package ru.cft.shiftlab.contentmaker.Stories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.cft.shiftlab.contentmaker.service.implementation.JsonProcessorService;
import ru.cft.shiftlab.contentmaker.util.DtoToEntityConverter;
import ru.cft.shiftlab.contentmaker.util.ImageNameGenerator;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class DeleteRequestTest {
    private final MultipartFileToImageConverter multipartFileToImageConverter = new MultipartFileToImageConverter(new ImageNameGenerator());


    private final DtoToEntityConverter dtoToEntityConverter = new DtoToEntityConverter(new ModelMapper());

    private final JsonProcessorService jsonProcessorService = new JsonProcessorService(
            multipartFileToImageConverter,
            dtoToEntityConverter);

    @Test
    void should_save_files() throws IOException {

    }
}
