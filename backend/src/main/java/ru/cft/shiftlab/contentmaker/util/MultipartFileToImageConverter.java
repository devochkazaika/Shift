package ru.cft.shiftlab.contentmaker.util;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        String fileFormat = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if(fileFormat == null){
            throw new StaticContentException(
                    "Could not parse file extension on picture",
                    "HTTP 500 - INTERNAL_SERVER_ERROR");
        }
        String fileName = name + "." + fileFormat;
        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
        File outputfile = new File(directory, fileName);
        ImageIO.write(bufferedImage, fileFormat, outputfile);
        return fileName;
    }

    public String parsePicture(ImageContainer imageContainer, String picturesSaveDirectory) throws IOException {
        String previewPictureName = convertMultipartFileToImageAndSave(
                imageContainer.getNextImage(),
                picturesSaveDirectory,
                imageNameGenerator.generateImageName(picturesSaveDirectory)
        );

        return picturesSaveDirectory + previewPictureName;
    }
}
