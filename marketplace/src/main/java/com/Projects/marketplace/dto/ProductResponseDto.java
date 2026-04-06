package com.Projects.marketplace.dto;

import com.Projects.marketplace.entity.ProductCondition;
import com.Projects.marketplace.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private String name;
    private String description;
    private BigDecimal price;
    private ProductStatus status;
    private Integer quantity;
    private String url;
    private ProductCondition condition;
    private String categoryName;
    private String owner;
    private LocalDate createdAt;
}
