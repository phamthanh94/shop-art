package com.smart.controllers;

import com.smart.dao.ProductRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Product;
import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("")
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ProductRepository productRepository;


	@GetMapping("/")
	public String home(Model model) {
		commonService.initMenuDropList(model);
		Pageable pageable = PageRequest.of(0, 10);
		Page<Product> productList = productRepository.findAll(pageable);
		model.addAttribute("productList", productList);
		return "home";
	}

	@GetMapping("/contact")
	public String contact(Model model) {
		commonService.initMenuDropList(model);
		return "contact";
	}
	
	//handler for custom Login
	@GetMapping("/signin")
	public String customLogin(Model model, HttpSession session) {
		return "redirect:home";
	}

}
