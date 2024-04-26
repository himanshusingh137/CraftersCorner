package com.SpringBoot.CraftersCorner.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.CraftersCorner.entity.Product;
import com.SpringBoot.CraftersCorner.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	public List<Product> getAllProducts(){
		return productRepo.findAll();
	}
	
	public void saveProduct(Product product) {
		productRepo.save(product);
	}
	
	public void removeProductById(Long id) {
		productRepo.deleteById(id);
	}
	public Optional<Product> getProductById(Long id) {
		Optional<Product> product = productRepo.findById(id);
		return product;
	}

	public List<Product> getAllProductsByCategoryId(int id) {
		return productRepo.findAllProductsByCategoryId(id);
	}
}
