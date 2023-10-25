package com.fox.cradle.features.stampsystem.model.template;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateEdit
{
    private String id;

    private String name;
    private String promise;
    private String description;

    private String stampCardCategory;
    private String stampCardStatus;

    private String image;
    private String expirationDate;
}
