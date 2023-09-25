package com.fox.cradle.features.stamp.model;

import com.fox.cradle.features.appuser.model.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Builder
public class Template
{
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;
    private String image;

    private String createdBy;
    private int defaultCount;

    private Instant createdDate;

    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;

    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    @Enumerated(EnumType.STRING)
    private StampCardStatus stampCardStatus;

    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser appUser;
}
