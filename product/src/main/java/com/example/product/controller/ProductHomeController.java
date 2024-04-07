package com.example.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductHomeController {
    @GetMapping("/product/home")
    public String home() {
        return "product_home";
    }
}
