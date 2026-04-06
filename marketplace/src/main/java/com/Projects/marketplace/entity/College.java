package com.Projects.marketplace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collegeDomain")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class College {
    @Id
    @SequenceGenerator(name = "collegeDomain_seq"
    ,sequenceName = "collegeDomain_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "collegeDomain_seq")
    private Long id;
    private String domain;
}
