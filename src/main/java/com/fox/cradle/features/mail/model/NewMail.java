package com.fox.cradle.features.mail.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class NewMail
{
    private String text;
    private Long templateId;
    private Long receiverId;
}
