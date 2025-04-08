package com.example.WITHUS.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profile_images/**") // URL에서 접근할 경로
                .addResourceLocations("file:///C:/community_uploads/profiles/"); // 실제 서버 경로
    }


}
