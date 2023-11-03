package com.fox.cradle.features.appuser;

import com.fox.cradle.features.appuser.model.AddInfoDTO;
import com.fox.cradle.features.appuser.model.AdditionalInfo;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.mail.MailMapperService;
import com.fox.cradle.features.mail.model.MailDTO;
import com.fox.cradle.features.mail.service.MailService;
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
    private final MailService mailService;

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

    public AppUserDTO mapAppUserToDTOWithAddInfoAndFriends(AppUser appUser)
    {
        List<AppUserDTO> friends = appUser.getFriends().stream()
                .map(this::mapAppUserToAddUserDTOWithAddInfo)
                .collect(Collectors.toList());

        AddInfoDTO info = mapAdditionalInfoToDTO(appUser.getAdditionalInfo());

        return AppUserDTO.builder()
                .id(appUser.getId())
                .appUserName(appUser.getAppUserName())
                .appUserEmail(appUser.getAppUserEmail())
                .nameIdentifier(appUser.getNameIdentifier())
                .addInfoDTO(info)
                .friends(friends)
                .build();
    }

    public AppUserDTO mapAppUserToAddUserDTOWithAddInfo(AppUser appUser)
    {
        AddInfoDTO info = mapAdditionalInfoToDTO(appUser.getAdditionalInfo());

        return AppUserDTO.builder()
                .id(appUser.getId())
                .appUserName(appUser.getAppUserName())
                .appUserEmail(appUser.getAppUserEmail())
                .nameIdentifier(appUser.getNameIdentifier())
                .addInfoDTO(info)
                .build();
    }

    public AppUserDTO mapAppUserToDTOWithAddInfoAndMails(AppUser appUser)
    {
        AddInfoDTO info = mapAdditionalInfoToDTO(appUser.getAdditionalInfo());
        List<MailDTO> mails = mailService.getMails(appUser);

        return AppUserDTO.builder()
                .id(appUser.getId())
                .appUserName(appUser.getAppUserName())
                .appUserEmail(appUser.getAppUserEmail())
                .nameIdentifier(appUser.getNameIdentifier())
                .addInfoDTO(info)
                .mails(mails)
                .build();
    }

    public List<AppUserDTO> mapAppUserFriendsToDTOWithAddInfo(List<AppUser> friends)
    {
        return friends.stream()
                .map(this::mapAppUserToAddUserDTOWithAddInfo)
                .collect(Collectors.toList());
    }
}
