package com.fox.cradle.features.stampsystem.service.template;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.picture.model.Picture;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stampsystem.model.enums.StampCardCategory;
import com.fox.cradle.features.stampsystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampsystem.model.enums.StampCardStatus;
import com.fox.cradle.features.stampsystem.model.stamp.TimeGateSecurity;
import com.fox.cradle.features.stampsystem.model.template.NewTemplate;
import com.fox.cradle.features.stampsystem.model.template.Template;
import com.fox.cradle.features.stampsystem.model.template.TemplateEdit;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import com.fox.cradle.features.stampsystem.service.MapService;
import com.fox.cradle.features.stampsystem.service.stamp.StampService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateService
{
    private final MapService mapService;
    private final TemplateRepository templateRepository;
    private final PictureService pictureService;
    private final StampService stampService;

    private final String ERROR = "Stamp card template not found";

    public List<TemplateResponse> getAllTemplates()
    {
        List<Template> templates = templateRepository.findAll();
        return templates.stream().map(mapService::mapTemplateToResponse).toList();
    }

    public Template getTemplateById(Long id)
    {
        return templateRepository.findById(id).orElseThrow(
                () -> new RuntimeException(ERROR));
    }

    public List<TemplateResponse> getMyTemplates(AppUser appUser)
    {
        List<Template> templates = templateRepository.findAll().stream().
                filter( template -> template.getAppUser().getId().equals(appUser.getId()))
                .toList();

        return templates.stream().map(mapService::mapTemplateToResponse).toList();
    }

    public TemplateResponse createTemplate(NewTemplate request, AppUser appUser)
    {
        //picture Saving
        String pictureId = pictureService.savePicture(request.getImage() , request.getFileName()).getId();

        Template createdTemplate = mapService.mapNewToTemplate(request, appUser, pictureId);
        createdTemplate.setCreatedDate(Instant.now());

        if(createdTemplate.getStampCardSecurity().equals(StampCardSecurity.TIMEGATE))
        {
        TimeGateSecurity timeGateSecurity = stampService.createTimeGate(request, createdTemplate);
        createdTemplate.setTimeGateSecurity(timeGateSecurity);
        }

        Template savedTemplate = templateRepository.save(createdTemplate);
        return mapService.mapTemplateToResponse(savedTemplate);
    }

    public Template getStampCardTemplateById(Long id)
    {
        return templateRepository.findById(id).orElseThrow(
            () -> new RuntimeException(ERROR));
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
                () -> new RuntimeException(ERROR));


        String oldString64 = pictureService.getPictureByIdBase64Encoded(template.getImage());
        String newString64 = pictureService.removePrefixFrom64(request.getImage());
        if(!oldString64.equals(newString64))
        {
            Picture picture = pictureService.updatePicutre(template.getImage(), request.getImage());
            template.setImage(picture.getId());
        }

        template.setName(request.getName());
        template.setPromise(request.getPromise());
        template.setDescription(request.getDescription());
        template.setDefaultCount(Integer.parseInt(request.getDefaultCount()));

        template.setStampCardCategory(StampCardCategory.valueOf(request.getStampCardCategory()));
        template.setStampCardSecurity(StampCardSecurity.valueOf(request.getStampCardSecurity()));
        template.setStampCardStatus(StampCardStatus.valueOf(request.getStampCardStatus()));

        template.setExpirationDate(request.getExpirationDate());
        template.setLastModifiedDate(Instant.now());

        Template savedTemplate = templateRepository.save(template);
        return mapService.mapTemplateToResponse(savedTemplate);
    }

    public List<TemplateResponse> getAllPublic()
    {
        List<Template> templates = templateRepository.findAll().stream()
                    .filter( template -> template.getStampCardStatus().equals(StampCardStatus.PUBLIC))
                    .toList();

        return templates.stream().map(mapService::mapTemplateToResponse).toList();
    }
}
