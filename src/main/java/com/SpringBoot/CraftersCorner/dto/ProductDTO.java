package com.SpringBoot.CraftersCorner.dto;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Long id;
	private String name;
	private int category_Id;  // category id not whole object
	private double price;
	private String description;
    private int quantityInStock;
    private String imageName;  

}
