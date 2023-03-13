package com.smart.dao;

import com.smart.entities.Author;
import com.smart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorById(long id);
}
