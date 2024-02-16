package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.dto.StoryDto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Класс, предназначенный для конвертации массива байтов в картинку,
 * которая в дальнейшем сохраняется в директорию.
 */
@Component
@RequiredArgsConstructor
public class MultipartFileToImageConverter {
    private final ImageNameGenerator imageNameGenerator;

    /**
     * Метод для конвертации MultipartFile в картинку и для ее сохранения в определенную директорию.
     *
     * @param directory  путь до директории, в которую будут сохраняться картинки.
     * @param name   название файла, который представляет собой картинку. Без расширения
     * @param multipartFile      файл, который будет конвертироваться в картинку.
     * @throws IOException исключение ввода-вывода.
     * @return String Название файла с расширением.
     */
    public String convertMultipartFileToImageAndSave(
            MultipartFile multipartFile,
            String directory,
            String name) throws IOException {
        String fileFormat = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1];
        String fileName = name + "." + fileFormat;
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        File outputfile = new File(directory, fileName + "." + fileFormat);
        ImageIO.write(bufferedImage, fileFormat, outputfile);
        return fileName + "." + fileFormat;
    }

    public String parsePicture(String pictureUrl, ImageContainer imageContainer, String picturesSaveDirectory) throws IOException {
        String previewPictureName = pictureUrl;
        if(pictureUrl == null || pictureUrl.isEmpty()){
            previewPictureName = imageNameGenerator.generateImageName();

            previewPictureName = convertMultipartFileToImageAndSave(
                    imageContainer.getNextImage(),
                    picturesSaveDirectory,
                    previewPictureName
            );
        }

        return picturesSaveDirectory + previewPictureName;
    }
}
