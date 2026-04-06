package com.Projects.marketplace.dto;


import com.Projects.marketplace.entity.ProductCondition;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "product name cannot be blank")
    private String name;
    @NotBlank(message = "description of product cannot be empty")
    private String description;
    @NotBlank(message = "image url cannot be empty")
    private String url;
    @DecimalMin(value = "0.1",message = "price must be greater than 0")
    @Digits(integer = 6,fraction = 2)
    @NotNull
    private BigDecimal price;
    @NotNull
    private ProductCondition condition;
    @NotNull
    @Min(1)
    private Integer quantity;
    @NotNull
    private Long categoryId;
}
