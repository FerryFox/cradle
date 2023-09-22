package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.picture.service.PictureService;
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
    private final PictureService pictureService;

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

    public TemplateResponse createTemplate(NewTemplate request, AppUser appUser)
    {
        String pictureId = pictureService.savePicture(request.getImage() , request.getFileName()).getId();

        Template newTemplate = MapService.mapRequestToTemplate(request, appUser, pictureId);
        Template savedTemplate = templateRepository.save(newTemplate);
        return MapService.mapTemplateToResponse(savedTemplate);
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

    public void deleteTemplate(Long id)
    {
        templateRepository.deleteById(id);
    }
}
