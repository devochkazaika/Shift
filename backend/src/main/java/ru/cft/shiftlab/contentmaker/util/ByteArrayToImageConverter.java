package ru.cft.shiftlab.contentmaker.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ByteArrayToImageConverter {

    public void convertByteArrayToImageAndSave(byte [] bytes, String directory, String fileName, String fileFormat, int counter) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, fileFormat.substring(1), new File(directory, fileName + counter + fileFormat));
    }

}
