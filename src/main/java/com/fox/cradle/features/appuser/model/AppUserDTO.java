package com.fox.cradle.features.appuser.model;

import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Builder
@Data
public class AppUserDTO
{
    private Long id;
    private String appUserName;
    private String appUserEmail;
    private String nameIdentifier;

    private AddInfoDTO addInfoDTO;
    private List<AppUserDTO> friends;
    private List<TemplateResponse> templates;
}
