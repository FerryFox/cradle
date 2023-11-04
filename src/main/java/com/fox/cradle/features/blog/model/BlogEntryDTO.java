package com.fox.cradle.features.blog.model;

import com.fox.cradle.features.appuser.model.AppUserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BlogEntryDTO
{
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 30, message = "Title should not be longer than 30 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(max = 700, message = "Content should not be longer than 700 characters")
    private String content;

    private String createdDate;
    private String pictureBase64;
    private AppUserDTO appUser;
}
