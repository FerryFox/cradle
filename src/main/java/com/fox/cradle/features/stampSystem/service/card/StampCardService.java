package com.fox.cradle.features.stampSystem.service.card;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stampSystem.model.stamp.StampField;
import com.fox.cradle.features.stampSystem.model.stamp.StampFieldResponse;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampSystem.model.template.Template;
import com.fox.cradle.features.stampSystem.model.template.TemplateResponse;
import com.fox.cradle.features.stampSystem.service.MapService;
import com.fox.cradle.features.stampSystem.service.stamp.StampFieldRepository;
import com.fox.cradle.features.stampSystem.service.template.TemplateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StampCardService
{
    private final StampCardRepository stampCardRepository;
    private final TemplateRepository templateRepository;
    private final StampFieldRepository stampFieldRepository;
    private final MapService mapService;


    @Transactional
    public StampCardResponse createStampCard(String templateId, AppUser appUser)
    {
        //create and save stampCard
        Template template = templateRepository
                .findById(Long.parseLong(templateId)).orElse(null);

        StampCard stampCard = StampCard.builder()
                .createdDate(Instant.now())
                .owner(appUser)
                .template(template)
                .isCompleted(false)
                .isRedeemed(false)
                .build();

        appUser.getMyStampCards().add(stampCard);
        StampCard savedCard = stampCardRepository.save(stampCard);

        List<StampField> fields = new ArrayList<>();
        for(int i = 1; i <= template.getDefaultCount() ; i++)
        {
            StampField stampField = StampField.builder()
                    .isStamped(false)
                    .Index(i)
                    .stampCard(savedCard)
                    .emptyImageUrl("https://images.nightcafe.studio/jobs/Ku1vjoHEHrx5OGqbtgxL/Ku1vjoHEHrx5OGqbtgxL--1--cyx7c.jpg?tr=w-640,c-at_max")
                    .stampedImageUrl("https://images.nightcafe.studio/jobs/c2EI3ymfZvoZHuTyjhos/c2EI3ymfZvoZHuTyjhos--1--vylwa.jpg?tr=w-1600,c-at_max")
                    .build();
            fields.add(stampField);
            stampFieldRepository.save(stampField);
        }

        List<StampFieldResponse> stampFieldResponses = mapService.mapStampFieldsToResponse(fields);

        TemplateResponse templateResponse = mapService.mapTemplateToResponse(template);

        return StampCardResponse.builder()
                .id(stampCard.getId())
                .createdDate(stampCard.getCreatedDate().toString())
                .templateModel(templateResponse)
                .stampFields(stampFieldResponses)
                .build();
    }

    public List<StampCardResponse> getAllStampCardsNoFields(AppUser appUser)
    {
       var stampCards = appUser.getMyStampCards();

       return mapService.mapStampCardsToResponseNoFields(stampCards);
    }

    public List<StampFieldResponse> getStampFields(Long stampCardId)
    {
        StampCard stampCard = new StampCard();
        stampCard.setId(stampCardId);

        List<StampField> fields = stampCard.getStampFields();

        List<StampFieldResponse> result = new ArrayList<>();
        for(StampField item : fields)
        {
            StampFieldResponse sfr = StampFieldResponse.builder()
                    .stampedImageUrl(item.getStampedImageUrl())
                    .emptyImageUrl(item.getEmptyImageUrl())
                    .isStamped(item.isStamped())
                    .index(item.getIndex())
                    .id(item.getId())
                    .stampCardId(stampCard.getId())
                    .build();
            
            result.add(sfr);
        }
        return result;
    }

    public StampCardResponse getStampCard(Long id)
    {
        StampCard stampCard = stampCardRepository.findById(id).orElse(null);

        TemplateResponse templateResponse = mapService.mapTemplateToResponse(stampCard.getTemplate());

        List<StampField> fields = stampFieldRepository.findByStampCardId(id);
        List<StampFieldResponse> stampFieldResponses = mapService.mapStampFieldsToResponse(fields);

        return StampCardResponse.builder()
                .id(stampCard.getId())
                .createdDate(stampCard.getCreatedDate().toString())
                .templateModel(templateResponse)
                .stampFields(stampFieldResponses)
                .build();
    }
}
