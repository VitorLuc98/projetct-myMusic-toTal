package com.ciandt.summit.bootcamp2022.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .excludePathPatterns(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v2/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/configuration/security");
    }
}
