package com.smart.dao;

import com.smart.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    ProductType findProductTypeById(long id);
}
