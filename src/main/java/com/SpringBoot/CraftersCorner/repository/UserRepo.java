package com.SpringBoot.CraftersCorner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SpringBoot.CraftersCorner.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findUserByEmail(String email);
}
