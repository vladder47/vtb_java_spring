package com.vtb.java.spring.boot.lesson7.homework.services;

import com.vtb.java.spring.boot.lesson7.homework.exceptions.ResourceNotFoundException;
import com.vtb.java.spring.boot.lesson7.homework.models.Product;
import com.vtb.java.spring.boot.lesson7.homework.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveOrUpdateProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Товар с таким id отсутствует в базе данных"));
    }

    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }
}
