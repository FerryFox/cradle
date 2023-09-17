package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stamp.model.Template;
import com.fox.cradle.features.stamp.model.NewTemplate;
import com.fox.cradle.features.stamp.model.TemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService
{
    private final PictureService pictureService;

    public Template mapRequestToTemplate(NewTemplate dto)
    {
        //AppUser not set
        //Id not set
        Template template = Template.builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .image(dto.getImage())
                        .createdBy(dto.getCreatedBy())
                        .stampCardCategory(dto.getStampCardCategory())
                        .stampCardSecurity(dto.getStampCardSecurity())
                        .stampCardStatus(dto.getStampCardStatus())
                        .build();

        return template;
    }

    public TemplateResponse mapTemplateToResponse(Template template)
    {
        TemplateResponse response = TemplateResponse.builder()
                .id(template.getId())
                .name(template.getName())
                .createdBy(template.getCreatedBy())
                .description(template.getDescription())
                .stampCardCategory(template.getStampCardCategory())
                .stampCardSecurity(template.getStampCardSecurity())
                .stampCardStatus(template.getStampCardStatus())
                .build();

        //image is loaded from the database
        var image = pictureService.getPictureId(template.getImage());
        response.setImage(image);

        return response;
    }
}
