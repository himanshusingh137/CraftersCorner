package com.SpringBoot.CraftersCorner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.CraftersCorner.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
