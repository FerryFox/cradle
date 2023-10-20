package com.fox.cradle.features.stampsystem.model.template;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stampsystem.model.enums.StampCardCategory;
import com.fox.cradle.features.stampsystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampsystem.model.enums.StampCardStatus;
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
    private String promise;
    private String description;

    private String image;
    private String fileName;
    private int defaultCount;

    private String expirationDate;

    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;

    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    @Enumerated(EnumType.STRING)
    private StampCardStatus stampCardStatus;

    private AppUser appUser;

    private String securityTimeGateDuration;
}
