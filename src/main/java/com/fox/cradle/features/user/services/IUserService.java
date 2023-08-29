package com.fox.cradle.features.user.services;

import com.fox.cradle.features.user.models.AppUser;

public interface IUserService
{
     AppUser addUser(AppUser appUser);

     AppUser deleteUser(long id);

     AppUser getUserById(long id);

     AppUser putUser(long id, AppUser newAppUserData);

     AppUser getUserByUsername(String username);
}
