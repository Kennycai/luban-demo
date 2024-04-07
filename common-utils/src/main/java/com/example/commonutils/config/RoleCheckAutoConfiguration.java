package com.example.commonutils.config;

import com.example.commonutils.EnableGlobalExceptionHandler;
import com.example.commonutils.EnableRoleCheckHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConditionalOnClass(EnableRoleCheckHandler.class)
public class RoleCheckAutoConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("RoleCheckAutoConfiguration.addInterceptors");
        registry.addInterceptor(roleCheckInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public RoleCheckInterceptor roleCheckInterceptor() {
        return new RoleCheckInterceptor();
    }
}