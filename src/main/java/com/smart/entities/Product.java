package com.smart.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, name = "product_type")
    private long productType;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "author_id")
    private long authorId;

    @Column(nullable = false, name = "size")
    private String size;

    @Column(nullable = false, name = "year")
    private String year;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(nullable = false, name = "material")
    private String material;

    @Column(nullable = false, name = "created_date")
    @CreationTimestamp
    private Date createdDate;
}
