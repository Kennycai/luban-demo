package com.example.uaa;

import com.example.commonutils.EnableGlobalExceptionHandler;
import com.example.commonutils.EnableRoleCheckHandler;
import com.example.uaa.config.FeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

@SpringBootApplication
@EnableLdapRepositories
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@EnableRoleCheckHandler
@EnableGlobalExceptionHandler
public class UaaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }

}
