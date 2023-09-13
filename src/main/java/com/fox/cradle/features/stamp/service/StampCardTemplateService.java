package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.stamp.model.StampCardTemplate;
import com.fox.cradle.features.stamp.model.TemplateRequestDTO;
import com.fox.cradle.features.stamp.model.TemplateResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StampCardTemplateService
{
    private final StampCardTemplateRepository templateRepository;

    public TemplateResponseDTO createTemplate(TemplateRequestDTO request)
    {
        StampCardTemplate template = mapDTOToStemCardTemplate(request);
        templateRepository.save(template);
        return mapStampCardTemplateToDTO(template);
    }

    private StampCardTemplate mapDTOToStemCardTemplate(TemplateRequestDTO dto)
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

    private TemplateResponseDTO mapStampCardTemplateToDTO(StampCardTemplate template)
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
