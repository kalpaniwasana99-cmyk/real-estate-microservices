 package com.realestate.notification_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {

    @Autowired
    private ApiKeyIntercepter apiKeyIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyIntercepter)
                .addPathPatterns("/api/notify/**")
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**");
    }
}