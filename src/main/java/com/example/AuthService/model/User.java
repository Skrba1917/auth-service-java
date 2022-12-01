package com.example.AuthService.model;

import static javax.persistence.DiscriminatorType.STRING;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.*;

@Entity
	@Inheritance(strategy=SINGLE_TABLE)
	@DiscriminatorColumn(name="type", discriminatorType=STRING)
	public class User {
		
		@javax.persistence.Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long Id;
		
		@Column(name="Name", unique=false, nullable=false)
		private String Name;
		
		@Column(name="Surname", unique=false, nullable=false)
		private String Surname;
		
		@Column(name="Sex", unique=false, nullable=false)
		private ESex Sex;
		
		@Column(name="Age", unique=false, nullable=false)
		private int Age;
		
		@Column(name="City", unique=false, nullable=false)
		private String City;
		
		@Column(name="Username", unique=false, nullable=false)
		private String Username;
		
		@Column(name="Password", unique=false, nullable=false)
		private String Password;

		public Long getId() {
			return Id;
		}

		public void setId(Long id) {
			Id = id;
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public String getSurname() {
			return Surname;
		}

		public void setSurname(String surname) {
			Surname = surname;
		}

		public ESex getSex() {
			return Sex;
		}

		public void setSex(ESex sex) {
			Sex = sex;
		}

		public int getAge() {
			return Age;
		}

		public void setAge(int age) {
			Age = age;
		}

		public String getCity() {
			return City;
		}

		public void setCity(String city) {
			City = city;
		}

		public String getUsername() {
			return Username;
		}

		public void setUsername(String username) {
			Username = username;
		}

		public String getPassword() {
			return Password;
		}

		public void setPassword(String password) {
			Password = password;
		}
		
		public User() {
			
		}
		
}

	
	
	
	