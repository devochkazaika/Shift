package ru.cft.shiftlab.contentmaker.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Класс, предназначенный для конвертации массива байтов в картинку, которая в дальнейшем будем сохраняться в директорию.
 */
public class ByteArrayToImageConverter {

    public void convertByteArrayToImageAndSave(byte [] bytes, String directory, String fileName, String fileFormat, int counter) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage bImage = ImageIO.read(bis);
        ImageIO.write(bImage, fileFormat, new File(directory, fileName + counter + "." + fileFormat));
    }

}
