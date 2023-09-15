package com.fox.cradle.configuration.security.auth;

import com.fox.cradle.features.appuser.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String email;
    private String password;
    private boolean receiveNews;
}
