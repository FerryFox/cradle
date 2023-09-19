package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stamp.model.Template;
import com.fox.cradle.features.stamp.model.NewTemplate;
import com.fox.cradle.features.stamp.model.TemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateService
{
    private final MapService MapService;
    private final TemplateRepository templateRepository;

    public List<TemplateResponse> getAllTemplates()
    {
        List<Template> templates = templateRepository.findAll();
        return templates.stream().map(MapService::mapTemplateToResponse).collect(Collectors.toList());
    }

    public List<TemplateResponse> getMyTemplates(AppUser appUser)
    {
        List<Template> templates = templateRepository.findAll().stream().
                filter( template -> template.getCreatedBy().equals(appUser.getAppUserEmail()))
                .toList();

        return templates.stream().map(MapService::mapTemplateToResponse).collect(Collectors.toList());
    }

    public TemplateResponse createTemplate(NewTemplate request)
    {
        Template template = MapService.mapRequestToTemplate(request);
        template = templateRepository.save(template);
        return MapService.mapTemplateToResponse(template);
    }

    public Template getStampCardTemplateById(Long id)
    {
        return templateRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Stamp card template not found"));
    }


    public Template save(Template template)
    {
        return templateRepository.save(template);
    }
}
