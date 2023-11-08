package com.fox.cradle.features.mail.model;

import com.fox.cradle.features.appuser.model.AppUserDTO;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO
{
    private Long id;
    private List<MailMessage> conversation;
    private TemplateResponse templateResponse;
    private boolean isRead;

    private boolean redeemedTemplate;

    private AppUserDTO sender;
    private AppUserDTO receiver;
}
