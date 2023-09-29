package com.fox.cradle.features.stampSystem.service.card;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampSystem.model.template.Template;
import com.fox.cradle.features.stampSystem.model.template.TemplateResponse;
import com.fox.cradle.features.stampSystem.service.MapService;
import com.fox.cradle.features.stampSystem.service.template.TemplateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StampCardService
{
    private final StampCardRepository stampCardRepository;
    private final TemplateRepository templateRepository;
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
                .build();

        appUser.getMyStampCards().add(stampCard);
        stampCardRepository.save(stampCard);
        //prepare response

        if(template == null)
        {
            return null;
        }
        TemplateResponse templateResponse = mapService.mapTemplateToResponse(template);

        return StampCardResponse.builder()
                .id(stampCard.getId())
                .createdDate(stampCard.getCreatedDate().toString())
                .templateResponse(templateResponse)
                .build();
    }

    public StampCard getStampCardById(long id)
    {
        return stampCardRepository.findById(id).orElse(null);
    }

    public List<StampCardResponse> getAllStampCards(AppUser appUser)
    {
        var result = appUser.getMyStampCards();
        return mapService.mapStampCardsToResponse(result);
    }
}
