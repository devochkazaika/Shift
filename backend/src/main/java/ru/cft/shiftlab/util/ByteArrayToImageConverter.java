package ru.cft.shiftlab.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ByteArrayToImageConverter {

    public BufferedImage convertByteArrayToImage(byte [] bytes) throws IOException {
        ByteArrayInputStream bisForPreview = new ByteArrayInputStream(bytes);
        return ImageIO.read(bisForPreview);
    }

}
