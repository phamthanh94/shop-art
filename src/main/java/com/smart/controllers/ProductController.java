package com.smart.controllers;

import com.smart.dao.ProductRepository;
import com.smart.dto.ProductDetailDto;
import com.smart.entities.Product;
import com.smart.entities.ProductType;
import com.smart.service.CommonService;
import com.smart.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommonService commonService;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal, HttpSession session) {
        try {
            commonService.addCommonData(model, principal);
        } catch (Exception e) {
            CommonUtils.alertWarn(session);
        }
    }

    @GetMapping(value = "/product-list/{type}")
    public String getListProduct(@PathVariable("type") Long type,
                                 Model model) {
        commonService.initMenuDropList(model);
//        Pageable pageable = PageRequest.of(page, 10);
        List<Product> productList = productRepository.findProductsByType(type);
        model.addAttribute("productList", productList);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", productList.getTotalPages());
        ProductType productType = commonService.findProductTypeById(type);
        model.addAttribute("productType", productType);
        return "product_list";
    }

    @GetMapping("/product-detail/{id}")
    public String productDetails(@PathVariable("id") Long id, Model model) {
        commonService.initMenuDropList(model);
        ProductDetailDto detailDto = commonService.getProductDetailById(id);
        model.addAttribute("product", detailDto);
        return "product_detail";
    }

}
