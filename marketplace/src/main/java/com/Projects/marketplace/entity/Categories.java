package com.Projects.marketplace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categories {

    @Id
    @SequenceGenerator(
            name = "categories_seq",
            sequenceName = "categories_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "categories_seq")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Products> products;

}
