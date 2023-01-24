package ru.cft.shiftlab.contentmaker.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.services.IStoriesService;

import java.io.File;

@Service
public class StoriesService implements IStoriesService {

    public void saveJson(StoriesRequestDto storiesRequestDto) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String directory = "/content-maker/backend/src/main/resources/data";
            String fileName = "stories.json";
            mapper.writeValue(new File(directory, fileName), storiesRequestDto);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
