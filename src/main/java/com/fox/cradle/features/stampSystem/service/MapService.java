package com.fox.cradle.features.stampSystem.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stampSystem.model.stamp.StampField;
import com.fox.cradle.features.stampSystem.model.stamp.StampFieldResponse;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampSystem.model.template.Template;
import com.fox.cradle.features.stampSystem.model.template.NewTemplate;
import com.fox.cradle.features.stampSystem.model.template.TemplateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapService
{
    private final PictureService pictureService;
    private final AppUserService appUserService;

    public Template mapRequestNewToTemplate(NewTemplate dto, AppUser appUser, String pictureId)
    {
        String uniqueUserName =
                appUser.getAppUserName() +
                "#" +
                appUser.getNameIdentifier();

        Instant instant = Instant.now();

        return Template.builder()
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .image(pictureId)
                        .defaultCount(dto.getDefaultCount())
                        .createdBy(uniqueUserName)
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

    public List<StampCardResponse> mapStampCardsToResponseNoFields(List<StampCard> stampCards)
    {
     return stampCards
                .stream()
                .map(this::mapStampCardToResponseNoStampFields)
                .collect(Collectors.toList());
    }

    public StampCardResponse mapStampCardToResponseNoStampFields(StampCard stampCard)
    {
        TemplateResponse templateResponse = mapTemplateToResponse(stampCard.getTemplate());
        return StampCardResponse.builder()
                .id(stampCard.getId())
                .createdDate(stampCard.getCreatedDate().toString())
                .templateModel(templateResponse)
                .isCompleted(stampCard.isCompleted())
                .isRedeemed(stampCard.isRedeemed())
                .build();
    }

    public List<StampFieldResponse> mapStampFieldsToResponse(List<StampField> fields)
    {
        return fields
                .stream()
                .map(this::mapStampFieldToResponse)
                .collect(Collectors.toList());
    }

    public StampFieldResponse mapStampFieldToResponse(StampField stampField)
    {
        return StampFieldResponse.builder()
                .id(stampField.getId())
                .stampedImageUrl(stampField.getStampedImageUrl())
                .emptyImageUrl(stampField.getEmptyImageUrl())
                .isStamped(stampField.isStamped())
                .index(stampField.getIndex())
                .stampCardId(stampField.getStampCard().getId())
                .build();
    }


    public StampCardResponse mapStampCardToResponse(StampCard stampCard)
    {
        TemplateResponse templateResponse = mapTemplateToResponse(stampCard.getTemplate());
        List<StampFieldResponse> stampFieldResponses = mapStampFieldsToResponse(stampCard.getStampFields());

        return StampCardResponse.builder()
                .id(stampCard.getId())
                .createdDate(stampCard.getCreatedDate().toString())
                .isRedeemed(stampCard.isRedeemed())
                .isCompleted(stampCard.isCompleted())
                .templateModel(templateResponse)
                .stampFields(stampFieldResponses)
                .build();
    }
}
