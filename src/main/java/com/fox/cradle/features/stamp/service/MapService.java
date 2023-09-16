package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stamp.model.StampCardTemplate;
import com.fox.cradle.features.stamp.model.TemplateRequest;
import com.fox.cradle.features.stamp.model.TemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService
{
    private final PictureService pictureService;

    public StampCardTemplate mapRequestToTemplate(TemplateRequest dto)
    {
        StampCardTemplate template = new StampCardTemplate();
        template.setName(dto.getName());
        template.setDescription(dto.getDescription());
        template.setImage(dto.getImage());
        template.setCreatedBy(dto.getCreatedBy());
        template.setStampCardCategory(dto.getStampCardCategory());
        template.setStampCardSecurity(dto.getStampCardSecurity());
        return template;
    }

    public TemplateResponse mapTemplateToResponse(StampCardTemplate template)
    {
        TemplateResponse response = new TemplateResponse();
        response.setName(template.getName());
        response.setDescription(template.getDescription());

        var image = pictureService.getPictureId(template.getImage());
        response.setImage(image);
        response.setCreatedBy(template.getCreatedBy());
        response.setStampCardCategory(template.getStampCardCategory());
        response.setStampCardSecurity(template.getStampCardSecurity());
        return response;
    }
}
