package com.fox.cradle.features.blog.model;

import com.fox.cradle.features.appuser.model.AppUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogEntryDTO
{
    private Long id;

    private String title;
    private String content;

    private String createdDate;
    private String pictureBase64;
    private AppUserDTO appUser;

}
