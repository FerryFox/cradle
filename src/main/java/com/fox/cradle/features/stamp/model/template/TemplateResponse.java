package com.fox.cradle.features.stamp.model.template;

import com.fox.cradle.features.stamp.model.enums.StampCardCategory;
import com.fox.cradle.features.stamp.model.enums.StampCardSecurity;
import com.fox.cradle.features.stamp.model.enums.StampCardStatus;
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
    private String description;
    private String image;

    private String createdBy;
    private int defaultCount;


    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;

    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    @Enumerated(EnumType.STRING)
    private StampCardStatus stampCardStatus;

    private String createdDate;
    private String lastModifiedDate;
}
