package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(Long id);

    List<Product> getProductList();

    Product addProduct(ProductDto productDto);

    void updateProduct(ProductDto productDto);

    void deleteProduct(Long id);
}
