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

        String pictureId = pictureService.savePicture(dto.getImage()).getId();

        return Template.builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .image(pictureId)
                        .appUser(dto.getAppUser())
                        .createdBy(dto.getCreatedBy())
                        .createdDate(new java.util.Date().toInstant())
                        .stampCardCategory(dto.getStampCardCategory())
                        .stampCardSecurity(dto.getStampCardSecurity())
                        .stampCardStatus(dto.getStampCardStatus())
                        .build();
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


        var image = pictureService.getPictureId(template.getImage());
        response.setImage(image);

        return response;
    }
}
