package ru.cft.shiftlab.contentmaker.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


/**
 * Сервис предназначенный для для перебора картинок по порядку.
 */
@Setter
@Getter
public class ImageContainer {
    MultipartFile[] images;
    Integer counterImages = 0;

    public ImageContainer(MultipartFile[] images) {
        this.images = images;
    }
    public ImageContainer(MultipartFile images) {

        this.images = new MultipartFile[1];
        this.images[0] = images;
    }

    public MultipartFile getNextImage(){
        return images[counterImages++];
    }

    public void decrementCounterImages(){
        counterImages--;
    }
}
