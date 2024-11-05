package ru.cft.shiftlab.contentmaker.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
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

    @Autowired
    private StoryPresentationRepository storyPresentationRepository;

    @Test
    public void StoryPresentationRepository_Insert_InsertValueWithIdOnly(){
        Long id = 1000L;
        storyPresentationRepository.insert(id);
        StoryPresentation st = storyPresentationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StoryPresentationRepository_Insert_InsertValueWithIdOnly"));
        Assertions.assertNotNull(st);
        Assertions.assertEquals(st.getId(), id);
    }

    @Test
    public void StoryPresentationRepository_GetUnApproved_ListStoryPresentation(){

        List<StoryPresentation> li = storyPresentationRepository.getUnApprovedStories();

        Assertions.assertNotNull(li);
        Assertions.assertEquals(li.size(), 2);
        Assertions.assertEquals(li.get(0).getId(), storyPresentation2.getId());
        Assertions.assertEquals(li.get(1).getId(), storyPresentation4.getId());
    }

    @Test
    public void StoryPresentationRepository_GetUnApprovedByBankAndPlatform_ListStoryPresentation(){

        List<StoryPresentation> li = storyPresentationRepository.getUnApprovedStories(
                        storyPresentation2.getBankId(),
                        storyPresentation2.getPlatform()
                );

        Assertions.assertNotNull(li);
        Assertions.assertEquals(li.size(), 1);
        Assertions.assertEquals(li.get(0).getId(), storyPresentation2.getId());
    }

    @Test
    public void StoryPresentationRepository_GetDeletedStories_ListStoryPresentation(){

        List<StoryPresentation> li = storyPresentationRepository.getDeletedStories();

        Assertions.assertNotNull(li);
        Assertions.assertEquals(li.size(), 2);
        Assertions.assertEquals(li.get(0).getId(), storyPresentation3.getId());
        Assertions.assertEquals(li.get(1).getId(), storyPresentation5.getId());
    }

    @Test
    public void StoryPresentationRepository_GetDeletedStoriesByBankAndPlatform_ListStoryPresentation(){

        List<StoryPresentation> li = storyPresentationRepository
                .getDeletedStories(
                        storyPresentation3.getBankId(),
                        storyPresentation3.getPlatform()
                );

        Assertions.assertNotNull(li);
        Assertions.assertEquals(li.size(), 1);
        Assertions.assertEquals(li.get(0).getId(), storyPresentation3.getId());
    }

    @Test
    public void findById(){
        Long id = 132L;
        storyPresentationRepository.insert(132L);
        StoryPresentation story = StoryPresentation.builder()
                .id(132L)
                .bankId("test_bank")
                .platform("ALL PLATFORMS")
                .previewTitleColor("#FFFF")
                .build();
        storyPresentationRepository.save(story);

        StoryPresentation st = storyPresentationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StoryPresentationRepository_FindById"));

        Assertions.assertNotNull(st);
        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertEquals(st.getId(), story.getId()),
                () -> Assertions.assertEquals(st.getPlatform(), story.getPlatform()),
                () -> Assertions.assertEquals(st.getBankId(), story.getBankId()),
                () -> Assertions.assertEquals(st.getPreviewUrl(), story.getPreviewUrl())
        );
    }
}
