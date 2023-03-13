package com.smart.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Name field is required !!")
	@Size(min = 2, max = 25, message = "min 2 and max 25")
	private String name;

	@NotBlank(message = "Password must be added")
	@Size(min = 4)
	private String password;

	@Column(length = 15)
	private String role;

	private String email;
	
}
