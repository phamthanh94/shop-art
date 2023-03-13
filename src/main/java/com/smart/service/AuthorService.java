package com.smart.service;

import com.smart.dao.AuthorRepository;
import com.smart.dao.ProductRepository;
import com.smart.dto.AuthorDetailDto;
import com.smart.entities.Author;
import com.smart.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ProductRepository productRepository;

    public AuthorService(AuthorRepository authorRepository, ProductRepository productRepository) {
        this.authorRepository = authorRepository;
        this.productRepository = productRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    public void save(Author author) {
        authorRepository.save(author);
    }

    public AuthorDetailDto getAuthorDetail(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        AuthorDetailDto dto = new AuthorDetailDto();
        if (authorOptional.isPresent()) {
            //author info
            Author author = authorOptional.get();
            dto.setAuthor(author);

            //get list product
            List<Product> productList = productRepository.findProductByAuthorId(author.getId());
            dto.setProductList(productList);
            return dto;
        }
        return null;
    }
}
