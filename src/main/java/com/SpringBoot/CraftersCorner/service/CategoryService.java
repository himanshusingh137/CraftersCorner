package com.SpringBoot.CraftersCorner.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SpringBoot.CraftersCorner.entity.Category;
import com.SpringBoot.CraftersCorner.repository.CategoryRepo;

@Service
public class CategoryService {
 
	@Autowired
	private CategoryRepo categoryRepo;
	
	public void saveCategory(Category category) {
		categoryRepo.save(category);
	}
	
	public List<Category> getAllCategory(){
		return categoryRepo.findAll();
	}
	
	public void removeCategoryById(int id) {
		categoryRepo.deleteById(id); 
	}
	public Optional<Category> findCategoryById(int id) {
		Optional<Category> category  = categoryRepo.findById(id);
		return category;
	}
}
