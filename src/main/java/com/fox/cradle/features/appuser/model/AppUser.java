package com.fox.cradle.features.appuser.model;

import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppUser
{
    @Id
    @GeneratedValue
    private Long id;

    private String appUserName;
    private String appUserEmail;
    private boolean receiveNews;

    //Relationships
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StampCard> myStampCards;
}
