package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentationFrames;

import java.util.UUID;

@Repository
public interface StoryPresentationFramesRepository extends CrudRepository<StoryPresentationFrames, UUID> {
}
