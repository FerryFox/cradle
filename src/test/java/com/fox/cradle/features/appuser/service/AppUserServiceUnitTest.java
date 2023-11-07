package com.fox.cradle.features.appuser.service;


import com.fox.cradle.features.appuser.model.AddInfoDTO;
import com.fox.cradle.features.appuser.model.AdditionalInfo;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.picture.model.Picture;
import com.fox.cradle.features.picture.service.PictureService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.mockito.ArgumentCaptor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AppUserServiceUnitTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PictureService pictureService;

    @InjectMocks
    private AppUserService appUserService;

    @BeforeEach
    public void setUp() {
        // Initialize AdditionalInfo and AddInfoDTO objects
    }

    @Test
    void shouldUpdateExistingPicture() {
        //GIVEN
        AdditionalInfo addInfo = AdditionalInfo.builder()
                .id(1L)
                .connection(null)
                .status("hey status")
                .bio("hey bio")
                .pictureId("123-abc")
                .build();

        AddInfoDTO additionalInfoDTO = AddInfoDTO.builder()
                .id(1L)
                .connection(null)
                .status("hey status")
                .bio("hey bio")
                .picture("data:image/png;base64,hey-picture")
                .build();

        AppUser appUser = AppUser.builder()
                .id(1L)
                .additionalInfo(addInfo)
                .build();

        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        // Act
        appUserService.updateAdditionalInfo(appUser, additionalInfoDTO);

        // Assert
        verify(appUserRepository).save(appUser); // Verify save was called with appUser
        verify(pictureService).updatePicutre(any(), eq(additionalInfoDTO.getPicture())); // Verify updatePicture was called
    }

    @Test
    void shouldUpdateOldPictureNull() {
        //GIVEN
        AdditionalInfo addInfo = AdditionalInfo.builder()
                .id(1L)
                .connection(null)
                .status("hey status")
                .bio("hey bio")
                .pictureId(null)
                .build();

        AddInfoDTO additionalInfoDTO = AddInfoDTO.builder()
                .id(1L)
                .connection(null)
                .status("hey status")
                .bio("hey bio")
                .picture(null)
                .build();

        AppUser appUser = AppUser.builder()
                .id(1L)
                .additionalInfo(addInfo)
                .build();


        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        // Act
        appUserService.updateAdditionalInfo(appUser, additionalInfoDTO);

        // Assert
        verify(appUserRepository).save(appUser);
        verify(pictureService, never()).updatePicutre(any(), eq(additionalInfoDTO.getPicture()));
    }

    @Test
    void verfyNewPictureSavedwhenUpdateAddInfo() {
        //GIVEN
        AdditionalInfo addInfo = AdditionalInfo.builder()
                .id(1L)
                .connection(null)
                .status("hey status")
                .bio("hey bio")
                .pictureId(null)
                .build();

        AddInfoDTO additionalInfoDTO = AddInfoDTO.builder()
                .id(1L)
                .connection(null)
                .status("hey status")
                .bio("hey bio")
                .picture("data:image/png;base64,hey-picture")
                .build();

        AppUser appUser = AppUser.builder()
                .id(1L)
                .additionalInfo(addInfo)
                .build();

        Picture mockPicture = mock(Picture.class);
        when(mockPicture.getId()).thenReturn("abc-123");


        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);
        when(pictureService.savePicture(any(), any())).thenReturn(mockPicture);

        // Act
        appUserService.updateAdditionalInfo(appUser, additionalInfoDTO);

        // Assert
        verify(appUserRepository).save(appUser);
        verify(pictureService, never()).updatePicutre(any(), eq(additionalInfoDTO.getPicture()));
    }
}
