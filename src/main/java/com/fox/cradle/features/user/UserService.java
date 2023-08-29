package com.fox.cradle.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository _userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this._userRepository = userRepository;
        addMockData();
    }

    public User addUser(User user)
    {
        return _userRepository.save(user);
    }

    public User deleteUser(long id)
    {
        User user = _userRepository.findById(id).orElse(null);
        if (user != null)
        {
            _userRepository.delete(user);
        }
        return user;
    }

    public User getUserById(long id)
    {
        return _userRepository.findById(id).orElse(null);
    }

        public User putUser(long id, User newUserData)
    {
        User existingUser = _userRepository.findById(id).orElse(null);
        if (existingUser != null)
        {
            existingUser.setUsername(newUserData.getUsername());
            existingUser.setPassword(newUserData.getPassword());
            _userRepository.save(existingUser);
        }
        return existingUser;
    }

    public User getUserByUsername(String username)
    {
        return _userRepository.findByUsername(username).orElse(null);
    }

    private void addMockData()
    {
        User user = new User();
        user.setUsername("Bob");
        user.setPassword("1234");

        _userRepository.save(user);
    }
}
