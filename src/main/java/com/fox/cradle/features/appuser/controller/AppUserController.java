package com.fox.cradle.features.appuser.controller;

import com.fox.cradle.configuration.security.jwt.JwtService;
import com.fox.cradle.features.appuser.model.AddInfoDTO;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.appuser.service.AppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AppUserController
{
    private final AppUserService appUserService;
    private final JwtService jwtService;

    @GetMapping("/my-additional-info")
    public ResponseEntity<AddInfoDTO> getAdditionalInfo(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();
        AddInfoDTO result = appUserService.getAdditionalInfo(appUser.get());

        return ResponseEntity.ok(result);
    }

    //load user data
    //with  - additional info
    //      - friends
    @GetMapping("/me")
    public ResponseEntity<AppUserDTO> getMe(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();

        AppUserDTO result = appUserService.getMeDTO(appUser.get());

        return ResponseEntity.ok(result);
    }

    //Get user data with
    //  -   additional info
    //  -   templates
    //  -   friends
    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getUser(@PathVariable String id)
    {
        AppUserDTO result = appUserService.getUserDTO(id);

        if (result == null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(result);
    }

    @Transactional
    @GetMapping("/friends")
    public ResponseEntity<List<AppUserDTO>> getFriends(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));
        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();

        List<AppUserDTO> friends = appUserService.getFriends(appUser.get());

        return ResponseEntity.ok(friends);
    }

    //return friend
    @PostMapping("/add-friend/{id}")
    public ResponseEntity<AppUserDTO> addFriend(HttpServletRequest httpServletRequest, @PathVariable Long id)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));
        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();

        AppUserDTO friend = appUserService.addFriend(appUser.get().getId(), id);

        return ResponseEntity.ok(friend);
    }

    @PostMapping("/additional-info")
    public ResponseEntity<AddInfoDTO> saveAdditionalInfo(@RequestBody AddInfoDTO info, HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();


        if(!appUser.get().getAdditionalInfo().getId().equals(info.getId()))
            return ResponseEntity.badRequest().build();

        appUserService.updateAdditionalInfo(appUser.get(), info);

        return ResponseEntity.ok(info);
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<AppUserDTO>> getUsers(HttpServletRequest httpServletRequest)
    {
        Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));

        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();

        List<AppUserDTO> users = appUserService.getUsers();

        List<Long> idsToRemove = appUser.get().getFriends().stream()
                .map(AppUser::getId)
                .collect(Collectors.toList());

        idsToRemove.add(appUser.get().getId());

        for (Long id : idsToRemove)
        {
            users.removeIf(user -> user.getId().equals(id));
        }

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete-friend/{friendId}")
    public ResponseEntity<Void> deleteFriend (HttpServletRequest httpServletRequest, @PathVariable Long friendId)
    {
    	Optional<AppUser> appUser =  appUserService.
                findUserByEmail(jwtService.extractUsernameFromRequest(httpServletRequest));
        if (appUser.isEmpty()) return ResponseEntity.badRequest().build();

        appUserService.deleteFriend(appUser.get(), friendId);

        return ResponseEntity.ok().build();
    }
}
