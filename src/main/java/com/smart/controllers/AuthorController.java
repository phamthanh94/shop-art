package com.smart.controllers;

import com.smart.dto.AuthorDetailDto;
import com.smart.service.AuthorService;
import com.smart.service.CommonService;
import com.smart.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class AuthorController {

    private final CommonService commonService;
    private final AuthorService authorService;

    public AuthorController(CommonService commonService, AuthorService authorService) {
        this.commonService = commonService;
        this.authorService = authorService;
    }

    @RequestMapping(value = "{id}/author-detail")
    public String getAuthorDetail(@PathVariable("id") Long id, HttpSession session, Model model) {
     commonService.initMenuDropList(model);
        AuthorDetailDto detailDto = authorService.getAuthorDetail(id);
        if (detailDto != null) {
            model.addAttribute("author", detailDto.getAuthor());
            model.addAttribute("productList", detailDto.getProductList());
            return "author_detail";
        } else {
            CommonUtils.alertWarn(session);
            return "redirect:/home";
        }
    }
}
