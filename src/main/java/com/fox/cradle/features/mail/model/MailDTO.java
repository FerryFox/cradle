package com.fox.cradle.features.mail.model;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO
{
    private Long id;
    private String text;

    private AppUserDTO sender;
    private TemplateResponse templateResponse;
    private boolean isRead;
}
