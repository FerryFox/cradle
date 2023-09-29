package com.fox.cradle.features.stampSystem.service.card;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCardResponse;
import com.fox.cradle.features.stampSystem.model.template.Template;
import com.fox.cradle.features.stampSystem.service.template.TemplateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StampCardService
{
    private final StampCardRepository stampCardRepository;
    private final TemplateRepository templateRepository;


    @Transactional
    public StampCardResponse createStampCard(String templateId, AppUser appUser)
    {
        Template template = templateRepository
                .findById(Long.parseLong(templateId)).orElse(null);

        StampCard stampCard = StampCard.builder()
                .createdDate(Instant.now())
                .owner(appUser)
                .template(template)
                .build();

        appUser.getMyStampCards().add(stampCard);
        stampCardRepository.save(stampCard);

        return StampCardResponse.builder()
                .id(stampCard.getId())
                .createdDate(stampCard.getCreatedDate().toString())
                .template(stampCard.getTemplate())
                .stamps(stampCard.getStamps())
                .build();
    }

    public StampCard getStampCardById(long id)
    {
        return stampCardRepository.findById(id).orElse(null);
    }
}
