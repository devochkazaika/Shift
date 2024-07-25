package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;

import java.util.UUID;

public interface StoryPresentationFramesRepository extends CrudRepository<StoryPresentationFrames, UUID> {
}
