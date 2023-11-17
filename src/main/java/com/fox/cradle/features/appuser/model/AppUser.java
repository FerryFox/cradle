package com.fox.cradle.features.appuser.model;

import com.fox.cradle.features.blog.model.BlogEntry;
import com.fox.cradle.features.mail.model.Mail;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCard;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser
{
    @Id
    @GeneratedValue
    private Long id;

    private String appUserName;
    private String appUserEmail;
    private String nameIdentifier;

    private boolean receiveNews;

    //Relationships
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StampCard> myStampCards = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppUser> friends = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AdditionalInfo additionalInfo;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BlogEntry> blogEntries = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mail>  mails = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mail> sendMails = new ArrayList<>();
}
