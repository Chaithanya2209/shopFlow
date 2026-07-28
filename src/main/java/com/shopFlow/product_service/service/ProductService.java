package com.shopFlow.product_service.service;

import com.shopFlow.product_service.dto.ProductRequest;
import com.shopFlow.product_service.dto.ProductResponse;
import com.shopFlow.product_service.entity.Product;
import com.shopFlow.product_service.mapper.ProductMapper;
import com.shopFlow.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
     private final ProductMapper productMapper  ;

    private final ProductRepository productRepository;

    //Constructor
    public  ProductService(ProductRepository productRepository , ProductMapper productMapper)
    {
        this.productMapper=productMapper;
         this.productRepository=productRepository;
    }

    public ProductResponse create(ProductRequest productRequest)
    {

        Product product = productMapper.toEntity(productRequest);
        Product saved=productRepository.save(product);

        return productMapper.toResponse(saved);
    }

    public List<ProductResponse> getAllProducts()
    {

        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    return productResponses;
    }

    public  ProductResponse getProductById(Long id)
    {
        Product product= productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found with id ; "+id));

        return productMapper.toResponse(product);
    }

    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }


}
