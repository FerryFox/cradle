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
    private long id;
    private String name;
    private String description;
    private int defaultCount;
    private String createdBy;
    private String image;
    private StampCardCategory stampCardCategory;
    private StampCardSecurity stampCardSecurity;
    private StampCardStatus stampCardStatus;
    private String createdDate;
}
