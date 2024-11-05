//package ru.cft.shiftlab.contentmaker;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;
//import ru.cft.shiftlab.contentmaker.util.StoryMapper;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class StoryMapperTest {
//    @Autowired
//    private StoryMapper storyMapper;
//
//
//    StoryPresentation st = StoryPresentation
//            .builder()
//            .id(15L)
//            .approved(StoryPresentation.Status.APPROVED)
//            .previewTitleColor("#FFFF")
//            .platform("ALL PLATFORMS")
//            .bankId("testBank")
//            .build();
//
//    @Test
//    public void putStoryToJson_successful_test() {
//        storyMapper.putStoryToJson(st, st.getBankId(), st.getPlatform());
//
//    }
//}
