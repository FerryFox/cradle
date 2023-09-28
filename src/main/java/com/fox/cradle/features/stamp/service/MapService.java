package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stamp.model.template.Template;
import com.fox.cradle.features.stamp.model.template.NewTemplate;
import com.fox.cradle.features.stamp.model.template.TemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class MapService
{
    private final PictureService pictureService;

    public Template mapRequestNewToTemplate(NewTemplate dto, AppUser appUser, String pictureId)
    {
        String appUserEmail = appUser.getAppUserEmail();
        Instant instant = Instant.now();

        return Template.builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .image(pictureId)
                        .defaultCount(dto.getDefaultCount())
                        .createdBy(appUserEmail)
                        .appUser(appUser)
                        .createdDate(instant)
                        .stampCardCategory(dto.getStampCardCategory())
                        .stampCardSecurity(dto.getStampCardSecurity())
                        .stampCardStatus(dto.getStampCardStatus())
                        .lastModifiedDate(instant)
                        .build();
    }

    public TemplateResponse mapTemplateToResponse(Template template)
    {
        String image = pictureService.getPictureByIdBase64Encoded(template.getImage());

        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        String zonedDateTimeCreated = template.getCreatedDate().atZone(zoneId).toString();
        String zonedDateUpdated = "";
        if (template.getLastModifiedDate() == null) {
            zonedDateUpdated = zonedDateTimeCreated;

        }
        else
        {
            zonedDateUpdated = template.getLastModifiedDate().atZone(zoneId).toString();
        }
        TemplateResponse response = TemplateResponse.builder()
                .id(template.getId())
                .name(template.getName())
                .description(template.getDescription())
                .defaultCount(template.getDefaultCount())
                .createdBy(template.getCreatedBy())
                .image(image)
                .stampCardCategory(template.getStampCardCategory())
                .stampCardSecurity(template.getStampCardSecurity())
                .stampCardStatus(template.getStampCardStatus())
                .createdDate(zonedDateTimeCreated)
                .lastModifiedDate(zonedDateUpdated)
                .build();

        return response;
    }
}
