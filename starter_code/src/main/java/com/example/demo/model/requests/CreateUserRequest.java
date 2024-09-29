package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateUserRequest {

	@NotNull(message = "Username cannot be null")
	@NotEmpty(message = "Username is required")
	@JsonProperty
	private String username;

	@NotNull(message = "Password cannot be null")
	@NotEmpty(message = "Password is required")
	@JsonProperty
	private String password;

	@JsonProperty
	private String confirmPassword;


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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
