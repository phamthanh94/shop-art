package com.smart.controllers;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	

	//method for adding common data to the response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		//principal will give current logged in user name
		String userName = principal.getName();
		System.out.println(userName);
		
		//get user from db
		User user = this.userRepository.getUserByUserName(userName);
		System.out.println(user);
		model.addAttribute("user", user);
	}

	@RequestMapping("/home")
	public String home(Model model, Principal principal) {
		return "normal/user_dashboard";
	}
	
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {	
		model.addAttribute("title", "User Dashboard");
		return "normal/user_dashboard";
	}

	
	//profile page handler
	@GetMapping("/profile")
	public String yourProfilePage(Model model) {
		model.addAttribute("title", "Profile Page");
		return "normal/profile";
	}
}
