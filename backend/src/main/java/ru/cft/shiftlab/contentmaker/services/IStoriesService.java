package ru.cft.shiftlab.contentmaker.services;

import ru.cft.shiftlab.contentmaker.dto.StoriesRequestDto;

public interface IStoriesService {

    void saveJson(StoriesRequestDto storiesRequestDto);
}
