package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.History;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {
}
