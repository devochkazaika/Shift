package ru.cft.shiftlab.contentmaker.service.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.BannerDto;
import ru.cft.shiftlab.contentmaker.entity.Bank;
import ru.cft.shiftlab.contentmaker.entity.Banner;
import ru.cft.shiftlab.contentmaker.exceptionhandling.ResourceNotFoundException;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;
import ru.cft.shiftlab.contentmaker.repository.BankRepository;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
import ru.cft.shiftlab.contentmaker.service.BannerService;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.MultipartFileToImageConverter;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static ru.cft.shiftlab.contentmaker.util.Constants.BANNERS_SAVE_DIRECTORY;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class BannerProcessorService implements BannerService {
    ObjectMapper mapper = new ObjectMapper();
    {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature
                        .FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private final DtoToEntityConverter dtoToEntityConverter;
    private final MultipartFileToImageConverter multipartFileToImageConverter;
    private final DirProcess dirProcess;
    private final BannerRepository bannerRepository;
    private final BankRepository bankRepository;

    /**
     * Метод для назначения mainBanner для банера
     *
     * @param code Код банера
     * @param codeMainBanner Код mainBanner
     */
    @Transactional
    public void setMainBanner(String code, String codeMainBanner){
        Banner banner = bannerRepository.findBannerByCode(code).orElseThrow(
                () -> new ResourceNotFoundException(String.format("banner with code= %s not found", code))
        );
        Banner mainBanner = bannerRepository.findBannerByCode(codeMainBanner).orElseThrow(
                () -> new ResourceNotFoundException(String.format("banner with code= %s not found", codeMainBanner))
        );
        banner.setMainBanner(mainBanner);
        bannerRepository.updateBannerByMainBanner(banner.getId(), mainBanner.getId());
    }

    /**
     * Метод для добавления банера на сервер
     * @param bannerRequestDto BannerDto в формате <code>String</code>
     * @param picture Картинка банера
     * @param icon Иконка банера
     * @throws IOException
     */
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
     * Возвращение списка банеров определенного банка и платформы
     * @param bankId Банк
     * @param platform Платформа (WEB | IOS | ALL PLATFORMS | ANDROID)
     * @return <code>List<Banner></code> Список банеров
     */
    @Override
    public List<Banner> getBannersList(String bankId, String platform) {
        Bank bank = bankRepository.findBankByName(bankId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("could not find bank with name = %s", bankId))
        );
        return bannerRepository.findBannerByBankAndPlatform(bank, platform);
    }

    /**
     * Метод для сохранения информации по банеру в БД (без картинки)
     * @param bannerDto BannerDto
     * @param filesName Массив имен файлов (1 - иконка, 2 - картинка)
     * @return Banner сохраненный банер
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
            if (bannerRepository.findBannerByCode(banner.getCode()).orElse(null) == null){
                throw new StaticContentException("Banner with code " + banner.getCode() + " is already exist",
                        "500");
            }
            throw new StaticContentException("Could not save files",
                    "500");
        }
    }

    /**
     * Метод для сохранения картинок для банеров
     * @param bannerDto Параметры банера BannerDto
     * @param picture Картинка для банера
     * @param icon Иконка банера
     * @return String[] массив имен 1-иконка, 2-картинка
     * @throws IOException
     */
    private String[] saveImage(BannerDto bannerDto, MultipartFile picture,
                            MultipartFile icon) throws IOException {
        String bankId = bannerDto.getBankName();
        String picturesSaveDirectory = BANNERS_SAVE_DIRECTORY;
        dirProcess.createFolders(picturesSaveDirectory+bankId+"/");
        String pictureName = multipartFileToImageConverter.convertMultipartFileToImageAndSave(
                picture,
                picturesSaveDirectory,
                String.format("%s/%s_pict", bankId, bannerDto.getCode())
        );
        String iconName = multipartFileToImageConverter.convertMultipartFileToImageAndSave(
                icon,
                picturesSaveDirectory,
                String.format("%s/%s_icon", bankId, bannerDto.getCode())
        );
        return new String[]{pictureName, iconName};
    }


    /**
     * Удаление банера
     * @param code Код банера
     */
    public void deleteBanner(String code){
        bannerRepository.deleteBannerByCode(code);
    }

    /**
     * Каскадное удаление банера вместе с его mainBanner
     * @param code Код банера
     */
    @Transactional
    public void deleteBannerCascade(String code){
        Banner banner = bannerRepository.findBannerByCode(code)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Banner with code = %s doesnt exist", code))
                );
        deleteBanner(code);
        deleteBanner(banner.getMainBanner().getCode());
    }

    /**
     * Изменение банера (без картинки)
     * @param bannerDto BannerDto
     * @param code Код банера
     * @throws JsonProcessingException
     */
    public void patchBanner(String bannerDto, String code) throws JsonProcessingException {
        BannerDto dto = mapper.readValue(bannerDto, BannerDto.class);
        //Нахождение банера из бд по коду
        Banner banner = bannerRepository.findBannerByCode(code)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String
                                .format("Banner with code = %s doesnt exist", code))
                );
        //Обновление банера
        banner = mapper.updateValue(banner, dto);
        bannerRepository.save(banner);
    }
}
