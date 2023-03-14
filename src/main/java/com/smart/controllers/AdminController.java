package com.smart.controllers;

import com.smart.dao.AuthorRepository;
import com.smart.dao.ProductRepository;
import com.smart.dao.ProductTypeRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Author;
import com.smart.entities.Product;
import com.smart.entities.ProductType;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.service.AuthorService;
import com.smart.service.CommonService;
import com.smart.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("")
public class AdminController {
    private final AuthorRepository authorRepository;

    private final ProductTypeRepository productTypeRepository;

    private final ProductRepository productRepository;

    private final CommonService commonService;

    private final AuthorService authorService;

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public AdminController(AuthorRepository authorRepository, ProductTypeRepository productTypeRepository, ProductRepository productRepository, CommonService commonService, AuthorService authorService, UserRepository userRepository) {
        this.authorRepository = authorRepository;
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.commonService = commonService;
        this.authorService = authorService;
        this.userRepository = userRepository;
    }


    @ModelAttribute
    public void addCommonData(Model model, Principal principal, HttpSession session) {
        try {
            commonService.addCommonData(model, principal);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
    }
    @GetMapping("admin/author/init-author")
    public String initAddAuthorForm(Model model, HttpSession session) {
        try {
            model.addAttribute("author", new Author());
            model.addAttribute("authors", authorService.findAll());
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        return "admin/add_author_form";
    }


    @RequestMapping(value = "admin/author/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, HttpSession session) {
        try {
            authorService.deleteById(id);
            CommonUtils.alertSuccess(session);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        return "admin/add_author_form";
    }

    @RequestMapping(value = "admin/author/update/{id}")
    public String updateAuthor(@PathVariable("id") Long id, HttpSession session, Model model) {
        try {
            Author author = authorRepository.findAuthorById(id);
            model.addAttribute("author", author);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        return "admin/update_author_form";
    }

    @PostMapping("admin/author/add-author")
    public String addAuthor(@ModelAttribute Author author,
                            @RequestParam("profileImage") MultipartFile multiPartFile,
                            HttpSession session, Model model) {
        try {
            if (!multiPartFile.isEmpty()) {
                Path path = CommonUtils.writeFile(multiPartFile);
                author.setImageUrl(path.getFileName().toString());
            } else {
                 author.setImageUrl("sidebar2.png");
            }
            authorService.save(author);
            //message success
            CommonUtils.alertSuccess(session);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        model.addAttribute("authors", authorService.findAll());
        return "admin/add_author_form";
    }



    @GetMapping("admin/product/init-product")
    public String initAddProductForm(Model model, HttpSession session) {
        try {
            model.addAttribute("product", new Product());
            model.addAttribute("productTypes", productTypeRepository.findAll());
            model.addAttribute("authors", authorRepository.findAll());
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        return "admin/add_product_form";
    }

    @GetMapping("admin/product/init-catalog-product")
    public String initAddCatalogProductForm(Model model, HttpSession session) {
        try {
            model.addAttribute("title", "Add Author");
            model.addAttribute("productType", new ProductType());
            model.addAttribute("productTypes", productTypeRepository.findAll());
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        return "admin/add_catalog_product_form";
    }

    @RequestMapping(value = "admin/product/type/delete/{id}")
    public String deleteProductType(@PathVariable("id") Long id, HttpSession session) {
        try {
            productTypeRepository.deleteById(id);
            CommonUtils.alertSuccess(session);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        return "admin/add_catalog_product_form";
    }

    @RequestMapping(value = "admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, HttpSession session) {
        try {
            productRepository.deleteById(id);
            CommonUtils.alertSuccess(session);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        return "redirect:/product/show-products/0";
    }

    @RequestMapping(value = "admin/product/update/{id}")
    public String initUpdateProduct(@PathVariable("id") Long id, HttpSession session, Model model) {
        try {
            commonService.initMenuDropList(model);
            Product product = productRepository.findProductById(id);
            model.addAttribute("product", product);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        return "admin/update_product_form";
    }

    @PostMapping("admin/product/add-catalog-product")
    public String addCatalogProduct(@ModelAttribute ProductType productType, HttpSession session, Model model) {
        try {
            productTypeRepository.save(productType);
            CommonUtils.alertSuccess(session);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        model.addAttribute("productTypes", productTypeRepository.findAll());
        return "admin/add_catalog_product_form";
    }


    @PostMapping("admin/product/add-product")
    public String addProduct(@ModelAttribute Product product,
                            @RequestParam("profileImage") MultipartFile multiPartFile,
                            HttpSession session, Model model) {
        try {
            if (!multiPartFile.isEmpty()) {
                Path path = CommonUtils.writeFile(multiPartFile);
                product.setImageUrl(path.getFileName().toString());
            } else {
                product.setImageUrl("sidebar2.png");
            }
            productRepository.save(product);
            //message success
            CommonUtils.alertSuccess(session);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
        return "admin/add_product_form";
    }

    @GetMapping("admin/product/show-products/{page}")
    public String showProducts(@PathVariable("page") Integer page, Model model, Principal principal) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdDate").descending());
        Page<Product> productList =  productRepository.findAll(pageable);
        model.addAttribute("productList", productList.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productList.getTotalPages());
        return "admin/show_product";
    }

    @GetMapping("admin/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "admin/signup";
    }

    @PostMapping("admin/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
                               @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
                               HttpSession session) {
        try {
            if (!agreement) {
                throw new Exception("You have not accepted the terms and conditions");
            }

            if(result1.hasErrors()) {
                model.addAttribute("user", user);
                return "admin/signup";
            }
            user.setRole("ROLE_ADMIN");
            //password encode
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went wrong !!" + e.getMessage(), "alert-danger"));
        }
        model.addAttribute("user", new User());
        return "admin/signup";

    }
}
