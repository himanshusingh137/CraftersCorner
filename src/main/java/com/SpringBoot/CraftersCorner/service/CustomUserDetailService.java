package com.SpringBoot.CraftersCorner.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SpringBoot.CraftersCorner.entity.CustomUserDetails;
import com.SpringBoot.CraftersCorner.entity.User;
import com.SpringBoot.CraftersCorner.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findUserByEmail(email);
		user.orElseThrow(()-> new UsernameNotFoundException("User not found"));
		return user.map(CustomUserDetails :: new ).get();
	}

}
