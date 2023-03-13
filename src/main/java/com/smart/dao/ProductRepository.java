package com.smart.dao;

import com.smart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductByAuthorId(Long authorId);
    @Query("from Product c where c.productType =:productType")
    List<Product> findProductsByType(@Param("productType") long productType);

    Product findProductById(long id);
}
