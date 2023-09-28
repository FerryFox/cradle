package com.fox.cradle.features.appuser.service;

import com.fox.cradle.features.appuser.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService
{
    private final AppUserRepository appUserRepository;

    public AppUser saveAppUser(AppUser appUser)
    {
        return appUserRepository.save(appUser);
    }

    public AppUser getAppUserById(long id)
    {
        return appUserRepository.findById(id).orElse(null);
    }

    public Optional<AppUser> findUserByEmail(String email)
    {
        return appUserRepository.findByEmail(email);
    }
}
