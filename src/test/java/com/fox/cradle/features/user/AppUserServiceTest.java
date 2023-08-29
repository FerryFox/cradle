package com.fox.cradle.features.user;

import com.fox.cradle.features.user.models.AppUser;
import com.fox.cradle.features.user.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AppUserServiceTest
{
    @Autowired
    private UserService _userService;

    private long addMockUserAndGetId()
    {
        AppUser appUser = new AppUser();
        appUser.setUsername("TestUser");
        appUser.setPassword("TestPassword");
        appUser = _userService.addUser(appUser);
        return appUser.getId();
    }

    @Test
    public void testAddUserReturnsValidId()
    {
        long id = addMockUserAndGetId();
        assertNotNull(id);
    }

    @Test public void deleteUser()
    {
        long Id = addMockUserAndGetId();

        AppUser deletedAppUser = _userService.deleteUser(Id);
        assertEquals(deletedAppUser.getId(), Id);
    }

    @Test public void getUserById()
    {
        long Id = addMockUserAndGetId();

        AppUser retrievedAppUser = _userService.getUserById(Id);
        assertEquals(retrievedAppUser.getId(), Id);
    }

    @Test public void putUser()
    {
        long id = addMockUserAndGetId();
        AppUser changedAppUser = new AppUser();
        changedAppUser.setUsername("ChangedUsername");
        changedAppUser.setPassword("ChangedPassword");

        AppUser updatedAppUser = _userService.putUser(id, changedAppUser);
        assertEquals(updatedAppUser.getUsername(), changedAppUser.getUsername());
    }
}
