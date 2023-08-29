package com.fox.cradle.features.user;

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

    @GetMapping("/id/{id}")
    public AppUser getUserById(@PathVariable long id)
    {
        return _userService.getUserById(id);
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
