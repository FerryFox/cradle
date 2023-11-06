package com.fox.cradle.features.blog;

import com.fox.cradle.features.appuser.UserMapService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.blog.model.BlogEntry;
import com.fox.cradle.features.blog.model.BlogEntryDTO;
import com.fox.cradle.features.picture.model.Picture;
import com.fox.cradle.features.picture.service.PictureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BlogMappingUnitTest
{
    @Mock
    private PictureService pictureService;

    @Mock
    private UserMapService userMapService;

    @InjectMocks
    private BlogMapping blogMapping;

    @Test
    void mapToDTO()
    {
        //GIVEN
        AppUser user = AppUser.builder()
                .id(1L)
                .build();

        AppUserDTO userDTO = AppUserDTO.builder()
                .id(1L)
                .build();

        BlogEntry blogEntry = BlogEntry.builder()
                .id(1L)
                .title("title")
                .content("content smaller than 700 characters")
                .createdDate(Instant.now())
                .pictureId("mongoDbId")
                .appUser(user)
                .build();

        //WHEN
        when(pictureService.getPictureString(blogEntry.getPictureId()))
                .thenReturn("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFhUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolHRgXITEhJSkrLi4uGB8zODMsNygtLisBC");
        when(userMapService.mapAppUserToAddUserDTOWithAddInfo(blogEntry.getAppUser()))
                .thenReturn(userDTO);

        BlogEntryDTO blogEntryDTO = blogMapping.mapToDTO(blogEntry);

        //THEN
        Assertions.assertEquals(blogEntry.getId(), blogEntryDTO.getId());
        Assertions.assertEquals(blogEntry.getTitle(), blogEntryDTO.getTitle());
        Assertions.assertEquals(blogEntry.getContent(), blogEntryDTO.getContent());
        Assertions.assertEquals(blogEntry.getCreatedDate().toString(), blogEntryDTO.getCreatedDate());
        Assertions.assertEquals("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFhUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolHRgXITEhJSkrLi4uGB8zODMsNygtLisBC", blogEntryDTO.getPictureBase64());
        Assertions.assertEquals(userDTO, blogEntryDTO.getAppUser());
    }

    @Test
    void mapBlogDTOToEntityWithPictureAndTimeStamp() {
        //GIVEN
        AppUser user = AppUser.builder()
                .id(1L)
                .appUserName("username")
                .build();

        BlogEntryDTO blogEntryDTO = BlogEntryDTO.builder()
                .title("title")
                .content("content smaller than 700 characters")
                .createdDate(Instant.now().toString())
                .pictureBase64("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFhUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolHRgXITEhJSkrLi4uGB8zODMsNygtLisBC")
                .build();

        //WHEN
        Picture picture = new Picture();
        picture.setId("1234567890");

        // Stub the pictureService.savePicture method to return the picture object with the ID.
        when(pictureService.savePicture(blogEntryDTO.getPictureBase64(), user.getAppUserName()))
                .thenReturn(picture);

        BlogEntry blogEntry = blogMapping.mapBlogDTOToEntity(blogEntryDTO, user);

        //THEN
        Assertions.assertEquals(blogEntryDTO.getTitle(), blogEntry.getTitle());
        Assertions.assertEquals(blogEntryDTO.getContent(), blogEntry.getContent());
        Assertions.assertEquals(blogEntryDTO.getCreatedDate(), blogEntry.getCreatedDate().toString());
        Assertions.assertEquals(picture.getId(), blogEntry.getPictureId());
        Assertions.assertEquals(user, blogEntry.getAppUser());
    }

    @Test
    void mapBlogDTOToEntityWithPictureNoTimeStamp() {
        //GIVEN
        AppUser user = AppUser.builder()
                .id(1L)
                .appUserName("username")
                .build();

        BlogEntryDTO blogEntryDTO = BlogEntryDTO.builder()
                .title("title")
                .content("content smaller than 700 characters")
                .pictureBase64("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFhUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolHRgXITEhJSkrLi4uGB8zODMsNygtLisBC")
                .build();

        //WHEN
        Picture picture = new Picture();
        picture.setId("1234567890");

        // Stub the pictureService.savePicture method to return the picture object with the ID.
        when(pictureService.savePicture(blogEntryDTO.getPictureBase64(), user.getAppUserName()))
                .thenReturn(picture);

        BlogEntry blogEntry = blogMapping.mapBlogDTOToEntity(blogEntryDTO, user);

        //THEN
        Assertions.assertEquals(blogEntryDTO.getTitle(), blogEntry.getTitle());
        Assertions.assertEquals(blogEntryDTO.getContent(), blogEntry.getContent());
        Assertions.assertNotNull(blogEntry.getCreatedDate().toString());
        Assertions.assertEquals(picture.getId(), blogEntry.getPictureId());
        Assertions.assertEquals(user, blogEntry.getAppUser());
    }

    @Test
    void mapBlogDTOToEntityTimeStampNoPicture() {
        //GIVEN
        AppUser user = AppUser.builder()
                .id(1L)
                .appUserName("username")
                .build();

        BlogEntryDTO blogEntryDTO = BlogEntryDTO.builder()
                .title("title")
                .createdDate(Instant.now().toString())
                .content("content smaller than 700 characters")
                .build();

        //WHEN


        BlogEntry blogEntry = blogMapping.mapBlogDTOToEntity(blogEntryDTO, user);

        Assertions.assertNull(blogEntry.getPictureId());
        Assertions.assertEquals(blogEntryDTO.getCreatedDate(), blogEntry.getCreatedDate().toString());
    }

    @Test
    void mapBlogDTOToEntityNoPicutreNoTimeStamp() {
        //GIVEN
        AppUser user = AppUser.builder()
                .id(1L)
                .appUserName("username")
                .build();

        BlogEntryDTO blogEntryDTO = BlogEntryDTO.builder()
                .title("title")
                .content("content smaller than 700 characters")
                .build();

        //WHEN

        BlogEntry blogEntry = blogMapping.mapBlogDTOToEntity(blogEntryDTO, user);

        //THEN
       Assertions.assertNull(blogEntry.getPictureId());
       Assertions.assertNotNull(blogEntry.getCreatedDate().toString());
    }

    @Test
    void mapBlogDTOToEntityWithPictureAndTimeStampList() {
        //GIVEN
        AppUser user = AppUser.builder()
                .id(1L)
                .appUserName("username")
                .build();

        BlogEntry blogEntry = BlogEntry.builder()
                .id(1L)
                .title("title")
                .content("content smaller than 700 characters")
                .createdDate(Instant.now())
                .pictureId("mongoDbId1")
                .appUser(user)
                .build();

        BlogEntry blogEntry2 = BlogEntry.builder()
                .id(2L)
                .title("title2")
                .content("content smaller than 700 characters")
                .createdDate(Instant.now())
                .pictureId("mongoDbId2")
                .appUser(user)
                .build();

        ArrayList<BlogEntry> blogEntries = new ArrayList<>();
        blogEntries.add(blogEntry);
        blogEntries.add(blogEntry2);

        //WHEN
        when(pictureService.getPictureString(blogEntry.getPictureId()))
                .thenReturn("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFhUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolHRgXITEhJSkrLi4uGB8zODMsNygtLisBC");
        when(pictureService.getPictureString(blogEntry2.getPictureId()))
                .thenReturn("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFhUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolHRgXITEhJSkrLi4uGB8zODMsNygtLisBB");
        when(userMapService.mapAppUserToAddUserDTOWithAddInfo(blogEntry.getAppUser()))
                .thenReturn(AppUserDTO.builder().id(1L).build());

        List<BlogEntryDTO> blogEntryDTOS = blogMapping.mapToDTOList(blogEntries);

        Assertions.assertEquals(blogEntries.size(), blogEntryDTOS.size());
        Assertions.assertEquals(blogEntries.get(0).getId(), blogEntryDTOS.get(0).getId());
        Assertions.assertEquals(blogEntries.get(0).getTitle(), blogEntryDTOS.get(0).getTitle());
        Assertions.assertEquals(blogEntries.get(0).getContent(), blogEntryDTOS.get(0).getContent());
        Assertions.assertEquals(blogEntries.get(0).getCreatedDate().toString(), blogEntryDTOS.get(0).getCreatedDate());
        Assertions.assertEquals("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFhUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolHRgXITEhJSkrLi4uGB8zODMsNygtLisBC", blogEntryDTOS.get(0).getPictureBase64());
        Assertions.assertEquals(blogEntries.get(0).getAppUser().getId(), blogEntryDTOS.get(0).getAppUser().getId());

        Assertions.assertEquals(blogEntries.get(1).getId(), blogEntryDTOS.get(1).getId());
        Assertions.assertEquals(blogEntries.get(1).getTitle(), blogEntryDTOS.get(1).getTitle());
        Assertions.assertEquals(blogEntries.get(1).getContent(), blogEntryDTOS.get(1).getContent());
        Assertions.assertEquals(blogEntries.get(1).getCreatedDate().toString(), blogEntryDTOS.get(1).getCreatedDate());
        Assertions.assertEquals("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFhUXGBgYGBgYGBgYGBgYGBgYGBgYGBgYHSggGBolHRgXITEhJSkrLi4uGB8zODMsNygtLisBB", blogEntryDTOS.get(1).getPictureBase64());
        Assertions.assertEquals(blogEntries.get(1).getAppUser().getId(), blogEntryDTOS.get(1).getAppUser().getId());
    }
}