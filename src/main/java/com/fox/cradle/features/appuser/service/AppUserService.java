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
        appUser.getMyStampCards().add(stampCard);
        appUserRepository.save(appUser);
        return appUser;
    }

    public AppUser save(AppUser appUser)
    {
        return appUserRepository.save(appUser);
    }
}
