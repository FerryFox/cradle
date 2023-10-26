package com.fox.cradle.features.appuser.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AppUserDTO
{
    private Long id;
    private String appUserName;
    private String appUserEmail;
    private String nameIdentifier;

    private AddInfoDTO addInfoDTO;
}
