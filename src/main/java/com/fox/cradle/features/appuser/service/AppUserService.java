package com.fox.cradle.features.appuser.service;

import com.fox.cradle.features.appuser.UserMapService;
import com.fox.cradle.features.appuser.model.AddInfoDTO;
import com.fox.cradle.features.appuser.model.AdditionalInfo;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.picture.service.PictureService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService
{
    private final AppUserRepository appUserRepository;
    private final PictureService pictureService;
    private final UserMapService userMapService;

    public AppUser saveAppUser(AppUser appUser)
    {
        //initialize additional info for user
        AdditionalInfo addInfo = new AdditionalInfo();
        addInfo.setAppUser(appUser);
        appUser.setAdditionalInfo(addInfo);

        return appUserRepository.save(appUser);
    }

    public Optional<AppUser> findUserByEmail(String email)
    {
        return appUserRepository.findByEmail(email);
    }

    @Transactional
    public AddInfoDTO getAdditionalInfo(AppUser appUser)
    {
        AdditionalInfo info = appUser.getAdditionalInfo();

        return userMapService.mapAdditionalInfoToDTO(info);
    }

    @Transactional
    public void updateAdditionalInfo(AppUser appUser, AddInfoDTO info)
    {
        AdditionalInfo oldInfo = appUser.getAdditionalInfo();
        oldInfo.setBio(info.getBio());
        oldInfo.setStatus(info.getStatus());
        oldInfo.setConnection(info.getConnection());

        //update picture or save new one
        if (oldInfo.getPictureId() != null)
        {
            pictureService.updatePicutre(oldInfo.getPictureId(), info.getPicture());
        }
        else
        {
            String pictureId = pictureService.savePicture(
                            info.getPicture(),
                            appUser.getAppUserName())
                            .getId();
            oldInfo.setPictureId(pictureId);
        }
        appUserRepository.save(appUser);
    }

    public List<AppUserDTO> getFriends(AppUser appUser)
    {
        List<AppUser> friends = appUser.getFriends();

        List<AppUserDTO> friendsDTO = userMapService.mapAppUserListToDTO(friends);
        return friendsDTO;
    }

    public void addFriend(Long userId, Long friendId) {
        // 1. Fetch both users
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        AppUser friend = appUserRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + friendId));

        // 2. & 3. Update the list and ensure bidirectionality
        if (!user.getFriends().contains(friend)) {
            user.getFriends().add(friend);
            friend.getFriends().add(user);
        }

        // 4. Save the updated entities
        appUserRepository.save(user);
        appUserRepository.save(friend);
    }

    public List<AppUserDTO> getUsers()
    {
        List<AppUser> users = appUserRepository.findAll();
        return userMapService.mapAppUserListToDTO(users);
    }
}
