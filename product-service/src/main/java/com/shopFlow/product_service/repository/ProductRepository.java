package com.shopFlow.product_service.repository;

import com.shopFlow.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProductRepository extends JpaRepository<Product,Long> {

}
