package com.fox.cradle.features.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO
{
        private String username;
        private String password;
        // ... getters, setters, and validation annotations ...
}
