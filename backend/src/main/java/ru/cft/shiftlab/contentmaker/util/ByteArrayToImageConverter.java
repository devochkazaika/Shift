package ru.cft.shiftlab.contentmaker.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Класс, предназначенный для конвертации массива байтов в картинку, которая в дальнейшем будет сохраняться в директорию.
 */
public class ByteArrayToImageConverter {

    /**
     * Метод для конвертации массива байтов в картинку и для ее сохранения в определенную директорию.
     *
     * @param fileFormat расширение файла.
     * @param directory  путь до директории, в которую будут сохраняться картинки.
     * @param fileName   название файла, который представляет собой картинку.
     * @param counter    счетчик, который считает количество картинок, подлежащих сохранению.
     * @param bytes      байтовый массив, который будет конвертироваться в картинку.
     * @throws IOException исключение ввода-вывода.
     */
    public static void convertByteArrayToImageAndSave(byte [] bytes, String directory, String fileName,
                                                      String fileFormat, int counter) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage bImage = ImageIO.read(bis);
        ImageIO.write(bImage, fileFormat, new File(directory, fileName + counter + "." + fileFormat));
    }

}
