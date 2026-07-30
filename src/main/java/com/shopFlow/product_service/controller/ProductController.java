package com.shopFlow.product_service.controller;

import com.shopFlow.product_service.dto.PagedResponse;
import com.shopFlow.product_service.dto.ProductRequest;
import com.shopFlow.product_service.dto.ProductResponse;
import com.shopFlow.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/bulk")
    public ResponseEntity<List<ProductResponse>> addProductBulk(@Valid @RequestBody  List<ProductRequest> product)
    {
        List<ProductResponse> created = productService.createBulk(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping
    public ResponseEntity<PagedResponse<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size)
    {
        Pageable  pageable= PageRequest.of(page, size);
        PagedResponse<ProductResponse>products =  productService.getAllProducts(pageable);

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
