package com.SpringBoot.CraftersCorner.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.CraftersCorner.dto.ProductDTO;
import com.SpringBoot.CraftersCorner.entity.Category;
import com.SpringBoot.CraftersCorner.entity.Product;
import com.SpringBoot.CraftersCorner.service.CategoryService;
import com.SpringBoot.CraftersCorner.service.ProductService;

@Controller
public class AdminController {

	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;

	@GetMapping("/admin")
	public String adminHome() {

		return "adminHome";
	}

	@GetMapping("/admin/categories")
	public String getCategories(Model model) {
		List<Category> allCategories = categoryService.getAllCategory();
		model.addAttribute("categories", allCategories);
		return "categories";
	}

	@GetMapping("/admin/categories/add")
	public String getCategoryAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}

	@PostMapping("/admin/categories/add")
	public String saveCategory(@ModelAttribute("category") Category category) { // this annotation reads the dorm data
																				// and bind it to a java object,
																				// additionally it will also make this
																				// java object available in the view as
																				// a model.
		categoryService.saveCategory(category);
		return "redirect:/admin/categories";
	} // same endpoints with different mapping type

	@GetMapping("/admin/categories/delete/{id}") // yahan par delete mapping use kyu nhi ki -> rest api ke case me koi
													// problem nhi he but jab view par work karte he to (form ) par get
													// ya post hi work karega so Request mapping ya get post use
													// karenge.
	public String deleteCategory(@PathVariable int id) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model model) {
		Optional<Category> categoryById = categoryService.findCategoryById(id);
		if (categoryById.isPresent()) {
			model.addAttribute("category", categoryById.get());
			return "categoriesAdd";
		} else {
			return "404";
		}
	}

	// product section

	@GetMapping("/admin/products")
	public String getProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@GetMapping("/admin/products/add")
	public String addProducts(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory()); // because we are entering the product
																			// category through drop down list
		return "productsAdd";
	}

	@PostMapping("/admin/products/add") // Ye post method he same endpoint ke sath save karne ke liye
	public String saveProducts(@ModelAttribute("productDTO") ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName) {
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setQuantityInStock(productDTO.getQuantityInStock());
		product.setCategory(categoryService.findCategoryById(productDTO.getCategory_Id()).get());

		// image ke do tukde one for database(image name) and 2 for productImages folder
		// (image)
		// (we should not store image name directly in db insted of this we should
		// generate uuid for that) but we are not going with that
        String imageUUID;
		if (!file.isEmpty()) {
				// Save the file to the productImages folder with the original filename
				imageUUID = file.getOriginalFilename();
				Path fileNameAndPath = Paths.get(uploadDir, imageUUID);  // This line constructs a Path object named fileNameAndPath, representing the path
				                                                         //where the uploaded file will be saved.
				try {
					Files.write(fileNameAndPath, file.getBytes());               
				} catch (IOException e) {
					e.printStackTrace();
				}

		}else {
			imageUUID= imgName;
		}
		// Save the file name to the database
		product.setImageName(imageUUID);
		productService.saveProduct(product);

		return "redirect:/admin/products";
	} 

	@GetMapping("/admin/product/delete/{id}")
	public String deleteProducts(@PathVariable Long  id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/update/{id}")
	public String updateProductGet(@PathVariable Long  id,Model model) {
		
		Product productById = productService.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(productById.getId());
		productDTO.setCategory_Id(productById.getCategory().getId());
		productDTO.setDescription(productById.getDescription());
		productDTO.setName(productById.getName());
		productDTO.setImageName(productById.getImageName());
		productDTO.setPrice(productById.getPrice());
		productDTO.setQuantityInStock(productById.getQuantityInStock());
      
		model.addAttribute("categories", categoryService.getAllCategory()); 
		model.addAttribute("productDTO", productDTO);
		return "productsAdd";
	}
}
