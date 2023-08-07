package com.back.PracaLicencjackaBackend.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Integer id;
	@NotEmpty(message = "Nazwa użytkownika nie może być pusta")
	private String username;
	@NotEmpty(message = "Numer telefonu nie może być pusty")
	@Pattern(regexp = "48[0-9]{9}", message = "Invalid mobile number")
	private String mobileNumber;
	@NotNull(message = "Hasło nie może być puste")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Wprowadź silne hasło, bez białych znaków i spacji. (co najmniej 8 znaków, w tym: 1 duża litera, 1 cyfra, 1 mała litera,1 znak specjalny)")
	private String password;
	@NotEmpty(message = "Email nie może być pusty")
	@Email(message = "Błędnie wprowadzony email")
	private String email;
	private String role;

}
