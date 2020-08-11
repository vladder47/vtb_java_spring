package com.vtb.java.spring.boot.lesson7.homework.controllers;

import com.vtb.java.spring.boot.lesson7.homework.models.Product;
import com.vtb.java.spring.boot.lesson7.homework.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public String getAllProducts(Model model) {
        model.addAttribute("frontProducts", productService.getAllProducts());
        return "all_products";
    }

    @GetMapping("/show_product/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        model.addAttribute("frontProduct", productService.getProductById(id));
        return "product_page";
    }

    @GetMapping("/add")
    public String showProductAddForm() {
        return "add_product_form";
    }

    @PostMapping("/add")
    public String addNewProduct(@ModelAttribute Product product) {
        productService.saveOrUpdateProduct(product);
        return "redirect:/products/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.removeProductById(id);
        return "redirect:/products/all";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("frontProduct", productService.getProductById(id));
        return "edit_product_form";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute Product modifiedProduct) {
        productService.saveOrUpdateProduct(modifiedProduct);
        return "redirect:/products/all";
    }
}
