package com.fox.cradle.features.user.services;

import com.fox.cradle.features.user.models.AppUser;
import com.fox.cradle.features.user.models.Role;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @Transactional @Slf4j
public class UserService implements IUserService
{
    private final UserRepository _userRepository;
    private final RoleRepository _roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository)
    {
        this._userRepository = userRepository;
        this._roleRepository = roleRepository;
        addMockData();
    }

    public AppUser addUser(AppUser appUser)
    {
        log.info("Adding user: " + appUser.getUsername());
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
        appUser.setEmail("test@cool.com");

        Role role = new Role();
        role.setName("ROLE_USER");
        _roleRepository.save(role);
        appUser.getRoles().add(role);

        Role role2 = new Role();
        role2.setName("ROLE_ADMIN");
        _roleRepository.save(role2);
        appUser.getRoles().add(role2);

        _userRepository.save(appUser);
    }
}
