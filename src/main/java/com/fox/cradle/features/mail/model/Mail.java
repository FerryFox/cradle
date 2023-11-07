package com.fox.cradle.features.mail.model;

import com.fox.cradle.features.appuser.model.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<MailMessage> text;

    private boolean isRead;
    private Long templateId;

    private boolean redeemedTemplate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="receiver_id")
    private AppUser owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sender_id")
    private AppUser sender;
}
