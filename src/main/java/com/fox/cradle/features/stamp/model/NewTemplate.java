package com.fox.cradle.features.stamp.model;

import com.fox.cradle.features.appuser.model.AppUser;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewTemplate
{
    private String name;
    private String description;

    private String image;
    private String fileName;
    private int defaultCount;

    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;

    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    @Enumerated(EnumType.STRING)
    private StampCardStatus stampCardStatus;

    private AppUser appUser;
}
