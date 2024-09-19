package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentationFrames;

import java.util.List;
import java.util.UUID;

@Repository
public interface StoryPresentationFramesRepository extends CrudRepository<StoryPresentationFrames, UUID> {
    @Query("DELETE FROM StoryPresentationFrames where id = :id")
    @Modifying
    void deleteById(@Param("id") UUID id);

    @Query("DELETE FROM StoryPresentationFrames where story.id = :id")
    @Modifying
    void deleteByStoryId(@Param("id") Long id);

    @Query("select st FROM StoryPresentationFrames st where st.story.id = :id")
    List<StoryPresentationFrames> getDeletedFramesFromStory(@Param("id") Long id);
}
