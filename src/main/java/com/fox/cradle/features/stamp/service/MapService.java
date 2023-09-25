package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stamp.model.Template;
import com.fox.cradle.features.stamp.model.NewTemplate;
import com.fox.cradle.features.stamp.model.TemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class MapService
{
    private final PictureService pictureService;

    public Template mapRequestToTemplate(NewTemplate dto, AppUser appUser, String pictureId)
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
                        .build();
    }

    public TemplateResponse mapTemplateToResponse(Template template)
    {
        String image = pictureService.getPictureByIdBase64Encoded(template.getImage());

        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        String zonedDateTime = template.getCreatedDate().atZone(zoneId).toString();

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
                .createdDate(zonedDateTime)
                .build();
        return response;
    }
}
