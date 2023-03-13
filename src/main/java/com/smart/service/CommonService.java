package com.smart.service;

import com.smart.dao.AuthorRepository;
import com.smart.dao.ProductRepository;
import com.smart.dao.ProductTypeRepository;
import com.smart.dao.UserRepository;
import com.smart.dto.ProductDetailDto;
import com.smart.entities.Author;
import com.smart.entities.Product;
import com.smart.entities.ProductType;
import com.smart.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
public class CommonService {
    private final AuthorRepository authorRepository;
    private final ProductTypeRepository productTypeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CommonService(AuthorRepository authorRepository,
                         ProductTypeRepository productTypeRepository,
                         UserRepository userRepository,
                         ProductRepository productRepository) {
        this.authorRepository = authorRepository;
        this.productTypeRepository = productTypeRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void initMenuDropList(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("productTypes", productTypeRepository.findAll());
    }

    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        //get user from db
        User user = this.userRepository.getUserByUserName(userName);
        model.addAttribute("user", user);
    }

    public ProductDetailDto getProductDetailById(long id) {
        Product product = productRepository.findProductById(id);
        if (product != null) {
            Author author = authorRepository.findAuthorById(product.getAuthorId());
            ProductType productType = productTypeRepository.findProductTypeById(product.getProductType());
            //map data
            ProductDetailDto detailDto = new ProductDetailDto();
            detailDto.setId(product.getId());
            detailDto.setName(product.getName());
            detailDto.setImageUrl(product.getImageUrl());
            detailDto.setSize(product.getSize());
            detailDto.setYear(product.getYear());
            detailDto.setMaterial(product.getMaterial());
            //author
            if (author != null) {
                detailDto.setAuthorId(author.getId());
                detailDto.setAuthorName(author.getName());
            }
            //productType
            if (productType != null) {
                detailDto.setProductType(productType.getId());
                detailDto.setProductTypeName(productType.getName());
            }
            return detailDto;
        }
        return null;
    }

    public ProductType findProductTypeById(long id) {
        return productTypeRepository.findProductTypeById(id);
    }
}
