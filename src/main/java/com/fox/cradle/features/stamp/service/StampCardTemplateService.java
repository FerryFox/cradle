package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stamp.model.StampCardTemplate;
import com.fox.cradle.features.stamp.model.TemplateRequest;
import com.fox.cradle.features.stamp.model.TemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StampCardTemplateService
{
    private final MapService MapService;
    private final StampCardTemplateRepository templateRepository;

    public TemplateResponse createTemplate(TemplateRequest request)
    {
        StampCardTemplate template = MapService.mapRequestToTemplate(request);
        templateRepository.save(template);
        return MapService.mapTemplateToResponse(template);
    }

    public List<TemplateResponse> getAllTemplates()
    {
        List<StampCardTemplate> templates = templateRepository.findAll();
        return templates.stream().map(MapService::mapTemplateToResponse).collect(Collectors.toList());
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
