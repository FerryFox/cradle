package com.fox.cradle.features.appuser.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stamp.model.StampCard;
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

    public AppUser getAppUserByEmail(String email)
    {
        Optional<AppUser> AppUserBox = appUserRepository.findByEmail(email);
        if(AppUserBox.isPresent()) return AppUserBox.get();
        else return null;
    }

    public AppUser updateAppUser(AppUser appUser)
    {
        //not changing the password
        //not changing email
        //not changing the id
        AppUser oldAppUser = getAppUserById(appUser.getId());
        if(oldAppUser != null)
        {
            oldAppUser.setAppUserName(appUser.getAppUserName());
            oldAppUser.setReceiveNews(appUser.isReceiveNews());
            oldAppUser.setMyStampCards(appUser.getMyStampCards());
            return saveAppUser(oldAppUser);
        }
        else return null;
    }
}
