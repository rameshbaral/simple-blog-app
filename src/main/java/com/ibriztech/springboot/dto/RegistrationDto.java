package com.ibriztech.springboot.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
    private Long id;
    @NotEmpty(message = "First Name should not be empty")
    private String firstName;
    @NotEmpty(message = "Last Name should not be empty")
    private String lastName;
    @NotEmpty(message = "Email Should Not be empty or null")
    @Email
    private String email;
    @NotEmpty(message = "password should not be empty")
    private String password;
}
