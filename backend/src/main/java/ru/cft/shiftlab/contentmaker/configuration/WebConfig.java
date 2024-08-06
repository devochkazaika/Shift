package ru.cft.shiftlab.contentmaker.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.cft.shiftlab.contentmaker.service.StoriesInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private StoriesInterceptor storiesInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(storiesInterceptor).addPathPatterns("/stories/**");
    }
}
