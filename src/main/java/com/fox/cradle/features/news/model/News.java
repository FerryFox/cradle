package com.fox.cradle.features.news.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
public class News
{
    @MongoId
    private String id;

    private String title;
    private String description;
    private String imageUrl;
    private NewsCategory newsCategory;
}
