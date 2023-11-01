package com.fox.cradle.features.mail.model;

import com.fox.cradle.features.appuser.model.AppUser;
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
    private String senderId;
    private String ownerId;

    private String subject;
    private String templateId;
}
