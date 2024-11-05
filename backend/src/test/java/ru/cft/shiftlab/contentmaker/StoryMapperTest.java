package ru.cft.shiftlab.contentmaker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
import ru.cft.shiftlab.contentmaker.util.DirProcess;
import ru.cft.shiftlab.contentmaker.util.StoryMapper;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
public class StoryMapperTest {
    @Autowired
    private StoryMapper storyMapper;

    @Autowired
    private DirProcess dirProcess;

    @BeforeEach
    public void init(){

    }

    StoryPresentation st = StoryPresentation
            .builder()
            .id(15L)
            .approved(StoryPresentation.Status.APPROVED)
            .previewTitleColor("#FFFF")
            .platform("ALL PLATFORMS")
            .bankId("testBank")
            .build();

    @Test
    public void putStoryToJson_successful_test() {
        storyMapper.putStoryToJson(st, st.getBankId(), st.getPlatform());

    }
}
