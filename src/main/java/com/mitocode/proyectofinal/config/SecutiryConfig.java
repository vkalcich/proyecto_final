package com.mitocode.proyectofinal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mitocode.proyectofinal.security.CustomUserDetailsService;
import com.mitocode.proyectofinal.security.JwtFilterRequest;

@EnableWebSecurity
public class SecutiryConfig extends WebSecurityConfigurerAdapter { 

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtFilterRequest jwtFilterRequest;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean()throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		  http.csrf().and()
          .headers().frameOptions().disable();
		  
		  http.csrf().disable()
          .authorizeRequests().antMatchers("/api/auth/*","/h2-console/**","/v2/api-docs",
                  "/configuration/ui",
                  "/swagger-resources/**",
                  "/configuration/security",
                  "/swagger-ui.html",
                  "/webjars/**").permitAll()
          .antMatchers(HttpMethod.GET, "/api/categories").hasAnyRole("USER", "ADMIN")
          .antMatchers(HttpMethod.GET, "/api/products").hasAnyRole("USER", "ADMIN")
          .antMatchers(HttpMethod.POST, "/api/categories").hasRole("ADMIN")
          .antMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
          .antMatchers(HttpMethod.PUT, "/api/categories").hasRole("ADMIN")
          .antMatchers(HttpMethod.PUT, "/api/products").hasRole("ADMIN")
          .antMatchers(HttpMethod.DELETE, "/api/categories").hasRole("ADMIN")
          .antMatchers(HttpMethod.DELETE, "/api/products").hasRole("ADMIN")
          .anyRequest().authenticated()
          .and().exceptionHandling()
          .and().sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		  
		http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
	}
}
