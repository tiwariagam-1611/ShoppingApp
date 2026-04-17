package com.shoppingapp.dto.request;

import jakarta.validation.constraints.*;

public class UserRequestDto {
	
	@NotBlank(message = "First name is required")
    private String firstName;
	
	@NotBlank(message = "Last name is required")
    private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
    private String email;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be valid and have exactly 10 digits")
    private String phone;
	
	@NotBlank(message = "Role is required")
	@Pattern(regexp = "^(CUSTOMER|ADMIN)$", message = "Role must be either 'CUSTOMER' or 'ADMIN'")
    private String role;

    public UserRequestDto() {}

    public UserRequestDto(String firstName, String lastName, String email, String phone, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    // Getters & Setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
