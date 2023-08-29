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
    public User getUserById(@PathVariable long id)
    {
        return _userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username)
    {
        return _userService.getUserByUsername(username);
    }

    @PostMapping
    public UserRegistrationDTO addUser(@RequestBody UserRegistrationDTO user)
    {
        //Mapping
        User newUser = new User();
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());

        _userService.addUser(newUser);
        return user;
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable long id)
    {
        return _userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User putUser(@PathVariable long id, @RequestBody User newUserData)
    {
        return _userService.putUser(id, newUserData);
    }
}
