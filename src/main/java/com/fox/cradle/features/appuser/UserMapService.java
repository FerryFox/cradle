package com.fox.cradle.features.appuser;

import com.fox.cradle.features.appuser.model.AddInfoDTO;
import com.fox.cradle.features.appuser.model.AdditionalInfo;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.picture.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapService
{
    private final PictureService pictureService;

    public AddInfoDTO mapAdditionalInfoToDTO(AdditionalInfo info)
    {
        String picture = null;
        if(info.getPictureId() != null)
        {
            picture = pictureService.getPictureString(info.getPictureId());
        }
        return AddInfoDTO.builder()
                .name(info.getAppUser().getAppUserName() + "#" + info.getAppUser().getNameIdentifier())
                .id(info.getId())
                .bio(info.getBio())
                .status(info.getStatus())
                .connection(info.getConnection())
                .picture(picture)
                .build();
    }

    public AppUserDTO mapAppUserToDTOWithAddInfo(AppUser appUser)
    {
        List<AppUserDTO> friends = appUser.getFriends().stream()
                .map(this::mapAppUserToAddUserDTOWithAddInfo)
                .collect(Collectors.toList());


        return AppUserDTO.builder()
                .id(appUser.getId())
                .appUserName(appUser.getAppUserName())
                .appUserEmail(appUser.getAppUserEmail())
                .nameIdentifier(appUser.getNameIdentifier())
                .addInfoDTO(mapAdditionalInfoToDTO(appUser.getAdditionalInfo()))
                .friends(friends)
                .build();
    }

    public AppUserDTO mapAppUserToAddUserDTOWithAddInfo(AppUser appUser)
    {
        return AppUserDTO.builder()
                .id(appUser.getId())
                .appUserName(appUser.getAppUserName())
                .appUserEmail(appUser.getAppUserEmail())
                .nameIdentifier(appUser.getNameIdentifier())
                .addInfoDTO(mapAdditionalInfoToDTO(appUser.getAdditionalInfo()))
                .build();
    }


    public List<AppUserDTO> mapAppUserListToDTO(List<AppUser> friends)
    {
        return friends.stream()
                .map(this::mapAppUserToDTOWithAddInfo)
                .collect(Collectors.toList());
    }
}
