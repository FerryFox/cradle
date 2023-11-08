package com.fox.cradle.features.mail.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @Size(min = 1, max = 200)
    private String text;
    private Long templateId;
    private boolean redeemedTemplate;

    @NotBlank
    private Long receiverId;
}
