package com.fox.cradle.features.user.controllers;

import com.fox.cradle.features.user.services.UserService;
import com.fox.cradle.features.user.models.AppUser;
import com.fox.cradle.features.user.models.UserRegistrationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    private final UserService _userService;

    public UserController(UserService userService)
    {
        this._userService = userService;
    }

    //this is may be better as a GET request because it also sends ok status
    @GetMapping("/id/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable long id)
    {
        return  ResponseEntity.ok().body( _userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    public AppUser getUserByUsername(@PathVariable String username)
    {
        return _userService.getUserByUsername(username);
    }

    @PostMapping
    public UserRegistrationDTO addUser(@RequestBody UserRegistrationDTO user)
    {
        //Mapping
        AppUser newAppUser = new AppUser();
        newAppUser.setPassword(user.getPassword());
        newAppUser.setUsername(user.getUsername());
        newAppUser.setEmail(user.getEmail());

        _userService.addUser(newAppUser);
        return user;
    }

    @DeleteMapping("/{id}")
    public AppUser deleteUser(@PathVariable long id)
    {
        return _userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public AppUser putUser(@PathVariable long id, @RequestBody AppUser newAppUserData)
    {
        return _userService.putUser(id, newAppUserData);
    }
}
