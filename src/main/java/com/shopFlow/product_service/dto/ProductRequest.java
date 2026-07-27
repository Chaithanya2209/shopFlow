package com.shopFlow.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor @NoArgsConstructor @Data
public class ProductRequest {

    @NotBlank
    private  String name;

    private String description;

    @NotNull @Positive
    private BigDecimal price;

    @NotNull @PositiveOrZero
    private Integer stockQuantity;

    @NotBlank
    private String sku;

}
