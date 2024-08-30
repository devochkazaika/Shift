package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;

import java.util.UUID;

@Repository
public interface StoryPresentationFramesRepository extends CrudRepository<StoryPresentationFrames, UUID> {
    @Query("DELETE FROM StoryPresentationFrames where id = :id")
    @Modifying
    void deleteById(@Param("id") UUID id);
}
