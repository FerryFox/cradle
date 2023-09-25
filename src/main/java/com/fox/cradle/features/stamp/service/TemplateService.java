package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.picture.model.Picture;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stamp.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
        newTemplate.setCreatedDate(Instant.now());

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

    @Transactional
    public TemplateResponse updateTemplate(TemplateEdit request)
    {
        Template template = templateRepository.findById(Long.parseLong(request.getId())).orElseThrow(
                () -> new RuntimeException("Stamp card template not found"));


        String oldString64 = pictureService.getPictureByIdBase64Encoded(template.getImage());
        String newString64 = pictureService.removePrefixFrom64(request.getImage());
        if(!oldString64.equals(newString64))
        {
            Picture picture = pictureService.updatePicutre(template.getImage(), request.getImage());
            template.setImage(picture.getId());
        }

        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setDefaultCount(Integer.parseInt(request.getDefaultCount()));

        template.setStampCardCategory(StampCardCategory.valueOf(request.getStampCardCategory()));
        template.setStampCardSecurity(StampCardSecurity.valueOf(request.getStampCardSecurity()));
        template.setStampCardStatus(StampCardStatus.valueOf(request.getStampCardStatus()));

        Template savedTemplate = templateRepository.save(template);
        return MapService.mapTemplateToResponse(savedTemplate);
    }
}
