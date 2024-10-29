package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.stories.StoryPresentation;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoryPresentationRepository extends JpaRepository<StoryPresentation, Long> {
    @Query(value = "SELECT * FROM stories WHERE approved = 'NOTAPPROVED'", nativeQuery = true)
    List<StoryPresentation> getUnApprovedStories();

    StoryPresentation findTopByOrderByIdDesc();

    @Query(value = "SELECT * FROM stories WHERE approved = 'NOTAPPROVED' and bank_id = :bank and platform = :platform", nativeQuery = true)
    List<StoryPresentation> getUnApprovedStories(@Param("bank") String bankId, @Param("platform") String platform);

    @Query(value = "SELECT * FROM stories WHERE approved = 'DELETED'", nativeQuery = true)
    List<StoryPresentation> getDeletedStories();

    @Query(value = "SELECT st FROM StoryPresentation st WHERE st.approved = 'DELETED' and st.bankId = :bank and st.platform = :platform")
    List<StoryPresentation> getDeletedStories(@Param("bank") String bankId, @Param("platform") String platform);

    @Query("SELECT st FROM StoryPresentation st WHERE st.id = :id")
    Optional<StoryPresentation> findById(@Param("id") Long id);

    @Query("Select st from StoryPresentation st where st.bankId = :bank and st.platform = :platform and st.approved = 'CHANGED'")
    List<StoryPresentation> getChangeRequest(@Param("bank") String bank, @Param("platform") String platform);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO stories (id) VALUES (:id)", nativeQuery = true)
    void insert(@Param("id") Long id);
}
