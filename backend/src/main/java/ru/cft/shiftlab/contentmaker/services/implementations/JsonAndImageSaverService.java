package ru.cft.shiftlab.contentmaker.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;
import ru.cft.shiftlab.contentmaker.services.FileSaverService;

import java.io.File;
import java.io.IOException;

@Service
public class JsonAndImageSaverService implements FileSaverService {

    @Override
    public void saveFiles(StoriesRequestDto storiesRequestDto){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String directory = "/content-maker/backend/src/main/resources/data";
            String fileName = "stories.json";
            mapper.writeValue(new File(directory, fileName), storiesRequestDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
