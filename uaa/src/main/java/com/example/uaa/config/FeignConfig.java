package com.example.uaa.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;

@Configuration
public class FeignConfig {

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(this.messageConverters()));
    }

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(this.messageConverters());
    }

    private ObjectFactory<HttpMessageConverters> messageConverters() {
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(
                new FormHttpMessageConverter());
        return () -> httpMessageConverters;
    }
}
