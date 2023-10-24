package com.fox.cradle.features.appuser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class AppUserServiceTest
{
    @Autowired
    AppUserService appUserServiceTest;

}