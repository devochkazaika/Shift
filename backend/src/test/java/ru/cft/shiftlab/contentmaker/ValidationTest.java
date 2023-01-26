package ru.cft.shiftlab.contentmaker;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.shiftlab.contentmaker.dto.StoriesDto;
import ru.cft.shiftlab.contentmaker.dto.StoryFramesDto;
import ru.cft.shiftlab.contentmaker.validation.Impl.StoriesTitleTextValidator;
import ru.cft.shiftlab.contentmaker.validation.Impl.StoryTitleValidator;
import ru.cft.shiftlab.contentmaker.validation.StoriesMultipleTitleTextValid;
import ru.cft.shiftlab.contentmaker.validation.StoryTitleValid;

import static org.junit.Assert.assertTrue;


@ExtendWith(MockitoExtension.class)
public class ValidationTest {
    @Mock
    StoryTitleValid storyTitleValid;

    @Mock
    StoriesMultipleTitleTextValid storiesMultipleTitleTextValid;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void validate_StoriesRequestDtoHasAllCorrectData() {
        StoryFramesDto storyFramesDto = new StoryFramesDto();
        StoriesDto storiesDto = new StoriesDto();

        storyFramesDto.setTitle("Online converter");
        storyFramesDto.setText("Convert value");
        storyFramesDto.setTextColor("FFFFFF");
        storyFramesDto.setPictureUrl(new byte[] {65, 65, 65});
        storyFramesDto.setLinkText("link text");
        storyFramesDto.setLinkUrl("link url");
        storyFramesDto.setButtonVisible(true);
        storyFramesDto.setButtonText("Попробовать");
        storyFramesDto.setButtonTextColor("FFFFFF");
        storyFramesDto.setButtonBackgroundColor("FFFFFF");
        storyFramesDto.setButtonUrl("button url");
        storyFramesDto.setGradient("HALF");

        storiesDto.setPreviewTitle("Conv money");
        storiesDto.setPreviewTitleColor("FFFFFF");
        storiesDto.setPreviewUrl(new byte[]{65, 65, 65});
        storiesDto.setPreviewGradient("FULL");

        storiesDto.addStoryFramesDto(storyFramesDto);

        StoryTitleValidator storyTitleValidator = new StoryTitleValidator();
        storyTitleValidator.initialize(storyTitleValid);

        StoriesTitleTextValidator storiesTitleTextValidator = new StoriesTitleTextValidator();
        storiesTitleTextValidator.initialize(storiesMultipleTitleTextValid);

        boolean resultOfStoryTitleValidator = storyTitleValidator.isValid(storiesDto, constraintValidatorContext);
        boolean resultOfStoriesTitleTextValidator = storiesTitleTextValidator.isValid(storyFramesDto, constraintValidatorContext);

        assertTrue(resultOfStoryTitleValidator && resultOfStoriesTitleTextValidator);
    }
}