package com.fox.cradle.features.stampSystem.model.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateEdit
{
    private String id;
    private String name;
    private String description;
    private String defaultCount;
    private String createdBy;
    private String image;
    private String stampCardCategory;
    private String stampCardSecurity;
    private String stampCardStatus;
    private String createdDate;
}
