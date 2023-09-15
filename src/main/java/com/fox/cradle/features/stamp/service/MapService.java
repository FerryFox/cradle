package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.stamp.model.StampCardTemplate;
import com.fox.cradle.features.stamp.model.TemplateRequestDTO;
import com.fox.cradle.features.stamp.model.TemplateResponseDTO;

public class MapService
{
    private MapService()
    {
        throw new UnsupportedOperationException("StringUtil is a utility class and cannot be instantiated.");
    }

    public static StampCardTemplate mapDTOToStemCardTemplate(TemplateRequestDTO dto)
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

    public static TemplateResponseDTO mapStampCardTemplateToDTO(StampCardTemplate template)
    {
        TemplateResponseDTO response = new TemplateResponseDTO();
        response.setName(template.getName());
        response.setDescription(template.getDescription());
        response.setImage(template.getImage());
        response.setCreatedBy(template.getCreatedBy());
        response.setStampCardCategory(template.getStampCardCategory());
        response.setStampCardSecurity(template.getStampCardSecurity());
        return response;
    }
}
