package com.fox.cradle.features.appuser.model;

import com.fox.cradle.features.mail.model.MailDTO;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO
{
    private Long id;
    private String appUserName;
    private String appUserEmail;
    private String nameIdentifier;

    private AddInfoDTO addInfoDTO;
    private List<AppUserDTO> friends;
    private List<TemplateResponse> templates;
    private List<MailDTO> mails;
}
