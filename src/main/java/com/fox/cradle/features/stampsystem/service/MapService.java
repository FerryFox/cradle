package com.fox.cradle.features.stampsystem.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stampsystem.model.stamp.StampField;
import com.fox.cradle.features.stampsystem.model.stamp.StampFieldResponse;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCard;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampsystem.model.template.Template;
import com.fox.cradle.features.stampsystem.model.template.NewTemplate;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService
{
    private final PictureService pictureService;

    public Template mapNewToTemplate(NewTemplate dto, AppUser appUser, String pictureId)
    {
        String uniqueUserName =
                appUser.getAppUserName() +
                "#" +
                appUser.getNameIdentifier();

        Instant instant = Instant.now();

        return Template.builder()
                        .name(dto.getName())
                        .promise(dto.getPromise())
                        .description(dto.getDescription())
                        .image(pictureId)
                        .defaultCount(dto.getDefaultCount())
                        .createdBy(uniqueUserName)
                        .appUser(appUser)
                        .createdDate(instant)
                        .expirationDate(dto.getExpirationDate())
                        .stampCardCategory(dto.getStampCardCategory())
                        .stampCardSecurity(dto.getStampCardSecurity())
                        .stampCardStatus(dto.getStampCardStatus())
                        .lastModifiedDate(instant)
                        .build();
    }

    @Transactional
    public TemplateResponse mapTemplateToResponse(Template template)
    {
        String image = pictureService.getPictureString(template.getImage());

        return TemplateResponse.builder()
                .id(template.getId())
                .name(template.getName())
                .promise(template.getPromise())
                .description(template.getDescription())
                .defaultCount(template.getDefaultCount())
                .createdBy(template.getCreatedBy())
                .userId(template.getAppUser().getId())
                .image(image)
                .stampCardCategory(template.getStampCardCategory())
                .stampCardSecurity(template.getStampCardSecurity())
                .stampCardStatus(template.getStampCardStatus())
                .createdDate(template.getCreatedDate().toString())
                .lastModifiedDate(template.getLastModifiedDate().toString())
                .expirationDate(template.getExpirationDate())
                .build();
    }

    public List<StampCardResponse> mapStampCardsToResponseNoFields(List<StampCard> stampCards)
    {
     return stampCards
                .stream()
                .map(this::mapStampCardToResponseNoStampFields)
                .toList();
    }

    @Transactional
    public StampCardResponse mapStampCardToResponseNoStampFields(StampCard stampCard)
    {
        TemplateResponse templateResponse = mapTemplateToResponse(stampCard.getTemplate());

        return StampCardResponse.builder()
                .id(stampCard.getId())
                .createdDate(stampCard.getCreatedDate().toString())
                .templateModel(templateResponse)
                .isCompleted(stampCard.isCompleted())
                .isRedeemed(stampCard.isRedeemed())
                .redeemDate(stampCard.getRedeemDate() != null ? stampCard.getRedeemDate().toString() : null)
                .build();
    }

    public List<StampFieldResponse> mapStampFieldsToResponse(List<StampField> fields)
    {
        return fields
                .stream()
                .map(this::mapStampFieldToResponse)
                .toList();
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


    @Transactional
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
