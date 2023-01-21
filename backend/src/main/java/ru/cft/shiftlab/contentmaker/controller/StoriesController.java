package ru.cft.shiftlab.contentmaker.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.cft.shiftlab.contentmaker.dto.StoriesDto;

@Controller
@RequestMapping("/stories")
@Slf4j
public class StoriesController {

    @PostMapping("/")
    public ResponseEntity<StoriesDto> addStories (@RequestBody StoriesDto storiesDto) {

        log.info("Received json with StoriesDto, which contains: {} ", storiesDto);
        log.info("Received json with StoryFramesDto, which contains: {} ", storiesDto.getStoryFramesDto());

        return ResponseEntity.ok(storiesDto);
    }
}
