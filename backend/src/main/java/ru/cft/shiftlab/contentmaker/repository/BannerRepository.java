package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cft.shiftlab.contentmaker.entity.Banner;

public interface BannerRepository extends CrudRepository<Banner, Long> {
}
