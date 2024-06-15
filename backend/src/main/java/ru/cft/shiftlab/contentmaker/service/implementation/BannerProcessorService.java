package ru.cft.shiftlab.contentmaker.service.implementation;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.BannerDto;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.Image.ImageContainer;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

import java.io.IOException;

import static ru.cft.shiftlab.contentmaker.util.Constants.BANNERS_SAVE_DIRECTORY;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class BannerProcessorService {
    ObjectMapper mapper = new ObjectMapper();
    {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature
                        .FAIL_ON_UNKNOWN_PROPERTIES,
                false);
    }

    private final DtoToEntityConverter dtoToEntityConverter;
    private final MultipartFileToImageConverter multipartFileToImageConverter;
    private final DirProcess dirProcess;


    public void addBanner(String banner,
                          MultipartFile picture,
                          MultipartFile icon) throws IOException {

        BannerDto bannerDto = mapper.readValue(banner, BannerDto.class);
//        System.out.println(bannerDto);
        String bankId = bannerDto.getBankName();
        String platformType = bannerDto.getPlatformType();
        String picturesSaveDirectory = BANNERS_SAVE_DIRECTORY;
        dirProcess.createFolders(picturesSaveDirectory+bankId+"/");
        ImageContainer imageContainerPreview = new ImageContainer(picture);
        String pictureName = multipartFileToImageConverter.convertMultipartFileToImageAndSave(
                picture,
                picturesSaveDirectory,
                bankId + "/" + bannerDto.getCode()+"_"+ "pict"
        );
        String iconName = multipartFileToImageConverter.convertMultipartFileToImageAndSave(
                picture,
                picturesSaveDirectory,
                bankId + "/" + bannerDto.getCode() + "_" + "icon"
        );
        System.out.println(pictureName);
        System.out.println(iconName);
    }
}
