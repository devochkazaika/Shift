package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.StoryPresentation;

import java.util.List;

@Repository
public interface StoryPresentationRepository extends CrudRepository<StoryPresentation, Long> {
    @EntityGraph(attributePaths = "storyPresentationFrames")
    @Query("SELECT st FROM StoryPresentation st WHERE st.bankId = :bank AND st.platform = :platform")
    List<StoryPresentation> getUnApprovedStories(@Param("bank") String bankId, @Param("platform") String platform);
}
