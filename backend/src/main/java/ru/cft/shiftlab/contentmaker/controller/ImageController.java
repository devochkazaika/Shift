package ru.cft.shiftlab.contentmaker.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;

@RestController
@RequestMapping("/")
public class ImageController {

    @GetMapping("/backend/site/share/htdoc/_files/skins/mobws_story/{bankId}/{platform}/{name}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable("bankId") String bankId,
            @PathVariable("platform") String plaform,
            @PathVariable("name") String name
    ) throws IOException {
        Resource resource = new FileSystemResource(FILES_SAVE_DIRECTORY+bankId+"/"+plaform+"/"+name);
        Path path = Paths.get(resource.getURI());
        byte[] data = Files.readAllBytes(path);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }
}