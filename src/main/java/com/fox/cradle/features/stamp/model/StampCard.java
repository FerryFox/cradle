package com.fox.cradle.features.stamp.model;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany
    public Set<Stamp> stamps = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;

    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;
}
