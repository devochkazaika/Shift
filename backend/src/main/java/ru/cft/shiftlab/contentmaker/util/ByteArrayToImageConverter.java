package ru.cft.shiftlab.contentmaker.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Класс, предназначенный для конвертации массива байтов в картинку,
 * которая в дальнейшем сохраняется в директорию.
 */
@Component
public class ByteArrayToImageConverter {

    /**
     * Метод для конвертации массива байтов в картинку и для ее сохранения в определенную директорию.
     *
     * @param fileFormat расширение файла.
     * @param directory  путь до директории, в которую будут сохраняться картинки.
     * @param fileName   название файла, который представляет собой картинку.
     * @param bytes      байтовый массив, который будет конвертироваться в картинку.
     * @throws IOException исключение ввода-вывода.
     */
    public void convertByteArrayToImageAndSave(byte [] bytes, String directory, String fileName,
                                                      String fileFormat) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage bImage = ImageIO.read(bis);
        ImageIO.write(bImage, fileFormat, new File(directory, fileName + "." + fileFormat));
    }

}
