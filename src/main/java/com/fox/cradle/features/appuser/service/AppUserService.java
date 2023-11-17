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
import org.springframework.data.domain.PageRequest;
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


    /**
     * Saves an AppUser entity to the database. This method initializes and sets additional information
     * for the user before saving. The AdditionalInfo entity is associated with the AppUser entity in a 1 on 1 relation.
     *
     * @param appUser The AppUser entity to be saved. It should not be null.
     * @return The saved AppUser entity, including any updates made during the save process.
     */
    public AppUser saveAppUser(AppUser appUser)
    {
        //initialize additional info for user
        AdditionalInfo addInfo = new AdditionalInfo();
        addInfo.setAppUser(appUser);
        appUser.setAdditionalInfo(addInfo);

        return appUserRepository.save(appUser);
    }

    /**
     * Finds an AppUser entity by its id.
     * @param email
     * @return Optional containing the AppUser entity if it exists, or an empty Optional if it does not.
     */
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


    /**
     * Returns a list of AppUserDTOs containing the friends of the given AppUser entity.
     * The AppUserDTOs contain additional information about the friends.
     * @param appUser
     * @return
     */
    public List<AppUserDTO> getFriends(AppUser appUser)
    {
        List<AppUser> friends = appUser.getFriends();

        return userMapService.mapAppUserFriendsToDTOWith_AddInfo(friends);
    }

    //return friend
    // - additional info

    // - friends empty
    // - templates null
    // - mails null

    /**
     * Adds a friend to the given AppUser entity.
     * @param userId
     * @param friendId
     * @return the resulting AppUserDTO of the friend that was added. The friend's friends list, templates and mails will be empty.
     * */
    public AppUserDTO addFriend(Long userId, Long friendId) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG+ userId));

        AppUser friend = appUserRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG + friendId));

        if (!user.getFriends().contains(friend))
        {
            user.getFriends().add(friend);
        }

        appUserRepository.save(user);

        return userMapService.mapAppUserToAddUserDTOWith_AddInfo(friend);
    }

    public List<AppUserDTO> getUsers()
    {
        List<AppUser> users = appUserRepository.findAll();
        return userMapService.mapAppUserFriendsToDTOWith_AddInfo(users);
    }

    /**
     * Completes the appUser entity by loading its additional information and friends.
     * @param appUser
     * @return AppUserDTO containing the given AppUser entity's additional information and friends.
     */
    @Transactional
    public AppUserDTO getMeDTO(AppUser appUser)
    {
        appUser.getFriends();
        appUser.getAdditionalInfo();
        return userMapService.mapAppUserToDTOWith_AddInfo_Friends(appUser);
    }

    @Transactional
    public void deleteFriend(AppUser appUser, Long friendId)
    {
        appUser.getFriends().removeIf(friend -> friend.getId().equals(friendId));

        appUserRepository.save(appUser);
    }

    //Get user data with
    //  -   additional info
    //  -   friends

    /**
     * Load AppuserDTO containing the AppUser entity with the given id's additional information, friends and templates.
     * @param id
     * @return Returns an AppUserDTO containing the AppUser entity with the given id's additional information, friends and templates.
     */
    @Transactional
    public AppUserDTO getUserDTO(String id)
    {
        AppUser user = appUserRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG + id));

        List<TemplateResponse> templates = templateService.getMyTemplates(user);

        AppUserDTO result = userMapService.mapAppUserToDTOWith_AddInfo_Friends(user);
        result.setTemplates(templates);
        return result;
    }

    @Transactional
    public AppUserDTO getPlainUserWithAddInfo(long id)
    {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG + 1L));

        return userMapService.mapAppUserToAddUserDTOWith_AddInfo(user);
    }

    public AppUser getUserById(Long senderId)
    {
        return appUserRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND_MSG + senderId));
    }

    public AppUserDTO getLatestUser()
    {
        Optional<AppUser> mostRecentUserWithPicture = appUserRepository
                .findTopByPictureIdIsNotNullOrderByIdDesc(PageRequest.of(0, 1))
                .stream()
                .findFirst();

        if (mostRecentUserWithPicture.isEmpty()) return null;

        return userMapService.mapAppUserToAddUserDTOWith_AddInfo(mostRecentUserWithPicture.get());
    }
}
