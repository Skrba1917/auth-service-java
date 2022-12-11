package com.example.AuthService.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RegisterDTO {


	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]{1,30}",message = "Invalide username format")
	private String username;
	@Pattern(regexp="^[a-zA-Z0-9]{8}", message="The password is invalid")
	private String password;
	@NotBlank
	private String sex;

	private int age;
	@NotBlank
	private String city;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	private String role;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public RegisterDTO() {
		
	}
	

}
