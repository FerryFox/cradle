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

    public AppUser addUser(AppUser appUser)
    {
        return _userRepository.save(appUser);
    }

    public AppUser deleteUser(long id)
    {
        AppUser appUser = _userRepository.findById(id).orElse(null);
        if (appUser != null)
        {
            _userRepository.delete(appUser);
        }
        return appUser;
    }

    public AppUser getUserById(long id)
    {
        return _userRepository.findById(id).orElse(null);
    }

        public AppUser putUser(long id, AppUser newAppUserData)
    {
        AppUser existingAppUser = _userRepository.findById(id).orElse(null);
        if (existingAppUser != null)
        {
            existingAppUser.setUsername(newAppUserData.getUsername());
            existingAppUser.setPassword(newAppUserData.getPassword());
            _userRepository.save(existingAppUser);
        }
        return existingAppUser;
    }

    public AppUser getUserByUsername(String username)
    {
        return _userRepository.findByUsername(username).orElse(null);
    }

    private void addMockData()
    {
        AppUser appUser = new AppUser();
        appUser.setUsername("Bob");
        appUser.setPassword("1234");

        _userRepository.save(appUser);
    }
}
