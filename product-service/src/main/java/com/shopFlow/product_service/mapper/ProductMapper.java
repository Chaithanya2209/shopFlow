package com.shopFlow.product_service.mapper;

import com.shopFlow.product_service.dto.ProductRequest;
import com.shopFlow.product_service.dto.ProductResponse;
import com.shopFlow.product_service.entity.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setStockQuantity(request.getStockQuantity());

        return product;
    }

    public ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setSku(product.getSku());
        response.setCreatedAt(product.getCreatedAt());

        return response;
    }
}
