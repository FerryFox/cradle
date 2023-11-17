package com.fox.cradle.features.blog;

import com.fox.cradle.features.appuser.UserMapService;
import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.blog.model.BlogEntry;
import com.fox.cradle.features.blog.model.BlogEntryDTO;
import com.fox.cradle.features.picture.service.PictureService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogMapping
{
    private final PictureService pictureService;
    private final UserMapService userMapService;

    @Transactional
    public BlogEntryDTO mapToDTO(BlogEntry blogEntry)
    {
        String pictureBase64 = pictureService.getPictureString(blogEntry.getPictureId());

        AppUser appUserWithAddInfo = blogEntry.getAppUser();
        appUserWithAddInfo.getAdditionalInfo();
        AppUserDTO appUserDTO = userMapService.mapAppUserToAddUserDTOWith_AddInfo(appUserWithAddInfo);

        return BlogEntryDTO.builder()
                .id(blogEntry.getId())
                .title(blogEntry.getTitle())
                .content(blogEntry.getContent())
                .createdDate(blogEntry.getCreatedDate().toString())
                .pictureBase64(pictureBase64)
                .appUser(appUserDTO)
                .build();
    }

    public BlogEntry mapBlogDTOToEntity(BlogEntryDTO blogEntryDTO, AppUser appUser)
    {
        String pictureId;
        if(blogEntryDTO.getPictureBase64() == null) pictureId = null;
        else pictureId = pictureService.savePicture(blogEntryDTO.getPictureBase64(), appUser.getAppUserName()).getId();

        String timeStamp;
        if(blogEntryDTO.getCreatedDate() != null) timeStamp = blogEntryDTO.getCreatedDate();
        else timeStamp = Instant.now().toString();

        return BlogEntry.builder()
                .title(blogEntryDTO.getTitle())
                .content(blogEntryDTO.getContent())
                .createdDate(Instant.parse(timeStamp))
                .pictureId(pictureId)
                .appUser(appUser)
                .build();
    }

    public List<BlogEntryDTO> mapToDTOList(List<BlogEntry> blogEntries)
    {
        return blogEntries.stream().map(this::mapToDTO).toList();
    }
}
