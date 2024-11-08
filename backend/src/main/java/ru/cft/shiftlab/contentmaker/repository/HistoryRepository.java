package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.History;

import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {
    @Query(value = "select his from History his where his.componentId = :id and his.componentType = 'STORIES' " +
            "ORDER BY his.day DESC, his.time DESC")
    List<History> getHistoryByStoryId(@Param("id") Long id);

    @Query("select his from History his where his.bankId = :bank and his.platform = :platform and his.componentType = 'STORIES' " +
            "ORDER BY his.day DESC, his.time DESC")
    List<History> getHistoryByBankAndPlatform(@Param("bank") String bank, @Param("platform") String platform);

    @Query("select his from History his where his.componentType = 'STORIES' " +
            "ORDER BY his.day DESC, his.time DESC")
    List<History> getAllHistory();

    @Modifying
    @Query("delete from History his where his.componentId = :id and his.componentType = 'STORIES'")
    void deleteByStoryId(Long id);

}
