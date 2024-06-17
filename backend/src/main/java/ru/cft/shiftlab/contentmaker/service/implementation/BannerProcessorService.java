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
import ru.cft.shiftlab.contentmaker.entity.Banner;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
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
    private final BannerRepository bannerRepository;

    /**
     * Метод для назначения mainBanner для банера
     * @param code
     * @param codeMainBanner
     */
    public void setMainBanner(String code, String codeMainBanner){
        Banner banner = bannerRepository.findBannerByCode(code);
        Banner mainBanner = bannerRepository.findBannerByCode(codeMainBanner);
        banner.setMainBanner(mainBanner);
    }
    public void addBanner(String bannerRequestDto,
                          MultipartFile picture,
                          MultipartFile icon) throws IOException {
        BannerDto bannerDto = mapper.readValue(bannerRequestDto, BannerDto.class);
        try {
            String[] namesFiles = saveImage(bannerDto, picture, icon);
            saveToDb(bannerDto, namesFiles);
        }
        catch (IOException e){
            throw new StaticContentException("Could not save file", "500");
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }

    /**
     * Метод для сохранение информации по банеру в БД
     * @param bannerDto
     * @param filesName
     * @return Banner
     */
    private Banner saveToDb(BannerDto bannerDto, String[] filesName){
        Banner banner = dtoToEntityConverter.fromBannerDtoToBanner(bannerDto,
                filesName[0],
                filesName[1]);
        try {
            banner = bannerRepository.save(banner);
            return banner;
        }
        catch (Exception e){
            if (bannerRepository.findBannerByCode(banner.getCode()) == null){
                throw new StaticContentException("Banner with code " + banner.getCode() + " is already exist",
                        "500");
            }
            throw new StaticContentException("Could not save files",
                    "500");
        }
    }

    /**
     * Метод для сохранения картинок для банеров
     * @param bannerDto
     * @param picture
     * @param icon
     * @return
     * @throws IOException
     */
    private String[] saveImage(BannerDto bannerDto, MultipartFile picture,
                            MultipartFile icon) throws IOException {
        String picturesSaveDirectory = BANNERS_SAVE_DIRECTORY;
        String bankId = bannerDto.getBankName();
        dirProcess.createFolders(picturesSaveDirectory+bankId+"/");
        String pictureName = multipartFileToImageConverter.convertMultipartFileToImageAndSave(
                picture,
                picturesSaveDirectory,
                bankId + "/" + bannerDto.getCode()+"_"+ "pict"
        );
        String iconName = multipartFileToImageConverter.convertMultipartFileToImageAndSave(
                icon,
                picturesSaveDirectory,
                bankId  + "/" + bannerDto.getCode() + "_" + "icon"
        );
        return new String[]{pictureName, iconName};
    }
}
