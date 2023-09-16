package com.fox.cradle.features.stamp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StampCardResponse
{
    private String name;
    private String description;
    private String image;
    private String createdBy;
    private StampCardCategory stampCardCategory;
    private StampCardSecurity stampCardSecurity;
    private StampCardStatus stampCardStatus;
}
