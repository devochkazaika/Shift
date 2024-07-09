package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.shiftlab.contentmaker.entity.Bank;
import ru.cft.shiftlab.contentmaker.entity.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface BannerRepository extends CrudRepository<Banner, Long> {
    @Query(value = "SELECT b FROM Banner as b where b.code = :code")
    Optional<Banner> findBannerByCode(@Param("code") String code);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Banner b WHERE b.code = :code")
    void deleteBannerByCode(@Param("code") String code);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM test_banner WHERE code = (SELECT code FROM test_banner main WHERE main.code = :code)", nativeQuery = true)
    void deleteMainBannerByCode(@Param("code") String code);

    @Modifying
    @Query("UPDATE Banner b SET b.mainBanner.id = :mainCodeId WHERE b.id = :bannerId")
    void updateBannerByMainBanner(@Param("bannerId") Long bannerId,
                                  @Param("mainCodeId") Long mainCodeId);

    @Query(value = "SELECT b FROM Banner as b where b.bank = :bank")
    Optional<ArrayList<Banner>> findBannerByBank(@Param("bank") Bank bank);

    @Query(value = "SELECT b FROM Banner as b where b.bank = :bank and b.platformType = :platform")
    List<Banner> findBannerByBankAndPlatform(@Param("bank") Bank bank,
                                                            @Param("platform") String platform);


}
