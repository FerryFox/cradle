package com.fox.cradle.features.appuser.model;

import com.fox.cradle.features.stampsystem.model.stampcard.StampCard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<StampCard> myStampCards;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppUser> friends;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AdditionalInfo additionalInfo;
}
