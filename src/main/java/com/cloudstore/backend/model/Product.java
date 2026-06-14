package com.cloudstore.backend.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
