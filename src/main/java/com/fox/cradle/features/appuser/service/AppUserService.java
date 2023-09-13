package com.fox.cradle.features.appuser.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stamp.model.StampCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService
{
    private final AppUserRepository appUserRepository;

    public AppUser addStampCardToUser(StampCard stampCard, AppUser appUser)
    {
        AppUser u = appUserRepository.findById(appUser.getId()).orElse(null);
        u.getMyStampCards().add(stampCard);
        appUserRepository.save(u);
        return u;
    }

    public AppUser save(AppUser appUser)
    {
        return appUserRepository.save(appUser);
    }

    public AppUser getAppUserById(long id)
    {
        return appUserRepository.findById(id).orElse(null);
    }
}
