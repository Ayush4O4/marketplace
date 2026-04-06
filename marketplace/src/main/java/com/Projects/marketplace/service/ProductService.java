package com.Projects.marketplace.service;


import com.Projects.marketplace.dto.ProductRequestDto;
import com.Projects.marketplace.dto.ProductResponseDto;
import com.Projects.marketplace.entity.Categories;
import com.Projects.marketplace.entity.Products;
import com.Projects.marketplace.entity.User;
import com.Projects.marketplace.exception.CategoryNotFoundException;
import com.Projects.marketplace.exception.ProductNotFoundException;
import com.Projects.marketplace.repositories.CategoriesRepository;
import com.Projects.marketplace.repositories.ProductRepository;
import com.Projects.marketplace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoriesRepository categoriesRepository;

    private User getLoggedInUser(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
       return  userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("username not found"));

    }
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto){
       Categories category= categoriesRepository.findById(productRequestDto.getCategoryId())
               .orElseThrow(()->new CategoryNotFoundException("category not found"));
       User user=getLoggedInUser();
        Products product=new Products();
        product.setName(productRequestDto.getName());
        product.setCondition(productRequestDto.getCondition());
        product.setCategory(category);
        product.setOwner(user);
        product.setPrice(productRequestDto.getPrice());
        product.setCondition(productRequestDto.getCondition());
        product.setImageUrl(productRequestDto.getUrl());
        product.setQuantity(productRequestDto.getQuantity());
        product.setDescription(productRequestDto.getDescription());

        Products savedProduct=productRepository.save(product);
        return new ProductResponseDto(savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice(),savedProduct.getStatus()
            , savedProduct.getQuantity(),savedProduct.getImageUrl(),savedProduct.getCondition(),savedProduct.getCategory().getName()
        ,savedProduct.getOwner().getUsername(),savedProduct.getCreatedAt());

    }
}
