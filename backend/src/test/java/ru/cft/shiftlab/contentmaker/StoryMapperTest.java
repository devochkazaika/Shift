package ru.cft.shiftlab.contentmaker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.exceptionhandling.StaticContentException;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.StoryMapper;

import java.io.IOException;
import java.util.Arrays;

import static ru.cft.shiftlab.contentmaker.util.Constants.FILES_SAVE_DIRECTORY;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class StoryMapperTest {
    @Autowired
    private StoryMapper storyMapper;

    @Autowired
    private DirProcess dirProcess;

    StoryPresentation st, st2, st3;

    @BeforeEach
    public void init() throws IOException {
        st = StoryPresentation
                .builder()
                .id(15L)
                .previewGradient("#FFFF")
                .previewTitleColor("#FFFF")
                .platform("ALL PLATFORMS")
                .bankId("testBank")
                .build();
        st2 = StoryPresentation
                .builder()
                .id(16L)
                .previewGradient("#DDDD")
                .previewTitleColor("#DDDD")
                .platform("ALL PLATFORMS")
                .bankId("testBank")
                .build();
        st3 = StoryPresentation
                .builder()
                .id(17L)
                .previewGradient("#AAAA")
                .previewTitleColor("#CCCC")
                .previewTitle("new Title")
                .platform("ALL PLATFORMS")
                .bankId("testBank")
                .build();
        dirProcess.deleteFolders(FILES_SAVE_DIRECTORY);
    }

    @Test
    public void StoryMapperTest_PutStoryToJson_StoryPresentation() {
        storyMapper.putStoryToJson(st, st.getBankId(), st.getPlatform());

        var list = storyMapper.getStoryList(st.getBankId(), st.getPlatform());
        Assertions.assertEquals(list.size(), 1);
        var story = list.get(0);
        Assertions.assertAll(
                () -> Assertions.assertEquals(story.getPreviewGradient(), st.getPreviewGradient()),
                () -> Assertions.assertEquals(story.getPreviewTitleColor(), st.getPreviewTitleColor()),
                () -> Assertions.assertEquals(story.getBankId(), st.getBankId()),
                () -> Assertions.assertEquals(story.getPlatform(), st.getPlatform())
        );
    }

    @Test
    public void StoryMapperTest_PutStoryToJson_StaticContentException() {
        storyMapper.putStoryToJson(st, st.getBankId(), st.getPlatform());
        Assertions
                .assertThrows(
                        StaticContentException.class,
                        () -> storyMapper.putStoryToJson(st, st.getBankId(), st.getPlatform())
                );
    }

    @Test
    public void StoryMapperTest_PutListStoryToJson_StoryPresentation(){
        var listStories = Arrays.asList(st, st2, st3);

        storyMapper.putStoryToJson(listStories, st.getBankId(), st.getPlatform());

        var list = storyMapper.getStoryList(st.getBankId(), st.getPlatform());
        Assertions.assertEquals(list.size(), 3);
        var story = list.get(0);
        Assertions.assertAll(
                () -> Assertions.assertEquals(story.getId(), st.getId()),
                () -> Assertions.assertEquals(story.getPreviewGradient(), st.getPreviewGradient()),
                () -> Assertions.assertEquals(story.getPreviewTitleColor(), st.getPreviewTitleColor()),
                () -> Assertions.assertEquals(story.getBankId(), st.getBankId()),
                () -> Assertions.assertEquals(story.getPlatform(), st.getPlatform())
        );
        var story2 = list.get(1);
        Assertions.assertAll(
                () -> Assertions.assertEquals(story2.getId(), st2.getId()),
                () -> Assertions.assertEquals(story2.getPreviewGradient(), st2.getPreviewGradient()),
                () -> Assertions.assertEquals(story2.getPreviewTitleColor(), st2.getPreviewTitleColor()),
                () -> Assertions.assertEquals(story2.getBankId(), st2.getBankId()),
                () -> Assertions.assertEquals(story2.getPlatform(), st2.getPlatform())
        );
        var story3 = list.get(2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(story3.getId(), st3.getId()),
                () -> Assertions.assertEquals(story3.getPreviewGradient(), st3.getPreviewGradient()),
                () -> Assertions.assertEquals(story3.getPreviewTitleColor(), st3.getPreviewTitleColor()),
                () -> Assertions.assertEquals(story3.getBankId(), st3.getBankId()),
                () -> Assertions.assertEquals(story3.getPlatform(), st3.getPlatform())
        );
    }

    @Test
    public void StoryMapperTest_updateStoryEntity_StoryPresentation() {
       var story = storyMapper.updateStoryEntity(st, st3);
       Assertions.assertAll(
               () -> Assertions.assertEquals(story.getId(), st.getId()),
               () -> Assertions.assertEquals(story.getPreviewGradient(), st3.getPreviewGradient()),
               () -> Assertions.assertEquals(story.getPreviewTitle(), st3.getPreviewTitle()),
               () -> Assertions.assertEquals(story.getPreviewTitleColor(), st3.getPreviewTitleColor())
       );
    }
}
