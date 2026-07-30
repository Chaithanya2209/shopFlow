package com.shopFlow.product_service.service;

import com.shopFlow.product_service.dto.PagedResponse;
import com.shopFlow.product_service.dto.ProductRequest;
import com.shopFlow.product_service.dto.ProductResponse;
import com.shopFlow.product_service.entity.Product;
import com.shopFlow.product_service.exception.ProductNotFoundException;
import com.shopFlow.product_service.mapper.ProductMapper;
import com.shopFlow.product_service.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<ProductResponse> createBulk(List<ProductRequest> requests) {

        List<Product> productResponses = requests.stream()
                .map(productMapper::toEntity)
                .collect(Collectors.toList());

        List<Product> saved= productRepository.saveAll(productResponses);

        return saved.stream().map(productMapper::toResponse).collect(Collectors.toList());
    }

    public PagedResponse<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> content = productPage.getContent().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                content,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    public  ProductResponse getProductById(Long id)
    {
        Product product= productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found with id ; "+id));

        return productMapper.toResponse(product);
    }

    public void deleteProduct(Long id)
    {
        if(!productRepository.existsById(id))
        {
          throw new ProductNotFoundException("Product not found with id ; "+id);
        }
        productRepository.deleteById(id);
    }


}
