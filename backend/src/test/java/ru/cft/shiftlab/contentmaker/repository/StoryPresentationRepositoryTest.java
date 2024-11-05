package ru.cft.shiftlab.contentmaker.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StoryPresentationRepositoryTest {

    private StoryPresentation storyPresentation = StoryPresentation.builder()
            .id(2L)
            .bankId("absolutbank")
            .platform("ALL PLATFORMS")
            .previewTitleColor("#FFFF")
            .previewUrl("https://url")
            .build();

    @Autowired
    private StoryPresentationRepository storyPresentationRepository;

    @Test
    public void StoryPresentationRepository_Insert_InsertValueWithIdOnly(){
        storyPresentationRepository.insert(3L);
        StoryPresentation st = storyPresentationRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("StoryPresentationRepository_Insert_InsertValueWithIdOnly"));
        Assertions.assertThat(st).isNotNull();
        Assertions.assertThat(st.getId()).isEqualTo(3L);
    }
}
