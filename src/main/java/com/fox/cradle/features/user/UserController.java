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

    @PostMapping
    public User addUser(@RequestBody User user)
    {
        return _userService.addUser(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id)
    {
        return _userService.getUserById(id);
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
