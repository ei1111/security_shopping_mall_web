package com.web.global.config.security;

import com.web.global.config.sql.QueryCountInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final QueryCountInterceptor queryCountInterceptor;

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

    /* 모든 경로를 기준으로 queryCountInterceptor를 중간에 넣어라 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(queryCountInterceptor).addPathPatterns("/**");
    }
}
