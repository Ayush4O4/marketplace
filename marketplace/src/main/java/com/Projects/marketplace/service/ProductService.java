package com.Projects.marketplace.service;


import com.Projects.marketplace.dto.ProductRequestDto;
import com.Projects.marketplace.dto.ProductResponseDto;
import com.Projects.marketplace.entity.Categories;
import com.Projects.marketplace.entity.Products;
import com.Projects.marketplace.entity.User;
import com.Projects.marketplace.exception.CategoryNotFoundException;
import com.Projects.marketplace.exception.ProductNotFoundException;
import com.Projects.marketplace.exception.UnAuthorizedException;
import com.Projects.marketplace.repositories.CategoriesRepository;
import com.Projects.marketplace.repositories.ProductRepository;
import com.Projects.marketplace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

        product.setImageUrl(productRequestDto.getUrl());
        product.setQuantity(productRequestDto.getQuantity());
        product.setDescription(productRequestDto.getDescription());

        Products savedProduct=productRepository.save(product);
        return new ProductResponseDto(savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice(),savedProduct.getStatus()
            , savedProduct.getQuantity(),savedProduct.getImageUrl(),savedProduct.getCondition(),savedProduct.getCategory().getName()
        ,savedProduct.getOwner().getUsername(),savedProduct.getCreatedAt());

    }

    public List<ProductResponseDto> getAllProducts(){
        List<Products> products=productRepository.findAll();
        return products.stream()
                .map(product->new ProductResponseDto(product.getName(), product.getDescription(), product.getPrice(),product.getStatus()
                        , product.getQuantity(),product.getImageUrl(),product.getCondition(),product.getCategory().getName()
                        ,product.getOwner().getUsername(),product.getCreatedAt()))
                .toList();
    }

    public ProductResponseDto getProductById(Long id){
        Products product= productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("product not found"));
        return new ProductResponseDto(product.getName(), product.getDescription(), product.getPrice(),product.getStatus()
                , product.getQuantity(),product.getImageUrl(),product.getCondition(),product.getCategory().getName()
                ,product.getOwner().getUsername(),product.getCreatedAt());
    }

    public String deleteProductById(Long id){
        User currUser=getLoggedInUser();
        Products product=productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("product not found exception"));


        if(!currUser.getUsername().equals(product.getOwner().getUsername())){
            throw new UnAuthorizedException("UnAuthorized Access");
        }
        productRepository.deleteById(id);
        return "product deleted";
    }

    public ProductResponseDto updateProductById(Long id,ProductRequestDto productRequestDto){
        User currUser=getLoggedInUser();
        Products product=productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("product not found"));

        if(!currUser.getUsername().equals(product.getOwner().getUsername())){
            throw new UnAuthorizedException("UnAuthorized Access");
        }
        Categories category= categoriesRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(()->new CategoryNotFoundException("category not found"));
        product.setName(productRequestDto.getName());
        product.setCondition(productRequestDto.getCondition());
        product.setCategory(category);
        product.setOwner(currUser);
        product.setPrice(productRequestDto.getPrice());

        product.setImageUrl(productRequestDto.getUrl());
        product.setQuantity(productRequestDto.getQuantity());
        product.setDescription(productRequestDto.getDescription());

        Products savedProduct=productRepository.save(product);
        return new ProductResponseDto(savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice(),savedProduct.getStatus()
                , savedProduct.getQuantity(),savedProduct.getImageUrl(),savedProduct.getCondition(),savedProduct.getCategory().getName()
                ,savedProduct.getOwner().getUsername(),savedProduct.getCreatedAt());
    }
}
