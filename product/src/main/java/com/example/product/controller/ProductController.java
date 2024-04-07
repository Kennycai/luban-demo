package com.example.product.controller;

import com.example.commonutils.annotation.Roles;
import com.example.commonutils.dto.IResponse;
import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Roles(roles = {"USER"})
    @GetMapping("/product")
    public IResponse<Product> getProduct(@RequestParam Long id) {
        return IResponse.ok(productService.getProduct(id));
    }

    @Roles(roles = {"USER"})
    @GetMapping("/product/list")
    public IResponse<List<Product>> getProductList() {
        return IResponse.ok(productService.getProductList());
    }

    @Roles(roles = {"EDITOR"})
    @PostMapping("/product")
    public IResponse<Product> addProduct(@RequestBody ProductDto productDto) {
        return IResponse.ok(productService.addProduct(productDto));
    }

    @Roles(roles = {"EDITOR"})
    @PutMapping("/product")
    public IResponse<Void> updateProduct(@RequestBody ProductDto productDto) {
        productService.updateProduct(productDto);
        return IResponse.ok(null);
    }

    @Roles(roles = {"EDITOR"})
    @DeleteMapping("/product")
    public IResponse<Void> deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return IResponse.ok(null);
    }
}
