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

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoriesRepository categoriesRepository;
    private final CloudinaryService cloudinaryService;

    private User getLoggedInUser(){
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
       return  userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("username not found"));

    }
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) throws IOException {
       Categories category= categoriesRepository.findById(productRequestDto.getCategoryId())
               .orElseThrow(()->new CategoryNotFoundException("category not found"));

        String imageUrl=cloudinaryService.uploadImage(productRequestDto.getImage());

       User user=getLoggedInUser();
        Products product=new Products();
        product.setName(productRequestDto.getName());
        product.setCondition(productRequestDto.getCondition());
        product.setCategory(category);
        product.setOwner(user);
        product.setPrice(productRequestDto.getPrice());

        product.setImageUrl(imageUrl);
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

    public ProductResponseDto updateProductById(Long id,ProductRequestDto productRequestDto) throws IOException {
        User currUser=getLoggedInUser();
        Products product=productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("product not found"));

        if(!currUser.getUsername().equals(product.getOwner().getUsername())){
            throw new UnAuthorizedException("UnAuthorized Access");
        }

        if(productRequestDto.getName()!=null){
            product.setName(productRequestDto.getName());
        }
        if(productRequestDto.getCondition()!=null){
            product.setCondition(productRequestDto.getCondition());
        }
        if(productRequestDto.getPrice()!=null){
            product.setPrice(productRequestDto.getPrice());
        }
        if(productRequestDto.getImage()!=null && !productRequestDto.getImage().isEmpty()){
            String imageUrl=cloudinaryService.uploadImage(productRequestDto.getImage());
            product.setImageUrl(imageUrl);
        }
        if(productRequestDto.getQuantity()!=null){
            product.setQuantity(productRequestDto.getQuantity());
        }
        if(productRequestDto.getDescription()!=null){
            product.setDescription(productRequestDto.getDescription());
        }
        if(productRequestDto.getCategoryId()!=null){
            Categories category= categoriesRepository.findById(productRequestDto.getCategoryId())
                    .orElseThrow(()->new CategoryNotFoundException("category not found"));
            product.setCategory(category);
        }



        Products savedProduct=productRepository.save(product);
        return new ProductResponseDto(savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice(),savedProduct.getStatus()
                , savedProduct.getQuantity(),savedProduct.getImageUrl(),savedProduct.getCondition(),savedProduct.getCategory().getName()
                ,savedProduct.getOwner().getUsername(),savedProduct.getCreatedAt());
    }
}
