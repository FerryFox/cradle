package com.fox.cradle.features.stampsystem.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.picture.service.PictureService;
import com.fox.cradle.features.stampsystem.model.enums.StampCardCategory;
import com.fox.cradle.features.stampsystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampsystem.model.enums.StampCardStatus;
import com.fox.cradle.features.stampsystem.model.stamp.StampField;
import com.fox.cradle.features.stampsystem.model.stamp.StampFieldResponse;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCard;
import com.fox.cradle.features.stampsystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampsystem.model.template.NewTemplate;
import com.fox.cradle.features.stampsystem.model.template.Template;
import com.fox.cradle.features.stampsystem.model.template.TemplateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MapServiceUnitTest
{
    @Mock
    private PictureService pictureService;

    @InjectMocks
    MapService mapService;

    @Test
    void mapNewToTemplate()
    {
        AppUser appUser = AppUser.builder()
                .appUserName("Test User")
                .appUserEmail("Test Email")
                .id(1L)
                .nameIdentifier("Test Identifier")
                .build();

        String pictureId = "888-888-888-888-888";

        //GIVEN
        NewTemplate newTemplate = NewTemplate.builder()
                .name("Test Template")
                .promise("Test Promise")
                .description("Test Description")
                .image("dsdsdsadsadasdlgfdlgkdflgkdglödkg")
                .fileName("Test File Name")
                .expirationDate("2030-10-11T06:39:11.609Z")
                .defaultCount(10)
                .stampCardCategory(StampCardCategory.FOOD)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .build();

        //WHEN
        Template template = mapService.mapNewToTemplate(newTemplate, appUser, pictureId);

        //THEN
        assertEquals("Test Template", template.getName());
        assertEquals("Test Promise", template.getPromise());
        assertEquals("Test Description", template.getDescription());
        assertEquals("888-888-888-888-888", template.getImage());
        assertEquals("2030-10-11T06:39:11.609Z", template.getExpirationDate());
        assertEquals(10, template.getDefaultCount());
        assertEquals(StampCardCategory.FOOD, template.getStampCardCategory());
        assertEquals(StampCardSecurity.TRUSTUSER, template.getStampCardSecurity());
        assertEquals(StampCardStatus.PUBLIC, template.getStampCardStatus());
        assertEquals("Test User#Test Identifier", template.getCreatedBy());
        assertEquals(appUser, template.getAppUser());

        //Then Given Map Template to Response
        when(pictureService.getPictureString(anyString())).thenReturn("/9f/test323");
        TemplateResponse templateResponse = mapService.mapTemplateToResponse(template);

        //Then
        assertEquals("Test Template", templateResponse.getName());
        assertEquals("Test Promise", templateResponse.getPromise());
        assertEquals("Test Description", templateResponse.getDescription());
        assertEquals("/9f/test323", templateResponse.getImage());
    }

    @Test
    void mapStampCardToResponseNoStampFields()
    {
        //GIVEN
        Instant instant = Instant.now();
        Template template = Template.builder()
                .id(1L)
                .createdDate(instant)
                .createdBy("Test User")
                .description("Test Description")
                .expirationDate("2030-10-11T06:39:11.609Z")
                .image("888-888-888-888-888")
                .lastModifiedDate(instant)
                .name("Test Template")
                .promise("Test Promise")
                .stampCardCategory(StampCardCategory.FOOD)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .build();

        StampCard stampCard = StampCard.builder()
                .isCompleted(false)
                .id(1L)
                .createdDate(Instant.now())
                .template(template)
                .stampFields(null)
                .isRedeemed(false)
                .lastStampDate(null)
                .redeemDate(null)
                .build();

        StampField stampField = StampField.builder()
                .id(1L)
                .stamp(null)
                .stampCard(stampCard)
                .emptyImageUrl("Test Empty Image Url")
                .stampedImageUrl("Test Filled Image Url")
                .build();

        stampCard.setStampFields(List.of(stampField));


        //WHEN No StampFields
        StampCardResponse stampCardResponse = mapService.mapStampCardToResponseNoStampFields(stampCard);
        //THEN
        Assertions.assertNotNull(stampCardResponse);
        Assertions.assertNull(stampCardResponse.getStampFields());

        //WHEN With StampFields
        StampCardResponse stampCardResponseWithFields = mapService.mapStampCardToResponse(stampCard);
        //THEN
        Assertions.assertNotNull(stampCardResponseWithFields.getStampFields());

        //GIVEN 2
        List<StampCard> stampCards = new ArrayList<>();
        stampCards.add(stampCard);

        //WHEN 2
        List<StampCardResponse> stampCardResponses = mapService.mapStampCardsToResponseNoFields(stampCards);

        //THEN 2
        Assertions.assertNotNull(stampCardResponses);
        Assertions.assertEquals(1, stampCardResponses.size());
        Assertions.assertNull(stampCardResponses.get(0).getStampFields());
    }

    @Test
    void mapStampFieldAndFieldsToResponse()
    {
        //GIVEN
        Instant instant = Instant.now();
        Template template = Template.builder()
                .id(1L)
                .createdDate(instant)
                .createdBy("Test User")
                .description("Test Description")
                .expirationDate("2030-10-11T06:39:11.609Z")
                .image("888-888-888-888-888")
                .lastModifiedDate(instant)
                .name("Test Template")
                .promise("Test Promise")
                .stampCardCategory(StampCardCategory.FOOD)
                .stampCardSecurity(StampCardSecurity.TRUSTUSER)
                .stampCardStatus(StampCardStatus.PUBLIC)
                .build();

        StampCard stampCard = StampCard.builder()
                .isCompleted(false)
                .id(1L)
                .createdDate(Instant.now())
                .template(template)
                .stampFields(null)
                .isRedeemed(false)
                .lastStampDate(null)
                .redeemDate(null)
                .build();

        StampField stampField = StampField.builder()
                .id(1L)
                .stamp(null)
                .stampCard(stampCard)
                .emptyImageUrl("Test Empty Image Url")
                .stampedImageUrl("Test Filled Image Url")
                .build();

        stampCard.setStampFields(List.of(stampField));

        //WHEN
        StampFieldResponse stampFieldResponse = mapService.mapStampFieldToResponse(stampField);

        //THEN
        Assertions.assertNotNull(stampFieldResponse);

        //GIVEN 2
        List<StampField> stampFields = new ArrayList<>();
        stampFields.add(stampField);

        //WHEN 2
        List<StampFieldResponse> stampFieldResponses = mapService.mapStampFieldsToResponse(stampFields);

        //THEN 2
        Assertions.assertNotNull(stampFieldResponses);
        Assertions.assertEquals(1, stampFieldResponses.size());

    }
}