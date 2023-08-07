package com.back.PracaLicencjackaBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy  = GenerationType.AUTO) 
	private int userId;
	@OneToOne(cascade = CascadeType.REMOVE)
	private Address address;
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
    private Cart cart;
	private String username;
	private String password;
	private String mobileNumber;
	private String email;
	private String role;
	private LocalDate dateOfCreation = LocalDate.now();
}
