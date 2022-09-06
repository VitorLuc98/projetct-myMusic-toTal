package com.ciandt.summit.bootcamp2022.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns(
                        "/swagger-ui/**",
                        "/v2/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/configuration/security");
    }
}
