package com.example.AuthService.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



public class WebConfig implements WebMvcConfigurer {


	//Dodati https localhost:8443 i ovaj http:localhost:8443

	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**").allowedOrigins("http://localhost:4200");
	        registry.addMapping("/**").allowedOrigins("http://localhost:8083");
		 	registry.addMapping("/**").allowedOrigins("https://localhost:8443");
		 	registry.addMapping("/**").allowedOrigins("http://localhost:8443");
			 registry.addMapping("/**").allowedOrigins("http://api-gateway:8083");
	        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
	    }
}
