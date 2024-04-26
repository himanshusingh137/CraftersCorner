package com.SpringBoot.CraftersCorner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.CraftersCorner.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

	Role findByName(String role_name);
}
