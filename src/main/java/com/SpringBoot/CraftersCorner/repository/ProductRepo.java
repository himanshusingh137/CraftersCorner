package com.SpringBoot.CraftersCorner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.CraftersCorner.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

	 List<Product> findAllProductsByCategoryId(int id);
}
