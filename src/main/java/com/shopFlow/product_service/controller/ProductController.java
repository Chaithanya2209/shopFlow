package com.shopFlow.product_service.controller;

import com.shopFlow.product_service.entity.Product;
import com.shopFlow.product_service.repository.ProductRepository;
import com.shopFlow.product_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController( ProductService productService)
    {

        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody  Product product)
    {
        Product created = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product>products =  productService.getAllProducts();

        return  ResponseEntity.status(HttpStatus.OK).body(products);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {

        Product fetch= productService.getProductById(id);

        return ResponseEntity.status(HttpStatus.OK).body(fetch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }



}
