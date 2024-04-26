package com.SpringBoot.CraftersCorner.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.SpringBoot.CraftersCorner.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	@Autowired
	CustomUserDetailService customUserDetailService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// CSRF configuration
				.csrf(csrf -> csrf.disable() // This is optional: use disable() if you want to explicitly disable CSRF
												// protection
				).authorizeHttpRequests(auth -> {
					auth.requestMatchers("/", "/home", "/shop/**", "/register").permitAll().requestMatchers("/admin/**")
							.hasRole("ADMIN").anyRequest().authenticated();
				}).formLogin(form -> {
					form.loginPage("/login").permitAll().failureUrl("/login?error=true")
							.defaultSuccessUrl("/home", true) // true means always redirect to "/home"
							.usernameParameter("email").passwordParameter("password"); // Optional: Specify custom
																						// password parameter
				}).oauth2Login(oauth -> {
					oauth.loginPage("/login").successHandler(googleOAuth2SuccessHandler);
				}).logout(logout -> {
					logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
							.invalidateHttpSession(true).deleteCookies("JSESSIONID");
				});

//				// Exception handling configuration
//				.exceptionHandling(ex -> ex.accessDeniedPage("/access-denied") // Specify your access denied page
//
//				);

		return http.build();

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	 @Bean
//	    AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
//	        return auth.userDetailsService(customUserDetailService);
//	    }
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
		auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder()); // Ensure you also set the
																								// password encoder if
																								// you are using one

		return auth.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/images/**",
				"/productImages/**");
	}

}
