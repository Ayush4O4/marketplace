package com.Projects.marketplace.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    @Id
    @SequenceGenerator(
            name = "product_seq",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_seq")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "condition",nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCondition condition;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @Column(name = "imageUrl",nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "owner_id",nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Categories category;

    @Column(name = "createdAt",nullable = false)
    private LocalDate createdAt;


    @PrePersist
    void setDefaultValues(){
        this.createdAt=LocalDate.now();
        this.status=ProductStatus.AVAILABLE;
    }



}
