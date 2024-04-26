package com.SpringBoot.CraftersCorner.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.SpringBoot.CraftersCorner.entity.Role;
import com.SpringBoot.CraftersCorner.entity.User;
import com.SpringBoot.CraftersCorner.global.GlobalData;
import com.SpringBoot.CraftersCorner.repository.RoleRepo;
import com.SpringBoot.CraftersCorner.repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	
	@GetMapping("/login")
	public String loginGet() {
		GlobalData.cart.clear();
		return "login";
	}
	
	@GetMapping("/register")
	public String registerGet() {
		return "register";
	}
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user , HttpServletRequest request ) throws ServletException {
		String password = user.getPassword();                                       // we use Httpservletrequest to redirect user to application logged in we don't want that user have to login after register.
		user.setPassword(passwordEncoder.encode(password));
		List<Role> roles = new ArrayList();
		roles.add(roleRepo.findByName("ROLE_USER"));
		user.setRoles(roles);
		
		userRepo.save(user);
		
		// how to use request here
		request.login(user.getEmail(), password);
		return "redirect:/home";
	}

}
