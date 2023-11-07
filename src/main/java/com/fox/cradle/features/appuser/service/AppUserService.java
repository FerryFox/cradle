package com.fox.cradle.features.appuser.service;

import com.fox.cradle.features.appuser.UserMapService;
import com.fox.cradle.features.appuser.model.AddInfoDTO;
import com.fox.cradle.features.appuser.model.AdditionalInfo;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import com.fox.cradle.features.stampsystem.service.template.TemplateService;
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
    private final TemplateService templateService;

    static final String USER_NOT_FOUND_MSG = "User not found with id ";

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
        else if (info.getPicture() == null)
        {
            oldInfo.setPictureId(null);
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

    //load fiends user data
    //with  - additional info
    public List<AppUserDTO> getFriends(AppUser appUser)
    {
        List<AppUser> friends = appUser.getFriends();

        return userMapService.mapAppUserFriendsToDTOWithAddInfo(friends);
    }

    //return friend
    // - additional info

    // - friends empty
    // - templates null
    // - mails null
    public AppUserDTO addFriend(Long userId, Long friendId) {
        // 1. Fetch both users
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG+ userId));

        AppUser friend = appUserRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG + friendId));

        // 2. & 3. Update the list and ensure bidirectionality
        if (!user.getFriends().contains(friend))
        {
            user.getFriends().add(friend);
        }

        // 4. Save the updated entities
        appUserRepository.save(user);

        return userMapService.mapAppUserToDTOWithAddInfoAndFriends(friend);
    }

    public List<AppUserDTO> getUsers()
    {
        List<AppUser> users = appUserRepository.findAll();
        return userMapService.mapAppUserFriendsToDTOWithAddInfo(users);
    }


    //load user data
    //      - additional info
    //      - friends
    @Transactional
    public AppUserDTO getMeDTO(AppUser appUser)
    {
        appUser.getFriends();
        appUser.getAdditionalInfo();
        return userMapService.mapAppUserToDTOWithAddInfoAndFriends(appUser);
    }

    @Transactional
    public void deleteFriend(AppUser appUser, Long friendId)
    {
        appUser.getFriends().removeIf(friend -> friend.getId().equals(friendId));

        appUserRepository.save(appUser);
    }

    //Get user data with
    //  -   additional info
    //  -   templates
    //  -   friends
    @Transactional
    public AppUserDTO getUserDTO(String id)
    {
        AppUser user = appUserRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG + id));

        List<TemplateResponse> templates = templateService.getMyTemplates(user);

        AppUserDTO result = userMapService.mapAppUserToDTOWithAddInfoAndFriends(user);
        result.setTemplates(templates);
        return result;
    }

    @Transactional
    public AppUserDTO getPlainUserWithAddInfo(long id)
    {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG + 1L));

        return userMapService.mapAppUserToAddUserDTOWithAddInfo(user);
    }

    public AppUser getUserById(Long senderId)
    {
        return appUserRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG + senderId));
    }
}
