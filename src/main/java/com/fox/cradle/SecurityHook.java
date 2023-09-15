package com.fox.cradle;

import com.fox.cradle.configuration.security.SecuritySender;
import com.fox.cradle.configuration.security.user.User;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityHook
{
    private final AppUserService appUserService;

    @EventListener
    public void handleSecuritySender(SecuritySender event)
    {
        User user = event.getUser();
        AppUser appUser = new AppUser();
        //may be handle user id
        appUser.setAppUserName(user.getFirstname());
        appUser.setAppUserEmail(user.getEmail());
        appUser.setReceiveNews(user.isReceiveNews());

        appUserService.saveAppUser(appUser);
    }
}
