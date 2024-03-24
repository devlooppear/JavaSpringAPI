package com.javaApi.javaApi.config;

import com.javaApi.javaApi.middleware.AuthMiddleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthMiddleware authMiddleware;

    @Autowired
    public WebMvcConfig(AuthMiddleware authMiddleware) {
        this.authMiddleware = authMiddleware;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authMiddleware);
    }
}