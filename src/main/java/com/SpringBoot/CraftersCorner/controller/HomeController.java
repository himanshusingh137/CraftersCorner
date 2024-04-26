package com.SpringBoot.CraftersCorner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.SpringBoot.CraftersCorner.entity.Product;
import com.SpringBoot.CraftersCorner.global.GlobalData;
import com.SpringBoot.CraftersCorner.service.CategoryService;
import com.SpringBoot.CraftersCorner.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
    private	CategoryService categoryService;
	@Autowired
	private ProductService productService;
	
	@GetMapping( {"/" , "/home"} )
	public String home(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size()); 
		return "index";
	}
	
	@GetMapping( "/shop")
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProducts());
		model.addAttribute("cartCount", GlobalData.cart.size()); 
		return "shop";
	}

	@GetMapping("/shop/category/{id}")
	public String shopByCategory(@PathVariable int id , Model model) {
		List<Product> allProductsByCategoryId = productService.getAllProductsByCategoryId(id);
		
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", allProductsByCategoryId);
		model.addAttribute("cartCount", GlobalData.cart.size()); 
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}") 
	public String viewProduct(@PathVariable long id , Model model) {  
		model.addAttribute("product", productService.getProductById(id).get());
		model.addAttribute("cartCount", GlobalData.cart.size()); 
		return "viewProduct";
	}
	
	
}
