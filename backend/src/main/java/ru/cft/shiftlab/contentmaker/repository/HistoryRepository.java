package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.HistoryEntity;

import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<HistoryEntity, Long> {
    @Query(value = "select his from HistoryEntity his where his.componentId = :id and his.componentType = 'STORIES' " +
            "ORDER BY his.day DESC, his.time DESC")
    List<HistoryEntity> getHistoryByStoryId(@Param("id") Long id);

    @Query("select his from HistoryEntity his where his.bankId = :bank and his.platform = :platform and his.componentType = 'STORIES' " +
            "ORDER BY his.day DESC, his.time DESC")
    List<HistoryEntity> getHistoryByBankAndPlatform(@Param("bank") String bank, @Param("platform") String platform);

    @Query("select his from HistoryEntity his where his.componentType = 'STORIES' " +
            "ORDER BY his.day DESC, his.time DESC")
    List<HistoryEntity> getAllHistory();

    @Modifying
    @Query("delete from HistoryEntity his where his.componentId = :id and his.componentType = 'STORIES'")
    void deleteByStoryId(Long id);

    @Query("select hist from HistoryEntity hist where hist.userName = :name and hist.status = 'SUCCESSFUL'")
    List<HistoryEntity> getRequestByUser(@Param("name") String name);

    @Query("select hist from HistoryEntity hist where hist.operationType= 'CREATE'")
    List<HistoryEntity> getCreateRequest();

    @Query("select hist.componentId, hist.secondComponentId from HistoryEntity hist where hist.bankId = :bank and hist.platform = :platform")
    List<List<Long>> getChangedStories(@Param("bank") String bank, @Param("platform") String platform);
}
