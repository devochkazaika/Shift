package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.cft.shiftlab.contentmaker.entity.Banner;

@Repository
public interface BannerRepository extends CrudRepository<Banner, Long> {
    @Query(value = "SELECT b FROM Banner where b.code = :code",
            nativeQuery = true)
    Banner findBannerByCode(@Param("code") String code);
}
