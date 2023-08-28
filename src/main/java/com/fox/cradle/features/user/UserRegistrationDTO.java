package com.fox.cradle.features.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO
{
    private String username;
    private String password;
    private String email;  // example of additional data
    // ... getters, setters, and validation annotations ...
}
