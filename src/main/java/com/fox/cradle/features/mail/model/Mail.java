package com.fox.cradle.features.mail.model;

import com.fox.cradle.features.appuser.model.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Mail
{
    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private boolean isRead;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="receiver_id")
    private AppUser owner;

    private Long templateId;
    private Long senderId;
}
