package com.example.product.service.impl;

import com.example.commonutils.exception.BusinessException;
import com.example.product.dao.ProductRepository;
import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getProductList() {
        // todo 分页
        return productRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Product addProduct(ProductDto productDto) {
        Product product = new Product();
        return productRepository.save(product);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProduct(ProductDto productDto) {
        Product product = getProduct(productDto.getId());
        if (product == null) {
            throw new BusinessException(BusinessException.FAILED, "product not found");
        }
        product.setName(productDto.getName());
        productRepository.save(product);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
