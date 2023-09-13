package com.fox.cradle.features.stamp.model;

import com.fox.cradle.features.appuser.model.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class StampCard
{
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;
    private String image;

    private Instant createdDate = Instant.now();

    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser appUser;

    @OneToOne(fetch = FetchType.EAGER)
    private StampCardTemplate stampCardTemplate;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Stamp> stamps = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;

    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    public void smallPrint()
    {
        System.out.println("stamp card : " + getStampCardTemplate().getName());
    }
    @Override
    public String toString()
    {

        return
        "stamp card name : " + getStampCardTemplate().getName() + "\n" +
        "created by: " + getStampCardTemplate().getCreatedBy() + "\n" +
        "template created: " + getStampCardTemplate().getCreatedDate() + "\n" +
        "stamp card created: " + getCreatedDate() + "\n" +
        "stamp card owner : " + getAppUser().getAppUserName() + "\n" +
        "----------Stamps ----------\n" +
        getStamps().toString() + "\n" ;
    }

}
