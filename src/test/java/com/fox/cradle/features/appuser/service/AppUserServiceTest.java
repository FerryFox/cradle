package com.fox.cradle.features.appuser.service;

import com.fox.cradle.features.appuser.model.AppUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppUserServiceTest
{
    @Autowired
    AppUserService appUserServiceTest;

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
        AppUser savedAppUser = appUserServiceTest.saveAppUser(appUser);

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
                .appUserEmail("w@w")
                .appUserName("dox")
                .receiveNews(true)
                .myStampCards(null)
                .additionalInfo(null)
                .build();

        AppUser savedAppUser = appUserServiceTest.saveAppUser(appUser);

        //When
        AppUser getUser = appUserServiceTest.getAppUserById(savedAppUser.getId());

        //Then
        Assertions.assertNotNull(getUser);
    }

    @Test
    void findUserByEmail()
    {
        //Given
        AppUser appUser = AppUser.builder()
                .appUserEmail("r@r")
                .appUserName("rox")
                .receiveNews(true)
                .myStampCards(null)
                .additionalInfo(null)
                .build();

        AppUser savedAppUser = appUserServiceTest.saveAppUser(appUser);

        //When
        AppUser getUser = appUserServiceTest.getAppUserById(savedAppUser.getId());

        //Then
        Assertions.assertNotNull(getUser);
    }
}