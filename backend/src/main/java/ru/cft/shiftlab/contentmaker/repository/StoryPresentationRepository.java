package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;

@Repository
public interface StoryPresentationRepository extends CrudRepository<StoryPresentation, Long> {
}
