package com.smart.dto;

import com.smart.entities.Author;
import com.smart.entities.Product;

import java.util.List;

public class AuthorDetailDto {
    private Author author;
    private List<Product> productList;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
