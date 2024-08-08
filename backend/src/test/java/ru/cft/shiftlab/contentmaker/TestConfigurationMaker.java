package ru.cft.shiftlab.contentmaker;

import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.cft.shiftlab.contentmaker.repository.BannerRepository;
import ru.cft.shiftlab.contentmaker.util.Story.DtoToEntityConverter;

@TestConfiguration
public class TestConfigurationMaker {
    private BannerRepository bannerRepository;
    private BankRepository bankRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Bean
    public DtoToEntityConverter employeeService() {
        return new DtoToEntityConverter(bannerRepository, bankRepository, modelMapper);
    }
}