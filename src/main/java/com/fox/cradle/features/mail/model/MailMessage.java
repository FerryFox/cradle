package com.fox.cradle.features.mail.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailMessage
{
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private boolean senderMassage;
}
