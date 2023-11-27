package com.fox.cradle.features.stampsystem.model.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewTemplateComposer
{
    private NewBasicInformation newBasicInformation;
    private NewTemplateSecurity newTemplateSecurity;
    private NewTemplateImage newTemplateImage;
}


