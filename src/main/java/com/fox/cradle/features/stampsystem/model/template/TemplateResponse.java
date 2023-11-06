package com.fox.cradle.features.stampsystem.model.template;

import com.fox.cradle.features.appuser.model.AppUserDTO;
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
public class TemplateResponse
{
    private long id;

    private String name;
    private String promise;
    private String description;
    private String image;

    private String createdBy;
    private Long userId;
    private int defaultCount;
    private AppUserDTO creator;

    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;
    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;
    @Enumerated(EnumType.STRING)
    private StampCardStatus stampCardStatus;

    private String createdDate;
    private String expirationDate;
    private String lastModifiedDate;
}
