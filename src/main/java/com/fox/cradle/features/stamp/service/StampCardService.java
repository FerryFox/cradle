package com.fox.cradle.features.stamp.service;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.appuser.service.AppUserService;
import com.fox.cradle.features.stamp.model.StampCard;
import com.fox.cradle.features.stamp.model.StampCardTemplate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StampCardService
{
    private final StampCardRepository stampCardRepository;
    private final AppUserService appUserService;

    @Transactional
    public StampCard createStampCard(StampCardTemplate template, AppUser appUser)
    {
        StampCard stampCard = new StampCard();
        stampCard.setName(template.getName());
        stampCard.setDescription(template.getDescription());
        stampCard.setImage(template.getImage());
        stampCard.setStampCardTemplate(template);
        stampCard.setStampCardSecurity(template.getStampCardSecurity());
        stampCard.setStampCardCategory(template.getStampCardCategory());

        stampCard.setAppUser(appUser);
        appUser.getMyStampCards().add(stampCard);

        return stampCardRepository.save(stampCard);
    }

    public StampCard getStampCardById(long id)
    {
        return stampCardRepository.findById(id).orElse(null);
    }
}
