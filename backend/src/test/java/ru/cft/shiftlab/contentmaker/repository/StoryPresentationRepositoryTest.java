package ru.cft.shiftlab.contentmaker.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StoryPresentationRepositoryTest {

    private StoryPresentation storyPresentation1 = StoryPresentation.builder()
            .id(1L)
            .bankId("absolutbank")
            .platform("ALL PLATFORMS")
            .previewTitleColor("#FFFF")
            .previewUrl("https://url")
            .approved(StoryPresentation.Status.APPROVED)
            .build();

    private StoryPresentation storyPresentation2 = StoryPresentation.builder()
            .id(2L)
            .bankId("absolutbank")
            .platform("ALL PLATFORMS")
            .previewTitleColor("#FFFF")
            .previewUrl("https://url")
            .approved(StoryPresentation.Status.NOTAPPROVED)
            .build();

    private StoryPresentation storyPresentation3 = StoryPresentation.builder()
            .id(3L)
            .bankId("absolutbank")
            .platform("ALL PLATFORMS")
            .previewTitleColor("#FFFF")
            .previewUrl("https://url")
            .approved(StoryPresentation.Status.APPROVED)
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

    @Test
    public void StoryPresentationRepository_GetUnApproved_ListStoryPresentation(){
        storyPresentationRepository.save(storyPresentation1);
        storyPresentationRepository.save(storyPresentation2);
        storyPresentationRepository.save(storyPresentation3);
        List<StoryPresentation> li = storyPresentationRepository.getUnApprovedStories();
        Assertions.assertThat(li).isNotNull();
        Assertions.assertThat(li.size()).isEqualTo(1);
        Assertions.assertThat(li.contains(storyPresentation2)).isTrue();
    }
}
