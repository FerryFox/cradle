package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.stamp.model.StampCardTemplate;
import com.fox.cradle.features.stamp.model.TemplateRequestDTO;
import com.fox.cradle.features.stamp.model.TemplateResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StampCardTemplateService
{
    private final StampCardTemplateRepository templateRepository;
    public TemplateResponseDTO createTemplate(TemplateRequestDTO request)
    {
        StampCardTemplate template = MapService.mapDTOToStemCardTemplate(request);
        templateRepository.save(template);
        return MapService.mapStampCardTemplateToDTO(template);
    }

    public List<TemplateResponseDTO> getAllTemplatesDTO()
    {
        List<StampCardTemplate> templates = templateRepository.findAll();
        return templates.stream().map(MapService::mapStampCardTemplateToDTO).collect(Collectors.toList());
    }

    public StampCardTemplate getStampCardTemplateById(Long id)
    {
        return templateRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Stamp card template not found"));
    }

    public StampCardTemplate save(StampCardTemplate stampCardTemplate)
    {
        return templateRepository.save(stampCardTemplate);
    }

}
