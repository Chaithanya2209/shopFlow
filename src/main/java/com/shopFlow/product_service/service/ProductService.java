package com.shopFlow.product_service.service;

import com.shopFlow.product_service.entity.Product;
import com.shopFlow.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    public  ProductService(ProductRepository productRepository)
    {
         this.productRepository=productRepository;
    }

    public Product create(Product product)
    {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public  Product getProductById(Long id)
    {
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found with id ; "+id));
    }

    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }


}
