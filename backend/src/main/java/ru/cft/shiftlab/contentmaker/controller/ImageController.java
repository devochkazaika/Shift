package ru.cft.shiftlab.contentmaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;

@RestController
@RequestMapping("/")
public class ImageController {
    /**
     * Get запрос для получения картинки
     * @return Array of bytes
     * @throws IOException
     */
    @GetMapping("image")
    @Operation(summary = "Возврат картинки из пути в JSON")
    public ResponseEntity<byte[]> getImage(
            @RequestParam("path") String path
    ) throws IOException {
        Resource resource = new FileSystemResource(path);
        Path file = Paths.get(resource.getURI());
        byte[] data = Files.readAllBytes(file);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }
}