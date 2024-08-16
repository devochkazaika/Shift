package ru.cft.shiftlab.contentmaker;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.cft.shiftlab.contentmaker.util.WhiteList;

@SpringBootApplication
@EnableConfigurationProperties
@EnableWebMvc
public class ContentMakerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ContentMakerApplication.class, args);
	}

	@Bean
	public WhiteList whitelist() {
		return new WhiteList();
	}

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
