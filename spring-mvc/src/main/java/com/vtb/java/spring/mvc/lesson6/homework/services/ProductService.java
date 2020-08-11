package com.vtb.java.spring.mvc.lesson6.homework.services;

import com.vtb.java.spring.mvc.lesson6.homework.models.Product;
import com.vtb.java.spring.mvc.lesson6.homework.repositories.ProductRepository;
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

    public void saveProduct(Product product) {
        productRepository.saveProduct(product);
    }

    public void saveProducts(List<Product> products) {
        productRepository.saveProducts(products);
    }

    public Product getProductById(long id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void deleteProductById(long id) {
        productRepository.deleteProductById(id);
    }
}
