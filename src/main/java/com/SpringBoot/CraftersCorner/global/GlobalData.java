package com.SpringBoot.CraftersCorner.global;

import java.util.ArrayList;
import java.util.List;

import com.SpringBoot.CraftersCorner.entity.Product;

public class GlobalData {
	
	public static List<Product> cart;
	static {
		cart= new ArrayList<Product>();
	}

}
