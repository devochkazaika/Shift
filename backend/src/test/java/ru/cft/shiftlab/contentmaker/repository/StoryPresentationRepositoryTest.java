package ru.cft.shiftlab.contentmaker.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
            .bankId("absolutbank")
            .platform("ALL PLATFORMS")
            .previewTitleColor("#FFFF")
            .previewUrl("https://url")
            .approved(StoryPresentation.Status.APPROVED)
            .build();

    private StoryPresentation storyPresentation2 = StoryPresentation.builder()
            .bankId("absolutbank")
            .platform("ALL PLATFORMS")
            .previewTitleColor("#FFFF")
            .previewUrl("https://url")
            .approved(StoryPresentation.Status.NOTAPPROVED)
            .build();

    private StoryPresentation storyPresentation3 = StoryPresentation.builder()
            .bankId("absolutbank")
            .platform("ALL PLATFORMS")
            .previewTitleColor("#FFFF")
            .previewUrl("https://url")
            .approved(StoryPresentation.Status.DELETED)
            .build();

    private StoryPresentation storyPresentation4 = StoryPresentation.builder()
            .bankId("absolutbank")
            .platform("ANDROID")
            .previewTitleColor("#FFFF")
            .previewUrl("https://url")
            .approved(StoryPresentation.Status.NOTAPPROVED)
            .build();

    private StoryPresentation storyPresentation5 = StoryPresentation.builder()
            .bankId("test_bank")
            .platform("ALL PLATFORMS")
            .previewTitleColor("#FFFF")
            .previewUrl("https://url")
            .approved(StoryPresentation.Status.DELETED)
            .build();

    @BeforeEach
    public void init(){
        storyPresentationRepository.deleteAll();
        storyPresentation1 = storyPresentationRepository.save(storyPresentation1);
        storyPresentation2 = storyPresentationRepository.save(storyPresentation2);
        storyPresentation3 = storyPresentationRepository.save(storyPresentation3);
        storyPresentation4 = storyPresentationRepository.save(storyPresentation4);
        storyPresentation5 = storyPresentationRepository.save(storyPresentation5);
    }
    @AfterEach
    public void destruct(){
        storyPresentationRepository.deleteAll();
    }

    @Autowired
    private StoryPresentationRepository storyPresentationRepository;

    @Test
    public void StoryPresentationRepository_Insert_InsertValueWithIdOnly(){
        Long id = 1000L;
        storyPresentationRepository.insert(id);
        StoryPresentation st = storyPresentationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StoryPresentationRepository_Insert_InsertValueWithIdOnly"));
        Assertions.assertThat(st).isNotNull();
        Assertions.assertThat(st.getId()).isEqualTo(id);
    }

    @Test
    public void StoryPresentationRepository_GetUnApproved_ListStoryPresentation(){

        List<StoryPresentation> li = storyPresentationRepository.getUnApprovedStories();

        Assertions.assertThat(li).isNotNull();
        Assertions.assertThat(li.size()).isEqualTo(2);
        Assertions.assertThat(li.get(0).getId()).isEqualTo(storyPresentation2.getId());
        Assertions.assertThat(li.get(1).getId()).isEqualTo(storyPresentation4.getId());
    }

    @Test
    public void StoryPresentationRepository_GetUnApprovedByBankAndPlatform_ListStoryPresentation(){

        List<StoryPresentation> li = storyPresentationRepository.getUnApprovedStories(
                        storyPresentation2.getBankId(),
                        storyPresentation2.getPlatform()
                );

        Assertions.assertThat(li).isNotNull();
        Assertions.assertThat(li.size()).isEqualTo(1);
        Assertions.assertThat(li.get(0).getId()).isEqualTo(storyPresentation2.getId());
    }

    @Test
    public void StoryPresentationRepository_GetDeletedStories_ListStoryPresentation(){

        List<StoryPresentation> li = storyPresentationRepository.getDeletedStories();

        Assertions.assertThat(li).isNotNull();
        Assertions.assertThat(li.size()).isEqualTo(2);
        Assertions.assertThat(li.get(0).getId()).isEqualTo(storyPresentation3.getId());
        Assertions.assertThat(li.get(1).getId()).isEqualTo(storyPresentation5.getId());
    }
}
