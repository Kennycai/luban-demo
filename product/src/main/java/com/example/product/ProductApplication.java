package com.example.product;

import com.example.commonutils.EnableGlobalExceptionHandler;
import com.example.commonutils.EnableRoleCheckHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableGlobalExceptionHandler
@EnableRoleCheckHandler
@EnableDiscoveryClient
public class ProductApplication {


	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}
}
