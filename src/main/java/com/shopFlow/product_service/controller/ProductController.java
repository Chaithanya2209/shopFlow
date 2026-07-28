package com.shopFlow.product_service.controller;

import com.shopFlow.product_service.dto.ProductRequest;
import com.shopFlow.product_service.dto.ProductResponse;
import com.shopFlow.product_service.service.ProductService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody  ProductRequest product)
    {
        ProductResponse created = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts()
    {
        List<ProductResponse>products =  productService.getAllProducts();

        return  ResponseEntity.status(HttpStatus.OK).body(products);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {

        ProductResponse fetch= productService.getProductById(id);

        return ResponseEntity.status(HttpStatus.OK).body(fetch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }



}
