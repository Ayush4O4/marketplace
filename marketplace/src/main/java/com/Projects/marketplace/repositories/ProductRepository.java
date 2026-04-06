package com.Projects.marketplace.repositories;

import com.Projects.marketplace.entity.Categories;
import com.Projects.marketplace.entity.Products;
import com.Projects.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products,Long> {

    List<Products> findByOwner(User owner);

    List<Products> findByCategory(Categories category);

    List<Products> findByNameContainingIgnoreCase(String name);
}
