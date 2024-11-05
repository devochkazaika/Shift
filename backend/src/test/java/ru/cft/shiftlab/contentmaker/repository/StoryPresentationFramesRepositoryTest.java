package ru.cft.shiftlab.contentmaker.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StoryPresentationFramesRepositoryTest {
    @Autowired
    private StoryPresentationFramesRepository repository;

    StoryPresentationFrames frame1 = StoryPresentationFrames.builder()
            .title("tittle test")
            .approved(true)
            .buttonText("button test")
            .buttonTextColor("#FFFF")
            .build();

    @BeforeEach
    public void init() {
        repository.deleteAll();
        frame1 = repository.save(frame1);
    }

    @Test
    public void StoryPresentationFramesRepositoryTest_DeleteById_void(){
        repository.deleteById(frame1.getId());

        var st = repository.findById(frame1.getId()).orElse(null);
        Assertions.assertNull(st);
    }
}
