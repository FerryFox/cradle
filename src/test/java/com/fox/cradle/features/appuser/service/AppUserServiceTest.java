package com.fox.cradle.features.appuser.service;

import com.fox.cradle.AbstractMongoDBIntegrationTest;
import com.fox.cradle.features.appuser.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserServiceTest extends AbstractMongoDBIntegrationTest
{
    @Autowired
    AppUserService appUserService;

    @Test
    void saveAppUser()
    {
        //Given
        AppUser appUser = AppUser.builder()
                .appUserEmail("q@q")
                .appUserName("fox")
                .receiveNews(true)
                .myStampCards(null)
                .additionalInfo(null)
                .build();

        //When
        AppUser savedAppUser = appUserService.saveAppUser(appUser);

        //Then
        Assertions.assertNotNull(savedAppUser);
        Assertions.assertNotNull(savedAppUser.getId());
        Assertions.assertEquals(savedAppUser.getAppUserEmail(), appUser.getAppUserEmail());
        Assertions.assertEquals(savedAppUser.getAppUserName(), appUser.getAppUserName());
    }

    @Test
    void getAppUserById()
    {
        //Given
        AppUser appUser = AppUser.builder()
                .appUserEmail("q@q")
                .appUserName("fox")
                .receiveNews(true)
                .myStampCards(null)
                .additionalInfo(null)
                .build();

        AppUser savedAppUser = appUserService.saveAppUser(appUser);

        //When
        AppUser getUser = appUserService.getAppUserById(savedAppUser.getId());

        //Then
        Assertions.assertNotNull(getUser);
        Assertions.assertEquals ("q@q",getUser.getAppUserEmail());
        Assertions.assertEquals ("fox",getUser.getAppUserName());
    }

    @Test
    void findUserByEmail()
    {
        //Given
        AppUser appUser = AppUser.builder()
                .appUserEmail("q@q")
                .appUserName("fox")
                .receiveNews(true)
                .myStampCards(null)
                .additionalInfo(null)
                .build();

        AppUser savedAppUser = appUserService.saveAppUser(appUser);

        //When
        AppUser getUser = appUserService.getAppUserById(savedAppUser.getId());

        //Then
        Assertions.assertNotNull(getUser);
        Assertions.assertEquals ("q@q",getUser.getAppUserEmail());
        Assertions.assertEquals ("fox",getUser.getAppUserName());
    }
}