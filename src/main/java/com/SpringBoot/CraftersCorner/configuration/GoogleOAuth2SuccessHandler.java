package com.SpringBoot.CraftersCorner.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.SpringBoot.CraftersCorner.entity.Role;
import com.SpringBoot.CraftersCorner.entity.User;
import com.SpringBoot.CraftersCorner.repository.RoleRepo;
import com.SpringBoot.CraftersCorner.repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	UserRepo userRepo;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();  // use to redirect  internally

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		// when google authenticate ,oauth token is in authentication
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		String email = token.getPrincipal().getAttributes().get("email").toString();
		if(userRepo.findUserByEmail(email).isPresent()) {
			
		}else {
			User user= new User();
			user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
			user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
			user.setEmail(email);
			//password not needed in google auth
			List<Role> roles = new ArrayList<>();
			roles.add(roleRepo.findByName("ROLE_USER"));  // case sensitive
			user.setRoles(roles);
			
			userRepo.save(user);
			
		}
		
		redirectStrategy.sendRedirect(request, response, "/home");
	}
	
}
