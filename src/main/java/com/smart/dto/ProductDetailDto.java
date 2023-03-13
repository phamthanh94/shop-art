package com.smart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
    private long id;
    private String name;
    private long productType;
    private String productTypeName;
    private long authorId;
    private String authorName;
    private String size;
    private String year;
    private String description;
    private String imageUrl;
    private String material;
}
