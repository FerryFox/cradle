package com.fox.cradle.configuration.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull(message = "Name is required")
    @Size(max = 30, message = "Name should not be longer than 30 characters")
    private String firstname;

    @NotNull(message = "Email is required")
    @Email(message = "Valid email is required")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private boolean receiveNews;
}
