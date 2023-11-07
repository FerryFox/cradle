package com.fox.cradle.features.appuser;

import com.fox.cradle.features.appuser.model.AdditionalInfo;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.mail.model.MailDTO;
import com.fox.cradle.features.mail.model.MailMessage;
import com.fox.cradle.features.mail.model.MessageDTO;
import com.fox.cradle.features.mail.service.MailService;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stampsystem.service.MapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserMapServiceUnitTest
{
    @Mock
    private PictureService pictureService;

    @Mock
    private MailService mailService;

    @InjectMocks
    UserMapService mapService;

    @Test
    void mapAdditionalInfoToDTOWithNoPicture()
    {
        //GIVEN
        AppUser appUser = AppUser.builder()
                .appUserName("Test User")
                .appUserEmail("Test Email")
                .id(1L)
                .nameIdentifier("Test Identifier")
                .build();

        AdditionalInfo info = AdditionalInfo.builder()
                .id(1L)
                .appUser(appUser)
                .bio("Test Bio")
                .status("Test Status")
                .connection(null)
                .pictureId(null)
                .build();

        //WHEN
        mapService.mapAdditionalInfoToDTO(info);

        //THEN
        verify(pictureService, times(0)).getPictureString(anyString());
    }
    @Test
    void mapAdditionalInfoToDTOWithPicture()
    {
        AppUser appUser = AppUser.builder()
                .appUserName("Test User")
                .appUserEmail("Test Email")
                .id(1L)
                .nameIdentifier("Test Identifier")
                .build();

        //GIVEN
        AdditionalInfo info = AdditionalInfo.builder()
                .id(1L)
                .appUser(appUser)
                .bio("Test Bio")
                .status("Test Status")
                .connection(null)
                .pictureId("abc-123")
                .build();

        //WHEN
        mapService.mapAdditionalInfoToDTO(info);

        //THEN
        verify(pictureService, times(1)).getPictureString(anyString());
    }

    @Test
    void mapAppUserToDTOWithAddInfoAndMailsTest() {
        //GIVEN
        AppUser appUser = AppUser.builder()
                .appUserName("Test User")
                .appUserEmail("Test Email")
                .id(1L)
                .nameIdentifier("Test Identifier")
                .build();

        AppUserDTO appUserDTO = AppUserDTO.builder()
                .id(appUser.getId())
                .appUserName(appUser.getAppUserName())
                .appUserEmail(appUser.getAppUserEmail())
                .nameIdentifier(appUser.getNameIdentifier())
                .build();

        AdditionalInfo info = AdditionalInfo.builder()
                .id(1L)
                .appUser(appUser)
                .bio("Test Bio")
                .status("Test Status")
                .connection(null)
                .pictureId("abc-123")
                .build();

        appUser.setAdditionalInfo(info);

        MailMessage message = MailMessage.builder()
                .senderMassage(true)
                .text("Test Message")
                .build();

        MailMessage message2 = MailMessage.builder()
                .senderMassage(true)
                .text("Test Message 2")
                .build();

        List<MailMessage> messages = List.of(message, message2);

        MailDTO mails = MailDTO.builder()
                .id(1L)
                .receiver(appUserDTO)
                .sender(appUserDTO)
                .isRead(false)
                .conversation(messages)
                .build();
        //WHEN

        when(mailService.getAllUserMails(appUser)).thenReturn(List.of(mails));
        mapService.mapAppUserToDTOWithAddInfoAndMails(appUser);

        //THEN
        verify(mailService, times(1)).getAllUserMails(appUser);
    }
}