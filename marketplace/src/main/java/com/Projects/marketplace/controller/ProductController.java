package com.Projects.marketplace.controller;

import com.Projects.marketplace.dto.ProductRequestDto;
import com.Projects.marketplace.dto.ProductResponseDto;
import com.Projects.marketplace.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@Valid @RequestBody ProductRequestDto productRequestDto){
        return productService.createProduct(productRequestDto);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable Long id){
        return productService.deleteProductById(id);
    }
    @PutMapping("/{id}")
    public ProductResponseDto updateProductById(@PathVariable Long id,@Valid @RequestBody ProductRequestDto productRequestDto){
        return productService.updateProductById(id,productRequestDto);
    }
}
