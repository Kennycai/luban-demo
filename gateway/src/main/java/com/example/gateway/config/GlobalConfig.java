package com.example.gateway.config;

import com.example.gateway.MyGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {
    @Bean
    public MyGlobalFilter globalFilter() {
        return new MyGlobalFilter();
    }
}
