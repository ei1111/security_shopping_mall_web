package com.web.global.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 요청한 /images/** 경로를 실제 폴더 경로 /Users/your-username/upload/로 매핑
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/Users/manjae/Documents/code/spring_projdct/web_project/upload/"); // MacOS에서 파일 경로
    }
}
