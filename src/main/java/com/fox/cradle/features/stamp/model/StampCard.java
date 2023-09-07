package com.fox.cradle.features.stamp.model;

import com.fox.cradle.features.appuser.model.AppUser;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(fetch = FetchType.EAGER)
    private List<Stamp> stamps = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;

    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser appUser;
}
