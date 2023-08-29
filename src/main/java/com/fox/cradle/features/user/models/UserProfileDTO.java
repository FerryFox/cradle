package com.fox.cradle.features.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO
{
    private Long id;
    private String username;
    private String email;
    // ... other fields, getters, setters ...
}
