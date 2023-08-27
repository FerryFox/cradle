package com.fox.cradle.features.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest
{
    @Autowired
    private UserService _userService;

    private long addMockUserAndGetId()
    {
        User user = new User();
        user.setUsername("TestUser");
        user.setPassword("TestPassword");
        user  = _userService.addUser(user);
        return user.getId();
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

        User deletedUser = _userService.deleteUser(Id);
        assertEquals(deletedUser.getId(), Id);
    }

    @Test public void getUserById()
    {
        long Id = addMockUserAndGetId();

        User retrievedUser = _userService.getUserById(Id);
        assertEquals(retrievedUser.getId(), Id);
    }

    @Test public void putUser()
    {
        long id = addMockUserAndGetId();
        User changedUser  = new User();
        changedUser.setUsername("ChangedUsername");
        changedUser.setPassword("ChangedPassword");

        User updatedUser = _userService.putUser(id, changedUser);
        assertEquals(updatedUser.getUsername(), changedUser.getUsername());
    }
}
